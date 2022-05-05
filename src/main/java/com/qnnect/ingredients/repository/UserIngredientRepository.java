package com.qnnect.ingredients.repository;

import com.qnnect.ingredients.domain.EIngredientType;
import com.qnnect.ingredients.domain.Ingredient;
import com.qnnect.ingredients.domain.UserIngredient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {
    @Query("SELECT m,COUNT(i.name) FROM UserIngredient m INNER JOIN m.ingredient i WHERE m.user.id=:userId GROUP BY i.name")
    List<Object[]> countByIngredientWhereUser_Id(@Param(value="userId") UUID userId);

    @Query("SELECT m,COUNT(i.name) FROM UserIngredient m INNER JOIN m.ingredient i WHERE m.user.id=:userId AND i.ingredientType=:ingredientType GROUP BY i.name")
    List<Object[]> countByIngredientWhereUser_IdAndType(@Param(value="userId") UUID userId, @Param(value = "ingredientType")EIngredientType ingredientType);

    List<UserIngredient> getByUser_IdAndIngredient_Id(@Param(value = "userId") UUID userId, @Param(value = "ingredientId")long ingredientId, Pageable pageable);

//    @Query("DELETE m FROM UserIngredient m WHERE m.user.id=:userId AND m.ingredient.id=:ingredientId AND m.id")
//    void deleteByUser_IdAndIngredient_Id(@Param(value = "userId") UUID userId, @Param(value = "ingredientId")long ingredientId);
}
