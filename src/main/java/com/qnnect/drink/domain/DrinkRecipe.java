package com.qnnect.drink.domain;

import com.qnnect.ingredients.domain.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DrinkRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private int number;

    @ManyToOne()
    @JoinColumn(name="DRINK_ID")
    private Drink drink;

    @ManyToOne()
    @JoinColumn(name="INGREDIENT_ID")
    private Ingredient ingredient;
}
