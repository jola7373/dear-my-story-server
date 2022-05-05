package com.qnnect.cafe.repository;

import com.qnnect.cafe.domain.CafeUser;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CafeUserRepository extends JpaRepository <CafeUser, Long>{
    long countByCafe_Id(@Param(value = "cafeId") long cafeId);
    CafeUser findByCafe_IdAndUser_Id(@Param(value="cafeId") long cafeId,
                                     @Param(value="userId") UUID userId);

    List<CafeUser> findAllByUser_Id(@Param(value = "userId") UUID userId);

    boolean existsByCafe_Id(@Param(value = "cafeId") long cafeId);

    List<CafeUser> findAllByCafe_Id(@Param(value = "cafeId") long cafeId);
}
