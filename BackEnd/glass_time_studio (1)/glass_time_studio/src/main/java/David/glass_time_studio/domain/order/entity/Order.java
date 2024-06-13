package David.glass_time_studio.domain.order.entity;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.orderProduct.entity.OrderProduct;
import David.glass_time_studio.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

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
    private String address;

    @Column
    private String productName;

    @Column
    private Long productPrice;

    @Column
    private Long productQuantity;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public String getOrderStatusDescription(){
        return orderStatus.getDescription();
    }

//    public void addOrderProduct(OrderProduct orderProduct){
//        orderProducts.add(orderProduct);
//        orderProduct.setOrder(this);
//    }
}
