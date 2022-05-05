package com.qnnect.drink.domain;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class UserDrinkSelected {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isFilled;

    @ManyToOne
    @JoinColumn(name = "DRINK_ID")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "CAFE_USER_ID")
    private CafeUser cafeUser;


    @OneToMany(mappedBy = "userDrinkSelected")
    private List<DrinkIngredientsFilled> drinkIngredientsFilled = new ArrayList<>();


    @Builder
    public UserDrinkSelected(CafeUser cafeUser, Drink drink){
        setCafeUser(cafeUser);
        setDrink(drink);
    }

    public void setCafeUser(CafeUser cafeUser){
        if (Objects.isNull(this.cafeUser)) {
            this.cafeUser = cafeUser;
        }
    }

    public void setDrink(Drink drink){
        if (Objects.isNull(this.drink)) {
            this.drink = drink;
        }
    }

}
