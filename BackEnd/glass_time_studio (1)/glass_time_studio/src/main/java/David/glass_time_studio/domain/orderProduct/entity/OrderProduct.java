package David.glass_time_studio.domain.orderProduct.entity;

import David.glass_time_studio.domain.order.entity.Order;
import David.glass_time_studio.domain.product.entity.Product;
import jakarta.persistence.*;

@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column
    private Long quantity;

    public OrderProduct(Order order, Product product){
        this.order=order;
        this.product=product;
    }

    public void setOrder(Order order){
        this.order = order;
    }
}
