package com.qnnect.ingredients.domain;

import com.qnnect.common.domain.BaseTimeEntity;
import com.qnnect.questions.domain.Question;
import com.qnnect.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class UserIngredient extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID")
    private Ingredient ingredient;

    @Builder
    public UserIngredient(User user, Ingredient ingredient) {
        setUser(user);
        setIngredient(ingredient);
    }

    public void setUser(User user){
        if (Objects.isNull(this.user)) {
            this.user = user;
        }
    }

    public void setIngredient(Ingredient ingredient){
        if (Objects.isNull(this.ingredient)) {
            this.ingredient = ingredient;
        }
    }
}
