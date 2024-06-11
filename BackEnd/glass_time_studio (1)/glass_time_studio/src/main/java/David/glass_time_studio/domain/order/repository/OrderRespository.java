package David.glass_time_studio.domain.order.repository;

import David.glass_time_studio.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRespository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM ORDERS WHERE order_Id = :orderId", nativeQuery = true)
    public Order findByOrderId(@Param("orderId") Long orderId);

    @Query(value = "SELECT * FROM ORDERS WHERE member_id = :memberId", nativeQuery = true)
    public Page<Order> findAllMyOrders(Pageable pageable, @Param("memberId")Long memberId);

    @Query(value = "SELECT * FROM ORDERS order by order_id desc", nativeQuery = true)
    public Page<Order> findAllOrders(Pageable pageable);

    @Query(value = "SELECT * FROM ORDERS WHERE member_id = :memberId AND order_id = :orderId", nativeQuery = true)
    public Order findMyOrder(@Param("memberId") Long memberId, @Param("orderId") Long orderId);
}
