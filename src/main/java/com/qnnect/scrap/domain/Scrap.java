package com.qnnect.scrap.domain;

import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.questions.domain.Question;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CAFE_QUESTION_ID")
    private CafeQuestion cafeQuestion;

    @Builder
    public Scrap(User user, CafeQuestion cafeQuestion) {
        setUser(user);
        setCafeQuestion(cafeQuestion);
    }

    public void setUser(User user){
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }
    public void setCafeQuestion(CafeQuestion cafeQuestion){
        if (Objects.isNull(this.cafeQuestion)) {
            this.cafeQuestion = cafeQuestion;
        }
    }
}
