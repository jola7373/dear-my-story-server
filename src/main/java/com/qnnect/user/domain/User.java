package com.qnnect.user.domain;

import com.qnnect.auth.ELoginType;
import com.qnnect.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "USER_ID")
    @Type(type = "uuid-char")
    private UUID id;

    @Column()
    private String socialId;

    @Column()
    private String nickName;

    @Column()
    private String profilePicture;

    @Column()
    private boolean pushEnabled;

    @Column()
    private int point;

    @Column()
    @Enumerated(EnumType.STRING)
    private ELoginType loginType;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;

    @Builder
    public User(String socialId, String profilePicture, ELoginType loginType, UUID id, String nickName) {
        this.socialId = socialId;
        this.loginType = loginType;
        this.profilePicture = profilePicture;
        this.id = id;
        this.nickName = nickName;
    }

    @Builder
    public User(String socialId) {
        this.socialId = socialId;
    }

    public void addPoint(int addingPoint) {
        this.point += addingPoint;
    }
    public void minusPoint(int minusPoint) {
        this.point -= minusPoint;
    }


}
