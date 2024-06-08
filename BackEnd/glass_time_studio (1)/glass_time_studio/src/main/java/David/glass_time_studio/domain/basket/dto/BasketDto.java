package David.glass_time_studio.domain.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BasketDto {

    @AllArgsConstructor
    @Setter
    @Getter
    public static class Post{
        Long memberId;
        Long productId;
    }
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Response{
        Long basketId;
        Long memberId;
        String memberName;
        Long productId;
        String productName;
    }
}
