package htl.stp.at.userverwaltung.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerId implements Serializable {

    @Column(name = "my_user_id")
    private String userEmail;
    @Column(name = "question_id")
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnswerId answerId = (AnswerId) o;
        return userEmail != null && Objects.equals(userEmail, answerId.userEmail)
                && questionId != null && Objects.equals(questionId, answerId.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, questionId);
    }
}
