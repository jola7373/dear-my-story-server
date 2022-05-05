package com.qnnect.questions.domain;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class CafeQuestionWaitingList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="CAFE_ID")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name="QUESTION_ID")
    private Question question;

    @Builder
    public CafeQuestionWaitingList(Cafe cafe, Question question){
        setCafe(cafe);
        setQuestion(question);
    }

    public void setCafe(Cafe cafe){
        if (Objects.isNull(this.cafe)) {
            this.cafe = cafe;
        }
    }

    public void setQuestion(Question question){
        if (Objects.isNull(this.question)) {
            this.question = question;
        }
    }
}
