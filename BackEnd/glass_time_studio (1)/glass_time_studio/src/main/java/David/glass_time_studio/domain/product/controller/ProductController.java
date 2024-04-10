package David.glass_time_studio.domain.product.controller;

import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import David.glass_time_studio.domain.product.dto.ProductDto;
import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.mapper.ProductMapper;
import David.glass_time_studio.domain.product.repository.ProductRepository;
import David.glass_time_studio.domain.product.service.ProductService;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper){
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // 상품 등록
    @Transactional
    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductDto.Post post) throws Exception{
        Product product = productMapper.productDtoPostToProduct(post);
        Product postedProduct = productService.postProduct(product);
        ProductDto.Response response = productMapper.productToProductDtoResponse(postedProduct);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity(response, headers, HttpStatus.CREATED);
    }

    // 단일 상품 조회
    @GetMapping("/{product_Id}")
    public ResponseEntity findProduct(@PathVariable("product_Id")@Positive Long product_id) throws Exception{
        Product product = productService.findProduct(product_id);
        ProductDto.Response response = productMapper.productToProductDtoResponse(product);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 상품 전체 조회
    @GetMapping("/all")
    public ResponseEntity findAllProduct(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size) {
        Page<Product> products = productService.findAllProduct(page-1, size);

        PageInfo pageInfo = new PageInfo(
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages());
        List<Product> productList = products.getContent();
        List<ProductDto.Response> responses = productMapper.productsToProductDtoResponse(productList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }

    // 단일 상품 수정
    @PatchMapping("/{product_Id}")
    public ResponseEntity updateProduct(@PathVariable("product_Id")@Positive Long product_id,
                                        @RequestBody ProductDto.Patch patch){
        Product product = productMapper.productDtoPatchToProduct(patch);
        Product updatedProduct = productService.updateProduct(product, product_id);
        ProductDto.Response response = productMapper.productToProductDtoResponse(updatedProduct);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 단일 상품 삭제
    @DeleteMapping("/{product_Id}")
    public ResponseEntity deleteProduct(@PathVariable("product_Id")@Positive Long product_id) throws Exception{
        Product product = productService.findProduct(product_id);
        productService.deleteProduct(product);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message","요청하신 '"+product.getProduct_Name()+"' 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
}
