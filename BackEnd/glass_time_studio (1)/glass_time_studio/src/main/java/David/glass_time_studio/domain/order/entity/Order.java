package David.glass_time_studio.domain.order.entity;

import David.glass_time_studio.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderId;

    @Column
    private Long memberId;

    @Column
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus orderStatus;

    @Column
    private String memberName;

    @Column
    private String mobile;

    @Column
    private String productName;

    @Column
    private Long productPrice;

    @Column
    private Long productQuantity;

    public String getOrderStatusDescription(){
        return orderStatus.getDescription();
    }
}
