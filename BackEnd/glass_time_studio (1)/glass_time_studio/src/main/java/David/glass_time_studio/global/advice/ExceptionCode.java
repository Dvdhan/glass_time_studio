package David.glass_time_studio.global.advice;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    PRODUCT_NOT_FOUND(400, "해당하는 상품이 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(400,"메서드가 잘못되었습니다."),
    FIELD_MUST_BE_FULFILLED(400,"필요한 데이터가 부족합니다."),
    LECTURE_NOT_FOUND(400, "해당하는 클래스가 존재하지 않습니다."),
    ANNOUNCEMENT_NOT_FOUND(400, "해당하는 공지사항이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(400, "찾으시는 회원이 없습니다."),
    BOOKING_NOT_FOUND(400, "찾으시는 예약이 존재하지 않습니다."),
    BASKET_NOT_FOUND(400, "찾으시는 장바구니 내역이 없습니다."),
    ALREADY_EXISTS(400, "이미 장바구니에 등록되어 있습니다."),
    BASKET_MEMBER_ID_AND_MEMBER_ID_IS_DIFFERENT(400, "요청하신 장바구니의 아이디와 요청한 회원이 다릅니다.")
    ;

    @Getter
    int status;

    @Getter
    String message;

    ExceptionCode(int status, String message){
        this.status=status;
        this.message=message;
    }
}
