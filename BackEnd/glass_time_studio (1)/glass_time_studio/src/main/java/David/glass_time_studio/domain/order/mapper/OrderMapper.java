package David.glass_time_studio.domain.order.mapper;

import David.glass_time_studio.domain.order.dto.OrderDto;
import David.glass_time_studio.domain.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order orderDtoPostToOrder(OrderDto.Post post);
    Order orderDtoPatchToOrder(OrderDto.Patch patch);
    @Mappings({
            @Mapping(target = "created_at", source = "created_At"),
            @Mapping(target = "modified_at", source = "modified_At")
    })
    OrderDto.Response orderToOrderDtoResponse(Order order);
    List<OrderDto.Response> ordersToOrderDtoResponse(List<Order> orders);
}
