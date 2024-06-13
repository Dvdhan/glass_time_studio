package David.glass_time_studio.domain.orderProduct.repository;

import David.glass_time_studio.domain.orderProduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
