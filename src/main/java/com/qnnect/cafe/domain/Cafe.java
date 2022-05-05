package com.qnnect.cafe.domain;

import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Cafe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private EGroupType groupType;

    @Enumerated(EnumType.STRING)
    private EQuestionCycle questionCycle;

    @Enumerated(EnumType.STRING)
    private EDiaryColor diaryColor;

    private String code;

    @OneToMany(mappedBy = "cafe")
    private List<CafeUser> cafeUsers;

    @OneToMany(mappedBy = "cafe")
    private List<CafeQuestion> cafeQuestions;


    @Builder
    public Cafe(String title, EGroupType groupType, EQuestionCycle questionCycle,
                EDiaryColor diaryColor, String code, User organizer){
        this.title = title;
        this.groupType = groupType;
        this.questionCycle = questionCycle;
        this.diaryColor = diaryColor;
        this.code = code;
    }
}
