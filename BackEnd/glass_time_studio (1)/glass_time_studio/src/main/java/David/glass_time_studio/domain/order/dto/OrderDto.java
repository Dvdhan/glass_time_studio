package David.glass_time_studio.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post{
        Long memberId;
        String memberName;
        String mobile;
        Long productId;
        String address;
        Long productQuantity;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch{
        Long orderId;
        Long memberId;
        String mobile;
        String address;
        Long productQuantity;
        String orderStatus;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        Long orderId;
        Long memberId;
        String memberName;
        String mobile;
        String address;
        Long productId;
        String productName;
        Long productPrice;
        Long productQuantity;
        String orderStatus;
    }
}
