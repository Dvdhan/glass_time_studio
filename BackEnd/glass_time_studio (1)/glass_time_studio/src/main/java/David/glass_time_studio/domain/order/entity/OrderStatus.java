package David.glass_time_studio.domain.order.entity;

public enum OrderStatus {

    ORDERED(1, "결제 완료"),
    CHECKING(2,"주문서 확인"),
    CONTACT(3, "주문 확인 연락"),
    MAKING(4, "제작"),
    SHIPPING(5, "배송"),
    DELIVERED(6, "배송 완료"),
    CANCELED(7, "주문 취소");

    int code;
    String description;
    OrderStatus(int code, String description){
        this.code = code;
        this.description=description;
    }

    public int getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }
}
