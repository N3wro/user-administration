package htl.stp.at.userverwaltung.presentation.api;

import htl.stp.at.userverwaltung.domain.MyUser;
import htl.stp.at.userverwaltung.domain.Question;
import htl.stp.at.userverwaltung.domain.advice.NoSuchUserException;
import htl.stp.at.userverwaltung.persistence.AnswerRepository;
import htl.stp.at.userverwaltung.persistence.MyUserRepository;
import htl.stp.at.userverwaltung.persistence.QuestionRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public record AdminController(AnswerRepository answerRepository, MyUserRepository myUserRepository, QuestionRepository questionRepository) {

    @GetMapping("/home")
    public String getAdminHomePage(Model model, Principal principal)
    {

        MyUser currentUser = myUserRepository.findByUsername(principal.getName()).orElseThrow(NoSuchUserException::new);
        model.addAttribute("greeting", currentUser.getEmail());

        if (!model.containsAttribute("new_question")) {
            model.addAttribute("new_question", new Question());
        }
        return "admin-page";
    }

    @PostMapping("/add")
    public String HandleNewQuestion(Model model, Principal principal, @ModelAttribute("new_question") @Valid Question question, BindingResult bindingResult)
    {

        if (bindingResult.hasErrors())
        {
            getAdminHomePage(model,principal);
        }

        questionRepository.save(question);

        return "redirect:/admin/home";
    }

    @GetMapping("/questions")
    public String getAdminQuestions(Model model, Principal principal)
    {

        MyUser currentUser = myUserRepository.findByUsername(principal.getName()).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("greeting", currentUser.getEmail());

        Map<Question, Integer> questionIntegerMap = new LinkedHashMap<>();
        for (Object[] row : answerRepository.getAnswerAmountByQuestion())
        {

            questionIntegerMap.put( (Question) row[0], ((Number) row[1]).intValue());
        }
        model.addAttribute("questions", questionIntegerMap);

        return "show-all-questions";
    }


}
