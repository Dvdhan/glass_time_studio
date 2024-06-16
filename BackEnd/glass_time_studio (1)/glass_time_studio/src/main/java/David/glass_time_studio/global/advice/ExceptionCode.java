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
    BASKET_MEMBER_ID_AND_MEMBER_ID_IS_DIFFERENT(400, "해당 장바구니 제품이 속한 회원 아이디와 삭제 요청한 회원이 다릅니다."),
    ORDER_NOT_FOUND(400, "찾으시는 주문 내역이 없습니다."),
    ORDER_MEMBER_ID_AND_MEMBER_ID_IS_DIFFERENT(400, "해당 주문이 가진 회원 아이디와 삭제 요청한 회원이 다릅니다"),
    OUT_OF_STOCK(400, "구매하시고자 하는 물품양이 재고량보다 많습니다."),
    ADDRESS_IS_EMPTY(400, "제품을 수령할 주소지가 없습니다."),
    MOBILE_IS_EMPTY(400, "주문자 연락처가 누락되었습니다."),
    MEMBER_NAME_IS_EMPTY(400, "주문자 성함이 누락되었습니다."),
    REVIEW_ALREADY_EXIST(400, "해당 수업과 해당 사용자의 리뷰가 존재합니다."),
    LECTURE_HISTORY_WITH_MEMBER_ID_IS_NOT_FOUND(400,"해당 사용자 아이디로 참여한 수업이 없습니다.");
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
