package David.glass_time_studio.domain.product.mapper;

import David.glass_time_studio.domain.product.dto.ProductDto;
import David.glass_time_studio.domain.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(target = "photoUrl_1", source = "photoUrl_1"),
            @Mapping(target = "photoUrl_2", source = "photoUrl_2"),
            @Mapping(target = "photoUrl_3", source = "photoUrl_3"),
            @Mapping(target = "photoUrl_4", source = "photoUrl_4"),
            @Mapping(target = "photoUrl_5", source = "photoUrl_5"),
            @Mapping(target = "photoUrl_6", source = "photoUrl_6"),
            @Mapping(target = "photoUrl_7", source = "photoUrl_7"),
            @Mapping(target = "photoUrl_8", source = "photoUrl_8"),
            @Mapping(target = "photoUrl_9", source = "photoUrl_9"),
            @Mapping(target = "photoUrl_10", source = "photoUrl_10")
    })
    Product productDtoPostToProduct(ProductDto.Post post);

    @Mappings({
            @Mapping(target = "photoUrl_1", source = "photoUrl_1"),
            @Mapping(target = "photoUrl_2", source = "photoUrl_2"),
            @Mapping(target = "photoUrl_3", source = "photoUrl_3"),
            @Mapping(target = "photoUrl_4", source = "photoUrl_4"),
            @Mapping(target = "photoUrl_5", source = "photoUrl_5"),
            @Mapping(target = "photoUrl_6", source = "photoUrl_6"),
            @Mapping(target = "photoUrl_7", source = "photoUrl_7"),
            @Mapping(target = "photoUrl_8", source = "photoUrl_8"),
            @Mapping(target = "photoUrl_9", source = "photoUrl_9"),
            @Mapping(target = "photoUrl_10", source = "photoUrl_10")
    })
    Product productDtoPatchToProduct(ProductDto.Patch patch);

    @Mappings({
            @Mapping(target = "photoUrl_1", source = "photoUrl_1"),
            @Mapping(target = "photoUrl_2", source = "photoUrl_2"),
            @Mapping(target = "photoUrl_3", source = "photoUrl_3"),
            @Mapping(target = "photoUrl_4", source = "photoUrl_4"),
            @Mapping(target = "photoUrl_5", source = "photoUrl_5"),
            @Mapping(target = "photoUrl_6", source = "photoUrl_6"),
            @Mapping(target = "photoUrl_7", source = "photoUrl_7"),
            @Mapping(target = "photoUrl_8", source = "photoUrl_8"),
            @Mapping(target = "photoUrl_9", source = "photoUrl_9"),
            @Mapping(target = "photoUrl_10", source = "photoUrl_10")
    })
    ProductDto.Response productToProductDtoResponse(Product product);

    List<ProductDto.Response> productsToProductDtoResponse(List<Product> products);
}
