package David.glass_time_studio.domain.order.controller;

import David.glass_time_studio.domain.order.dto.OrderDto;
import David.glass_time_studio.domain.order.entity.Order;
import David.glass_time_studio.domain.order.entity.OrderStatus;
import David.glass_time_studio.domain.order.mapper.OrderMapper;
import David.glass_time_studio.domain.order.service.OrderService;
import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    public OrderController (OrderService orderService, OrderMapper orderMapper){
        this.orderService=orderService;
        this.orderMapper=orderMapper;
    }

    // 주문 생성
    @PostMapping
    public ResponseEntity postOrder(@RequestBody @Valid OrderDto.Post post) throws Exception{
        log.info("Post DTO: {}", post);
        Order order = orderMapper.orderDtoPostToOrder(post);
        Order newOrder = orderService.postOrder(order);
        OrderDto.Response response = orderMapper.orderToOrderDtoResponse(newOrder);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity(response, headers, HttpStatus.CREATED);
    }
    // 내 주문 전체 조회
    @GetMapping("/allMyOrders/{memberId}")
    public ResponseEntity allMyOrders(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size,
                                      @PathVariable("memberId")@Positive Long memberId){
        log.info("내 주문 조회 memberId: "+memberId);
        Page<Order> orders = orderService.findAllMyOrders(page-1, size, memberId);

        PageInfo pageInfo = new PageInfo(
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalElements(),
                orders.getTotalPages());
        List<Order> orderList = orders.getContent();
        List<OrderDto.Response> responses = orderMapper.ordersToOrderDtoResponse(orderList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }
    // 내 주문 단일 조회
    @GetMapping("/{memberId}/{orderId}")
    public ResponseEntity findMyOrder (@PathVariable("memberId")@Positive Long memberId,
                                       @PathVariable("orderId")@Positive Long orderId) {
        log.info("내 주문 단일 조회 - memberId: "+memberId+"orderId: "+orderId);
        Order order = orderService.findMyOrder(memberId, orderId);
        OrderDto.Response response = orderMapper.orderToOrderDtoResponse(order);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 전체 주문 조회 [관리자용]
    @GetMapping("/all")
    public ResponseEntity allOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Order> orders = orderService.findAllOrders(page-1, size);
        PageInfo pageInfo = new PageInfo(
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalElements(),
                orders.getTotalPages());
        List<Order> orderList = orders.getContent();
        List<OrderDto.Response> responses = orderMapper.ordersToOrderDtoResponse(orderList);
        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }
    // 주문 취소
    @DeleteMapping("/{memberId}/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("memberId")@Positive Long memberId,
                                      @PathVariable("orderId")@Positive Long orderId) {
        log.info("내 주문 단일 조회 - memberId: "+memberId+", orderId: "+orderId);
        Order order = orderService.findMyOrder(memberId, orderId);

        OrderStatus[] invalidCancelArray = {OrderStatus.MAKING, OrderStatus.SHIPPING, OrderStatus.DELIVERED};
        Map<String, String> responseMessage = new HashMap<>();

        for (OrderStatus status : invalidCancelArray){
            if(order.getOrderStatus() == status){
                responseMessage.put("message", "["+order.getMemberName()+"]님이 요청하신, 주문번호 ["+orderId+"] - ["+order.getProductName()+"]의 주문은 ["+status+"] 단계라 취소 불가능 합니다.\n" +
                        "판매자(010-1234-1234)에게 직접 연락주세요.");
                return ResponseEntity.ok(responseMessage);
            }
        }
        orderService.deleteReq(order, memberId);
        responseMessage.put("message", "["+order.getMemberName()+"]님이 요청하신, 주문번호 ["+orderId+"] - ["+order.getProductName()+"]의 주문이 취소되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
    // 주문 상태 변경 [관리자용]
    @PatchMapping("/updateStatus/{orderId}")
    public ResponseEntity updateOrderStatus(@PathVariable("orderId")@Positive Long orderId,
                                            @RequestBody OrderDto.Patch patch){
        log.info("주문 상태 변경 orderId: "+orderId);
        log.info("Patch DTO {}", patch);
        Order order = orderMapper.orderDtoPatchToOrder(patch);
        Order updateOrder = orderService.updateOrderStatus(order, orderId);
        OrderDto.Response response = orderMapper.orderToOrderDtoResponse(updateOrder);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
