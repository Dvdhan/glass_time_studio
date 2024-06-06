package David.glass_time_studio.domain.basket.repository;

import David.glass_time_studio.domain.basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
}
