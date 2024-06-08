package David.glass_time_studio.domain.basket.entity;

import David.glass_time_studio.domain.basketProduct.entity.BasketProduct;
import David.glass_time_studio.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basketId")
    private Long basketId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
//    private Member member;

    @OneToMany(mappedBy = "basket")
    private List<BasketProduct> basketProducts = new ArrayList<>();

    @Column
    private Long memberId;

    @Column
    private Long productId;

    @Column
    private String memberName;

    @Column
    private String productName;

}
