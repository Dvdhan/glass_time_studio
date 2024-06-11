package David.glass_time_studio.domain.order.mapper;

import David.glass_time_studio.domain.order.dto.OrderDto;
import David.glass_time_studio.domain.order.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order orderDtoPostToOrder(OrderDto.Post post);
    Order orderDtoPatchToOrder(OrderDto.Patch patch);
    OrderDto.Response orderToOrderDtoResponse(Order order);
    List<OrderDto.Response> ordersToOrderDtoResponse(List<Order> orders);
}
