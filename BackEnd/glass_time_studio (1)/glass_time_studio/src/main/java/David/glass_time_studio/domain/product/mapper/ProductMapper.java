package David.glass_time_studio.domain.product.mapper;

import David.glass_time_studio.domain.product.dto.ProductDto;
import David.glass_time_studio.domain.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product productDtoPostToProduct (ProductDto.Post post);
    Product productDtoPatchToProduct (ProductDto.Patch patch);
    ProductDto.Response productToProductDtoResponse (Product product);
    List<ProductDto.Response> productsToProductDtoResponse (List<Product> products);
}
