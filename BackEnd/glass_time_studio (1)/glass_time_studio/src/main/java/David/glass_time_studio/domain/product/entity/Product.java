package David.glass_time_studio.domain.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column
    private String productName;
    @Column
    private String productDescription;
    @Column
    private Long productPrice;
    @Column
    private Long productQuantity;
    @Column
    private String mainPhotoUrl;
    @Column
    private String productStatus;

//    @ElementCollection
//    @CollectionTable(name = "product_photo_urls", joinColumns = @JoinColumn(name = "product_id"))
//    @Column(name = "photo_url")
//    private List<String> productPhotoUrls;

    @Column
    private String photoUrl_1;
    @Column
    private String photoUrl_2;
    @Column
    private String photoUrl_3;
    @Column
    private String photoUrl_4;
    @Column
    private String photoUrl_5;
    @Column
    private String photoUrl_6;
    @Column
    private String photoUrl_7;
    @Column
    private String photoUrl_8;
    @Column
    private String photoUrl_9;
    @Column
    private String photoUrl_10;


    public Product(Long productId, String productName, String productDescription,
                   Long productPrice, Long productQuantity,String mainPhotoUrl,
                   List<String> productPhotoUrls,
                   String photoUrl_1,String photoUrl_2,String photoUrl_3,
                   String photoUrl_4,String photoUrl_5,String photoUrl_6,
                   String photoUrl_7,String photoUrl_8,String photoUrl_9,
                   String photoUrl_10
    ){
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.mainPhotoUrl = mainPhotoUrl;
//        this.productPhotoUrls = productPhotoUrls;
        this.photoUrl_1=photoUrl_1;
        this.photoUrl_2=photoUrl_2;
        this.photoUrl_3=photoUrl_3;
        this.photoUrl_4=photoUrl_4;
        this.photoUrl_5=photoUrl_5;
        this.photoUrl_6=photoUrl_6;
        this.photoUrl_7=photoUrl_7;
        this.photoUrl_8=photoUrl_8;
        this.photoUrl_9=photoUrl_9;
        this.photoUrl_10=photoUrl_10;
    }
}
