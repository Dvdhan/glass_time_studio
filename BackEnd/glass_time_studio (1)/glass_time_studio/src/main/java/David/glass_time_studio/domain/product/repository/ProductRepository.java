package David.glass_time_studio.domain.product.repository;

import David.glass_time_studio.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM PRODUCT ORDER BY product_id", nativeQuery = true)
    public Page<Product> findAllProduct(Pageable pageable);

    @Query(value = "SELECT * FROM PRODUCT WHERE product_name LIKE :keyword", nativeQuery = true)
    public List<Product> searchProduct(@Param("keyword") String keyword);
}
