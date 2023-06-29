package htl.stp.at.userverwaltung.persistence;


import htl.stp.at.userverwaltung.domain.Answer;
import htl.stp.at.userverwaltung.domain.AnswerId;
import htl.stp.at.userverwaltung.domain.MyUser;
import htl.stp.at.userverwaltung.domain.Question;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


@Component
public record DatabaseInit(MyUserRepository myUserRepository, QuestionRepository questionRepository,
                           AnswerRepository answerRepository) implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {

        //Password=username
        var users = List.of(
                new MyUser("a@gmail.com", "user1", "$2a$12$uwsRLMMU0n78tUDJU5WsKOhUlw9YKJ.YEutB8I7E/ybCXU.pW6F6K", "user"),
                new MyUser("b@gmail.com", "user2", "$2a$12$KD1HlC6Q5lLpRgeE4fGnD.wuqgza1K4inyxR5sUcOTtLBAfQXUgnW", "user"),
                new MyUser("c@gmail.com", "user3", "$2a$12$zv/IENi7isqrReWUaXBe4.fedtvNS6jnPYsJBBtlVtAKcNxTqkbcC", "user"),
                new MyUser("d@gmail.com", "user4", "$2a$12$vCf9M0ASg1g2/VpZ2YjJX.roc.iSCg8nyC4niFCH4U3SyKAyoEQe2", "admin")
        );
        users.forEach(user -> myUserRepository.save(user));


        Question question1 = new Question(1L, LocalDate.of(2023, 2, 1),
                "Persönlichkeitstest1",
                "Es fällt Ihnen schwer, sich anderen Menschen vorzustellen."
        );
        questionRepository.save(question1);

        AnswerId answerId = new AnswerId(users.get(0).getEmail(), question1.getId());
        Answer answer = new Answer(answerId, "trifft zu");
        AnswerId answerId2 = new AnswerId(users.get(1).getEmail(), question1.getId());
        Answer answer2 = new Answer(answerId2, "trifft zu");

        answerRepository.save(answer);
        answerRepository.save(answer2);

//        // 2. Frage
        Question question2 = new Question(2L, LocalDate.of(2023, 4, 14),
                "Persönlichkeitstest2",
                "Sie verlieren sich oft so sehr in Gedanken, dass Sie Ihre Umgebung ignorieren oder vergessen.");
        questionRepository.save(question2);
        AnswerId answerId3 = new AnswerId(users.get(0).getEmail(), question2.getId());
        Answer answer3 = new Answer(answerId3, "trifft zu");
        answerRepository.save(answer3);



        // 3. Frage
        Question question3 = new Question(3L, LocalDate.of(2023, 4, 14),
                "Persönlichkeitstest3",
                "Sie versuchen Ihre Mails möglichst zeitnah zu beantworten und können einen unordentlichen Posteingang nicht ertragen.");
        questionRepository.save(question3);

//
//        // 4. Frage
        Question question4 = new Question(4L, LocalDate.of(2023, 4, 14),
                "Persönlichkeitstest4",
                "Es fällt Ihnen leicht, entspannt und fokussiert zu bleiben, selbst wenn Sie unter ein wenig Druck stehen.");
        questionRepository.save(question4);
//
//        // 5. Frage
        Question question5 = new Question(5L, LocalDate.of(2023, 4, 14),
                "Persönlichkeitstest5",
                "Normalerweise beginnen Sie keine Gespräche.");
        questionRepository.save(question5);
    }
}
