package com.qnnect.cafe.repository;


import com.qnnect.cafe.domain.Cafe;
import com.qnnect.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    @Query("SELECT c FROM Cafe c WHERE c.id = ?1")
    Cafe getNowById(Long id);


    Optional<Cafe> findByCode(@Param(value = "code")String code);
}
