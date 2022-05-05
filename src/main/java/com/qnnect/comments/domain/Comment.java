package com.qnnect.comments.domain;

import com.qnnect.cafe.domain.EDiaryColor;
import com.qnnect.cafe.domain.EGroupType;
import com.qnnect.cafe.domain.EQuestionCycle;
import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.questions.domain.CafeQuestion;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String content;

    @Column()
    private String imageUrl1;

    @Column()
    private String imageUrl2;

    @Column()
    private String imageUrl3;

    @Column()
    private String imageUrl4;

    @Column()
    private String imageUrl5;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<Reply> reply;

    @ManyToOne
    @JoinColumn(name="CAFE_QUESTION_ID")
    private CafeQuestion cafeQuestion;


    @Builder
    public Comment(String content, String imageUrl1, String imageUrl2, String imageUrl3,
                   String imageUrl4, String imageUrl5, User user, CafeQuestion cafeQuestion){
        this.content = content;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
        setUser(user);
        setCafeQuestion(cafeQuestion);
    }

    public void setUser(User user){
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }


    public int countReply(){
        return this.reply.size();
    }
}
