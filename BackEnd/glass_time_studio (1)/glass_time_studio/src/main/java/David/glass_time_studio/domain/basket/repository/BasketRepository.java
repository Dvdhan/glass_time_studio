package David.glass_time_studio.domain.basket.repository;

import David.glass_time_studio.domain.basket.entity.Basket;
import David.glass_time_studio.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Query(value = "SELECT * FROM basket WHERE member_id = :memberId AND product_id = :productId", nativeQuery = true)
    Basket findBasketByMemberIdAndProductId(@Param("memberId")Long memberId, @Param("productId") Long productId);

    @Query(value = "SELECT * FROM BASKET ORDER BY basket_id", nativeQuery = true)
    public Page<Basket> findAllBasket(Pageable pageable);

    @Query(value = "SELECT * FROM BASKET WHERE member_id = :memberId", nativeQuery = true)
    public Page<Basket> findAllMyBasket(Pageable pageable, @Param("memberId")Long memberId);
}
