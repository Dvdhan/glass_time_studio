package David.glass_time_studio.domain.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_Id;
    @Column
    private String product_Name;
    @Column
    private String product_Description;
    @Column
    private Long product_Price;
    @Column
    private Long product_Quantity;

    public Product(Long product_Id, String product_Name, String product_Description,
                   Long product_Price, Long product_Quantity){
        this.product_Id = product_Id;
        this.product_Name = product_Name;
        this.product_Description = product_Description;
        this.product_Price = product_Price;
        this.product_Quantity = product_Quantity;
    }
}
