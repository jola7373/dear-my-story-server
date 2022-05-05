package com.qnnect.comments.domain;

import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @Builder
    public Reply(String content, User user, Comment comment) {
        this.content = content;
        setUser(user);
        setComment(comment);
    }

    public void setUser(User user) {
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
