package htl.stp.at.userverwaltung.persistence;

import htl.stp.at.userverwaltung.domain.Answer;

import htl.stp.at.userverwaltung.domain.AnswerId;
import htl.stp.at.userverwaltung.domain.MyUser;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {


    @Query("""
                    SELECT q, count(a.answer)
                    from Question q
                    left join Answer  a
                    on a.answerId.questionId =q.id    
                    group by q.id
                    order by count(a.answer) desc                 
            """)
    public List<Object[]> getAnswerAmountByQuestion();

    @Query("""
        SELECT count(a.answerId)
        from Answer  a
        where a.answerId.userEmail = :email and a.answerId.questionId= :questionId
""")
    Integer isAlreadyAnswered(Long questionId, String email);
}
