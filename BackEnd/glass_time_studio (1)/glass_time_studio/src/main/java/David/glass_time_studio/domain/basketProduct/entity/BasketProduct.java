package David.glass_time_studio.domain.basketProduct.entity;

import David.glass_time_studio.domain.basket.entity.Basket;
import David.glass_time_studio.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BasketProduct")
public class BasketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long basketProductId;

    @ManyToOne
    @JoinColumn(name = "basketId")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

}
