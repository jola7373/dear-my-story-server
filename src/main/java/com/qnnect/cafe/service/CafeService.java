package com.qnnect.cafe.service;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.dto.CafeRequest;
import com.qnnect.cafe.dto.CafeDetailResponse;
import com.qnnect.user.domain.User;

public interface CafeService {
    public Cafe createCafe(CafeRequest cafeCreateRequest,
                           User user);
    CafeDetailResponse getCafe(Long cafeId, User user);
    CafeDetailResponse joinCafe(String code, User user);
    void deleteCafe(Long cafeId, User user);
    public Cafe updateCafe(Long cafeId, CafeRequest cafeCreateRequest, User user);
    public void leaveCafe(Long cafeId, User user);
}
