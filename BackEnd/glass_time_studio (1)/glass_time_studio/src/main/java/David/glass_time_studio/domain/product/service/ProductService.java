package David.glass_time_studio.domain.product.service;

import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.repository.ProductRepository;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Slf4j
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public Product postProduct(Product product){
        Product postedProduct = productRepository.save(product);
        return postedProduct;
    }



    public Product findProduct(Long productId){
        Optional<Product> findProduct = productRepository.findById(productId);
        Product foundProduct = findProduct.orElseThrow(()->new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
        return foundProduct;
    }
    public Page<Product> findAllProduct(int page, int size){
        return productRepository.findAllProduct(PageRequest.of(page, size));
    }


    public Product updateProduct(Product product, Long productId){
        log.info("수정제품 ID: "+productId);
        Product target = findProduct(productId);

        log.info("수정 요청 product{}", product);

        String new_Product_Name = product.getProductName();
        log.info("수정 요청 Name: "+new_Product_Name);

        String new_Product_Description = product.getProductDescription();
        log.info("수정 요청 Description: "+new_Product_Description);

        Long new_Product_Price = product.getProductPrice();
        log.info("수정 요청 Price: "+new_Product_Price);

        Long new_Product_Quantity = product.getProductQuantity();
        log.info("수정 요청 Quantity: "+new_Product_Quantity);

        String new_MainPhotoUrl = product.getMainPhotoUrl();
        log.info("수정 요청 MainPhotoUrl: "+new_MainPhotoUrl);

        String new_Product_Status = product.getProductStatus();
        log.info("수정 요청 Status: "+new_Product_Status);

        String new_PhotoUrl_1 = product.getPhotoUrl_1();
        log.info("수정 요청 new_PhotoUrl_1: "+new_PhotoUrl_1);

        String new_PhotoUrl_2 = product.getPhotoUrl_2();
        log.info("수정 요청 new_PhotoUrl_2: "+new_PhotoUrl_2);

        String new_PhotoUrl_3 = product.getPhotoUrl_3();
        log.info("수정 요청 new_PhotoUrl_3: "+new_PhotoUrl_3);

        String new_PhotoUrl_4 = product.getPhotoUrl_4();
        log.info("수정 요청 new_PhotoUrl_4: "+new_PhotoUrl_4);

        String new_PhotoUrl_5 = product.getPhotoUrl_5();
        log.info("수정 요청 new_PhotoUrl_5: "+new_PhotoUrl_5);

        String new_PhotoUrl_6 = product.getPhotoUrl_6();
        log.info("수정 요청 new_PhotoUrl_6: "+new_PhotoUrl_6);

        String new_PhotoUrl_7 = product.getPhotoUrl_7();
        log.info("수정 요청 new_PhotoUrl_7: "+new_PhotoUrl_7);

        String new_PhotoUrl_8 = product.getPhotoUrl_8();
        log.info("수정 요청 new_PhotoUrl_8: "+new_PhotoUrl_8);

        String new_PhotoUrl_9 = product.getPhotoUrl_9();
        log.info("수정 요청 new_PhotoUrl_9: "+new_PhotoUrl_9);

        String new_PhotoUrl_10 = product.getPhotoUrl_10();
        log.info("수정 요청 new_PhotoUrl_10: "+new_PhotoUrl_10);

        boolean isUpdated = false;
        if(new_Product_Name != null){
            target.setProductName(new_Product_Name);
            isUpdated = true;
        }
        if(new_Product_Description != null){
            target.setProductDescription(new_Product_Description);
            isUpdated = true;
        }
        if(new_Product_Price != null ){
            target.setProductPrice(new_Product_Price);
            isUpdated = true;
        }
        if(new_Product_Quantity != null){
            target.setProductQuantity(new_Product_Quantity);
            isUpdated = true;
        }
        if(new_MainPhotoUrl != null){
            target.setMainPhotoUrl(new_MainPhotoUrl);
            isUpdated = true;
        }
        if(new_Product_Status != null){
            target.setProductStatus(new_Product_Status);
            isUpdated = true;
        }
        if(new_PhotoUrl_1 != null){
            target.setPhotoUrl_1(new_PhotoUrl_1);
            isUpdated = true;
        }
        if(new_PhotoUrl_2 != null){
            target.setPhotoUrl_2(new_PhotoUrl_2);
            isUpdated = true;
        }
        if(new_PhotoUrl_3 != null){
            target.setPhotoUrl_3(new_PhotoUrl_3);
            isUpdated = true;
        }
        if(new_PhotoUrl_4 != null){
            target.setPhotoUrl_4(new_PhotoUrl_4);
            isUpdated = true;
        }
        if(new_PhotoUrl_5 != null){
            target.setPhotoUrl_5(new_PhotoUrl_5);
            isUpdated = true;
        }
        if(new_PhotoUrl_6 != null){
            target.setPhotoUrl_6(new_PhotoUrl_6);
            isUpdated = true;
        }
        if(new_PhotoUrl_7 != null){
            target.setPhotoUrl_7(new_PhotoUrl_7);
            isUpdated = true;
        }
        if(new_PhotoUrl_8 != null){
            target.setPhotoUrl_8(new_PhotoUrl_8);
            isUpdated = true;
        }
        if(new_PhotoUrl_9 != null){
            target.setPhotoUrl_9(new_PhotoUrl_9);
            isUpdated = true;
        }
        if(new_PhotoUrl_10 != null){
            target.setPhotoUrl_10(new_PhotoUrl_10);
            isUpdated = true;
        }
        if(isUpdated){
            return productRepository.save(target);
        }else {
            return target;
        }
    }

    public void deleteProduct (Product product){
        productRepository.delete(product);
    }

    public List<Product> searchProductByName(String keyword){
        return productRepository.searchProduct("%" + keyword + "%");
    }

}
