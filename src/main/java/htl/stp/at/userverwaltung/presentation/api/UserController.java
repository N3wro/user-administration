package htl.stp.at.userverwaltung.presentation.api;

import htl.stp.at.userverwaltung.domain.Answer;
import htl.stp.at.userverwaltung.domain.AnswerId;
import htl.stp.at.userverwaltung.domain.MyUser;
import htl.stp.at.userverwaltung.domain.Question;
import htl.stp.at.userverwaltung.domain.advice.NoSuchUserException;
import htl.stp.at.userverwaltung.persistence.AnswerRepository;
import htl.stp.at.userverwaltung.persistence.MyUserRepository;
import htl.stp.at.userverwaltung.persistence.QuestionRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/user")
public record UserController(AnswerRepository answerRepository, MyUserRepository myUserRepository, QuestionRepository questionRepository) {

    @GetMapping("/home")
    public String getUserHomePage(Model model, Principal principal)
    {
        MyUser currentUser = myUserRepository.findByUsername(principal.getName()).orElseThrow(NoSuchUserException::new);
        model.addAttribute("greeting", currentUser.getEmail());

//        List<Question> openQuestions= new LinkedList<>();

//        for ( Question question :  questionRepository.queryQuestionByExpiration_date()) {
//            if (answerRepository.isAlreadyAnswered(question.getId(), currentUser.getEmail()).intValue()==0) {
//                openQuestions.add(question);
//            }
//        }

        model.addAttribute("open_questions", questionRepository.getUnansweredUnexpiredQuestions(currentUser.getEmail()));

        return "user-page";
    }


        @PostMapping("/answer/{id}")
    public String handleNewAnswer(Model model, @PathVariable Long id, Principal principal, @Valid @ModelAttribute("new_answer") Answer answer, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            getNewAnswer(model, principal, id);
        }

        String email = myUserRepository().findByUsername(principal.getName()).orElseThrow(NoSuchUserException::new).getEmail();

       AnswerId answerId = new AnswerId(email,id);
       answer.setAnswerId(answerId);
        answerRepository.save(answer);

        return "redirect:/user/home";
    }

    @GetMapping("/answer/{id}")
    public String getNewAnswer(Model model, Principal principal,@PathVariable Long id)
    {

        AnswerId answerId = new AnswerId(principal.getName(),id);
        List<String> answers = new LinkedList<>();
        answers.add("trifft zu");
        answers.add("trifft teilweise zu");
        answers.add("trifft nicht zu");
        model.addAttribute("answers", answers);
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(IllegalArgumentException::new));

        if (!model.containsAttribute("new_answer")) {
            model.addAttribute("new_answer", new Answer(answerId, ""));
        }
        return "answer-question";
    }


}
