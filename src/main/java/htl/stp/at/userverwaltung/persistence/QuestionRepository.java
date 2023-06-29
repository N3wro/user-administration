package htl.stp.at.userverwaltung.persistence;

import htl.stp.at.userverwaltung.domain.MyUser;
import htl.stp.at.userverwaltung.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {


    @Query("""
                    SELECT q
                    from Answer a, Question q
                    group by q.id
                    order by  count(a.answerId) desc
                    
            """)
   List<Question> queryAllOrderByAnswerAmount();
/*
    @Query("""
            SELECT q
            from Question  q, Answer a
            where q.expiration_date > current_date  and a.answerId.userEmail = :email and a.answerId.questionId= :questionId
""")
    List<Question> queryQuestionByExpiration_date();
*/

    @Query("""
    SELECT q
    FROM Question q
    WHERE q.expiration_date > CURRENT_DATE
    AND NOT EXISTS (
        SELECT a
        FROM Answer a
        WHERE a.answerId.userEmail = :email
        AND a.answerId.questionId = q.id
    )
""")
    List<Question> getUnansweredUnexpiredQuestions(String email);

    @Query("""
        select count(a)
        from  Answer a
        where a.answerId.questionId = :questionId
""")
    Integer queryQuestionByAnswerAmount(Long questionId);


//    @Query("""
//        SELECT q
//        FROM  Question q, Answer a, MyUser user
//        where  q.expiration_date > current_date and :email NOT LIKE (SELECT  a.answerId.userEmail, q.id FROM Answer a, Question q where a.answerId.questionId = )
//""")
//   List<Question> queryOpenQuestions(String email);


}
