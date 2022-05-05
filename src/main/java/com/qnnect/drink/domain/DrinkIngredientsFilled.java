package com.qnnect.drink.domain;

import com.qnnect.ingredients.domain.Ingredient;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class DrinkIngredientsFilled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="USER_DRINK_SELECTED_ID")
    private UserDrinkSelected userDrinkSelected;

    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID")
    private Ingredient ingredient;

    @Builder
    public DrinkIngredientsFilled(UserDrinkSelected userDrinkSelected,
                                  Ingredient ingredient){
        setUserDrinkSelected(userDrinkSelected);
        setIngredient(ingredient);
    }

    public void setUserDrinkSelected(UserDrinkSelected userDrinkSelected){
        if (Objects.isNull(this.userDrinkSelected)) {
            this.userDrinkSelected = userDrinkSelected;
        }
    }

    public void setIngredient(Ingredient ingredient){
        if (Objects.isNull(this.ingredient)) {
            this.ingredient = ingredient;
        }
    }
}
