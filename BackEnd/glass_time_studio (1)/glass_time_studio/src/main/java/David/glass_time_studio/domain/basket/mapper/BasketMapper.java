package David.glass_time_studio.domain.basket.mapper;

import David.glass_time_studio.domain.basket.dto.BasketDto;
import David.glass_time_studio.domain.basket.entity.Basket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    Basket basketDtoPostToBasket(BasketDto.Post post);
    BasketDto.Response basketToBasketDtoResponse(Basket basket);
    List<BasketDto.Response> basketsToBasketDtoResponse(List<Basket> baskets);
}
