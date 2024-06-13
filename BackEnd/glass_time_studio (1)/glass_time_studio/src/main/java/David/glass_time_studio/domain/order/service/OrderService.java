package David.glass_time_studio.domain.order.service;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.domain.order.entity.Order;
import David.glass_time_studio.domain.order.entity.OrderStatus;
import David.glass_time_studio.domain.order.repository.OrderRespository;
import David.glass_time_studio.domain.orderProduct.entity.OrderProduct;
import David.glass_time_studio.domain.orderProduct.repository.OrderProductRepository;
import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.repository.ProductRepository;
import David.glass_time_studio.domain.product.service.ProductService;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private OrderRespository orderRespository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private ProductService productService;
    private ProductRepository productRepository;
    private OrderProductRepository orderProductRepository;

    public OrderService (OrderRespository orderRespository, MemberService memberService,
                         MemberRepository memberRepository, ProductService productService,
                         ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRespository=orderRespository;
        this.memberService=memberService;
        this.memberRepository=memberRepository;
        this.productService=productService;
        this.productRepository=productRepository;
        this.orderProductRepository=orderProductRepository;
    }

    public Order postOrder(Order order){
        Long memberId = order.getMemberId();
        Long productId = order.getProductId();
        Order postOrder = setOrderPost(order, memberId, productId);
        return orderRespository.save(postOrder);
    }

    public Order setOrderPost(Order order, Long memberId, Long productId){
        Order newOrder = new Order();
        Member member = memberService.findMemberById(memberId);
        if(member == null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }else{
            newOrder.setMemberId(memberId);
        }
        Product product = productService.findProduct(productId);
        if(product == null){
            throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND);
        }else{
            newOrder.setProductId(productId);
        }
        Long productQuantity = product.getProductQuantity();
        log.info("DB 제품 재고량: "+productQuantity);

        Long requestQuantity = order.getProductQuantity();
        log.info("구매 요청의 재고량: "+requestQuantity);

        long stocks = productQuantity - requestQuantity;
        log.info("남은 재고량: "+stocks);

        // 수량
        if(stocks < 0){
            throw new BusinessLogicException(ExceptionCode.OUT_OF_STOCK);
        } else if(stocks == 0){
            product.setProductQuantity(stocks);
            product.setProductStatus("SOLD-OUT");
            productRepository.save(product);
            newOrder.setProductQuantity(requestQuantity);
        } else {
            product.setProductQuantity(stocks);
            log.info("변경된 DB 재고량: "+product.getProductQuantity());
            productRepository.save(product);
            newOrder.setProductQuantity(requestQuantity);
        }
        // 주문자 주소
        if(order.getAddress() == null){
            throw new BusinessLogicException(ExceptionCode.ADDRESS_IS_EMPTY);
        }else{
            newOrder.setAddress(order.getAddress());
        }
        // 주문자 연락처
        if(order.getMobile() == null){
            throw new BusinessLogicException(ExceptionCode.MOBILE_IS_EMPTY);
        }else {
            newOrder.setMobile(order.getMobile());
        }
        // 주문자 이름
        if(order.getMemberName() == null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NAME_IS_EMPTY);
        }else {
            newOrder.setMemberName(order.getMemberName());
        }
        // 제품명
        newOrder.setProductName(product.getProductName());
        // 제품 가격
        newOrder.setProductPrice(product.getProductPrice());
        // 주문 상태
        newOrder.setOrderStatus(OrderStatus.ORDERED);

        return newOrder;
    }
    public Page<Order> findAllMyOrders(int page, int size, Long memberId){
        Page<Order> orderPage = orderRespository.findAllMyOrders(PageRequest.of(page, size), memberId);
        if(orderPage.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }else {
            return orderPage;
        }
    }
    public Order findMyOrder (Long memberId, Long orderId){
        Order order = orderRespository.findMyOrder(memberId, orderId);
        if(order == null){
            throw new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND);
        }else {
            return order;
        }
    }
    public Page<Order> findAllOrders(int page, int size){
        Page<Order> orderPage = orderRespository.findAllOrders(PageRequest.of(page, size));
        if(orderPage.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND);
        }else {
            return orderPage;
        }
    }

    public void deleteReq(Order order, Long memberId){
        Long target_memberId = order.getMemberId();
        if(target_memberId != memberId){
            throw new BusinessLogicException(ExceptionCode.ORDER_MEMBER_ID_AND_MEMBER_ID_IS_DIFFERENT);
        }else {
            deleteOrder(order);
        }
    }
    public void deleteOrder(Order order){
        orderRespository.delete(order);
    }

    public Order updateOrderStatus(Order order, Long orderId){
        // DB 속 주문
        Order target = orderRespository.findByOrderId(orderId);
        if(target == null){
            throw new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND);
        }
        log.info("DB에 저장된 주문 상태 : "+target.getOrderStatus());
        log.info("변경하려는 주문 상태 : "+order.getOrderStatus());

        boolean isUpdated = false;
        // 상태 변경
        if(!target.getOrderStatus().equals(order.getOrderStatus())) {
            target.setOrderStatus(order.getOrderStatus());
            isUpdated = true;
        }
        // 주소 변경
        String newAddress = order.getAddress();
        if(newAddress!=null && !target.getAddress().equals(newAddress)){
            target.setAddress(newAddress);
            isUpdated = true;
        }
        // 연락처 변경
        String newMobile = order.getMobile();
        if(newMobile != null && !target.getMobile().equals(newMobile)){
            target.setMobile(newMobile);
            isUpdated = true;
        }
        // 구매 수량 변경
        Long newQuantity = order.getProductQuantity();
        if(newQuantity != null && !target.getProductQuantity().equals(newQuantity)){
            target.setProductQuantity(newQuantity);
            isUpdated = true;
        }
        if(isUpdated){
            return orderRespository.save(target);
        }
        else {
            return target;
        }
    }

}
