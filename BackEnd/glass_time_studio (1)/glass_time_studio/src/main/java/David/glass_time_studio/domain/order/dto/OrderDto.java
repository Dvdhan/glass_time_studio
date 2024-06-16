package David.glass_time_studio.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
        Long orderPayment;
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
        Long orderPayment;
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modified_at;

        Long orderPayment;
    }
}
