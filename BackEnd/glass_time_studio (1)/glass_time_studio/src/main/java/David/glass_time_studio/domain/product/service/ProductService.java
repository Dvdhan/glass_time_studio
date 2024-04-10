package David.glass_time_studio.domain.product.service;

import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.repository.ProductRepository;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Getter
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

    public Product findProduct(Long product_Id){
        Optional<Product> findProduct = productRepository.findById(product_Id);
        Product foundProduct = findProduct.orElseThrow(()->new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
        return foundProduct;
    }
    public Page<Product> findAllProduct(int page, int size){
        return productRepository.findAllProduct(PageRequest.of(page, size));
    }


    public Product updateProduct(Product product, Long product_Id){
        Product target = findProduct(product_Id);

        String new_Product_Name = product.getProduct_Name();
        String new_Product_Description = product.getProduct_Description();
        Long new_Product_Price = product.getProduct_Price();
        Long new_Product_Quantity = product.getProduct_Quantity();

        boolean isUpdated = false;

        if(new_Product_Name != null){
            target.setProduct_Name(new_Product_Name);
            isUpdated = true;
        }
        if(new_Product_Description != null){
            target.setProduct_Description(new_Product_Description);
            isUpdated = true;
        }
        if(new_Product_Price != null ){
            target.setProduct_Price(new_Product_Price);
            isUpdated = true;
        }
        if(new_Product_Quantity != null){
            target.setProduct_Quantity(new_Product_Quantity);
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
}
