package com.qnnect.cafe.dto;

import com.qnnect.cafe.domain.Cafe;
import com.qnnect.cafe.domain.EDiaryColor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CafeMainResponse {
    private Long id;
    private String title;
    private LocalDate createdAt;
    private int cafeUserNum;
    private EDiaryColor diaryColor;


    public static CafeMainResponse from(Cafe cafe) {
        return CafeMainResponse.builder()
                .id(cafe.getId())
                .title(cafe.getTitle())
                .diaryColor(cafe.getDiaryColor())
                .createdAt(cafe.getCreatedAt().toLocalDate())
                .cafeUserNum(cafe.getCafeUsers().size())
                .build();
    }

    public static List<CafeMainResponse> listFrom(List<Cafe> cafeList) {
        if(cafeList == null){
            return null;
        }

        List<CafeMainResponse> cafeMainResponseList = new ArrayList<>();

        for(Cafe cafe:cafeList){
            cafeMainResponseList.add(from(cafe));
        }

        return cafeMainResponseList;
    }
}





