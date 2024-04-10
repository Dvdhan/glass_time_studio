package David.glass_time_studio.global.advice;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    PRODUCT_NOT_FOUND(400, "해당하는 상품이 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(400,"메서드가 잘못되었습니다."),
    FIELD_MUST_BE_FULFILLED(400,"필요한 데이터가 부족합니다.");

    @Getter
    int status;

    @Getter
    String message;

    ExceptionCode(int status, String message){
        this.status=status;
        this.message=message;
    }
}
