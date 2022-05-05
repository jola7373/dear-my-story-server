package com.qnnect.questions.domain;

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
public class Question extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private EQuestionerType questionerType;

    @Enumerated(EnumType.STRING)
    private EQuestionType questionType;

    @ManyToOne()
    @JoinColumn(name="USER_ID")
    private User user;

    @Builder
    public Question(String content, String questionType, User user) {
        this.content = content;
        this.questionType =  EQuestionType.valueOf(questionType);
        this.questionerType = EQuestionerType.user;
        setUser(user);
    }

    public void setUser (User user){
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }
    public void setContent (String content){
        this.content = content;
    }
}
