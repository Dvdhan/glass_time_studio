package David.glass_time_studio.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ProductDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Post{
        @NotBlank
        String product_Name;
        @NotBlank
        String product_Description;
        Long product_Price;
        Long product_Quantity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Response{
        long product_Id;
        String product_Name;
        String product_Description;
        String product_Price;
        String product_Quantity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Put{
        String product_Name;
        String product_Description;
        Long product_Price;
        Long product_Quantity;
    }
}
