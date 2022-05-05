package com.qnnect.notification.domain;

import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.notification.domain.ENotificationType;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ENotificationType notificationType;

    private long contentId;

    private String senderName;

    private String content;

    private String groupName;

    private boolean userChecked;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Notification(ENotificationType notificationType, long contentId, User user,
                        String senderName, String content, String groupName){
        this.contentId = contentId;
        this.notificationType = notificationType;
        this.senderName = senderName;
        this.content = content;
        this.groupName = groupName;
        setUser(user);
    }

    public void setUser(User user){
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }
}
