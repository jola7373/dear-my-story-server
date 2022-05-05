package com.qnnect.cafe.domain;

import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.drink.domain.UserDrinkSelected;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class CafeUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CAFE_ID")
    private Cafe cafe;

    @OneToMany(mappedBy = "cafeUser")
    private List<UserDrinkSelected> userDrinkSelected;

    @Builder
    public CafeUser(User user, Cafe cafe, UserDrinkSelected userDrinkSelected){
        setUser(user);
        setCafe(cafe);
    }


    public void setUser(User user){
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }

    public void setCafe(Cafe cafe){
        if (Objects.isNull(this.cafe)) {
            this.cafe = cafe;
        }
    }
}
