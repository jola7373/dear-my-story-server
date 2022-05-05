package com.qnnect.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),

    QUESTION_NOT_SCRAPPED(HttpStatus.NOT_FOUND, "스크랩된 질문이 아닙니다."),
    INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 재료를 찾을 수 없습니다."),
    INCORRECT_CAFE_CODE_EXCEPTION(HttpStatus.UNAUTHORIZED, "존재하지 않는 카페 코드입니다."),
    CAFE_DRINK_NOT_SELECTED_EXCEPTION(HttpStatus.UNAUTHORIZED, "음료를 선택하지 않아 답변할 수 없습니다."),
    CAFE_MEMBER_EXCEED_EXCEPTION(HttpStatus.NOT_ACCEPTABLE, "카페가 이미 다 찼어요! 5명까지 카페 참여가 가능합니다."),
    CAFE_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "카페에서 찾을 수 없는 질문입니다."),
    POINT_NOT_ENOUGH(HttpStatus.NOT_ACCEPTABLE, "포인트가 부족합니다"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "답변을 찾을 수 없습니다"),
    CAFE_QUESTION_DATE_PASSED( HttpStatus.LOCKED , "질문을 답변할 수 있는 기간이 지났습니다"),
    WRONG_INGREDIENT_SAME_LEVEL( HttpStatus.NOT_ACCEPTABLE , "앗! 잘못 넣었어요. 레시피를 한번 더 확인해보세요."),
    WRONG_INGREDIENT_DIFFERENT_LEVEL( HttpStatus.NOT_ACCEPTABLE , "해당 주재료의 단계가 아닙니다!"),
    WRONG_INGREDIENT_HIGHER_LEVEL(HttpStatus.NOT_ACCEPTABLE, "을 넣을 단계에요"),
    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 코드를 가진 카페를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 알림을 찾을 수 없습니다."),
    FCM_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "FCM 토큰을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}


