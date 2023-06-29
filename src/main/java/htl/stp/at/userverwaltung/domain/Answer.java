package htl.stp.at.userverwaltung.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "Answer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Answer {

    @EmbeddedId
    AnswerId answerId;

    @Column(name = "answer")
    private String answer;

    public Answer(AnswerId answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;
        return answerId != null && Objects.equals(answerId, answer.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
