package David.glass_time_studio.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@ToString
public class ProductDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class Post{
        @NotBlank
        String productName;
        @NotBlank
        String productDescription;
        Long productPrice;
        Long productQuantity;
        String mainPhotoUrl;
        String productStatus;
//        List<String> productPhotoUrls;
        String photoUrl_1;
        String photoUrl_2;
        String photoUrl_3;
        String photoUrl_4;
        String photoUrl_5;
        String photoUrl_6;
        String photoUrl_7;
        String photoUrl_8;
        String photoUrl_9;
        String photoUrl_10;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class Response{
        long productId;
        String productName;
        String productDescription;
        String productPrice;
        String productQuantity;
        String mainPhotoUrl;
        String productStatus;
//        List<String> productPhotoUrls;
        String photoUrl_1;
        String photoUrl_2;
        String photoUrl_3;
        String photoUrl_4;
        String photoUrl_5;
        String photoUrl_6;
        String photoUrl_7;
        String photoUrl_8;
        String photoUrl_9;
        String photoUrl_10;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class Patch{
        long productId;
        String productName;
        String productDescription;
        Long productPrice;
        Long productQuantity;
        String mainPhotoUrl;
        String productStatus;
//        List<String> productPhotoUrls;
        String photoUrl_1;
        String photoUrl_2;
        String photoUrl_3;
        String photoUrl_4;
        String photoUrl_5;
        String photoUrl_6;
        String photoUrl_7;
        String photoUrl_8;
        String photoUrl_9;
        String photoUrl_10;
    }
}
