package David.glass_time_studio.domain.basket.controller;

import David.glass_time_studio.domain.basket.dto.BasketDto;
import David.glass_time_studio.domain.basket.entity.Basket;
import David.glass_time_studio.domain.basket.mapper.BasketMapper;
import David.glass_time_studio.domain.basket.service.BasketService;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.domain.page.MultiResponse;
import David.glass_time_studio.domain.page.PageInfo;
import David.glass_time_studio.domain.product.dto.ProductDto;
import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/basket")
@Slf4j
public class BasketController {

    private BasketService basketService;
    private BasketMapper basketMapper;
    private MemberService memberService;
    private ProductService productService;
    public BasketController(BasketService basketService, BasketMapper basketMapper,
                            MemberService memberService, ProductService productService){
        this.basketService=basketService;
        this.basketMapper=basketMapper;
        this.memberService=memberService;
        this.productService=productService;
    }
    // 장바구니 추가
    @Transactional
    @PostMapping
    public ResponseEntity postBasket(@RequestBody @Valid BasketDto.Post post) throws Exception{
        log.info("Post DTO: {}", post);
        log.info("장바구니 추가 memberId: "+post.getMemberId());
        log.info("장바구니 추가 productId: "+post.getProductId());
        Basket basket = basketMapper.basketDtoPostToBasket(post);
        Basket postedBasket = basketService.postBasket(basket);
        BasketDto.Response response = basketMapper.basketToBasketDtoResponse(postedBasket);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity(response, headers, HttpStatus.CREATED);
    }
    // 징바구니 삭제
    @DeleteMapping("/{memberId}/{basketId}")
    public ResponseEntity deleteBasket(@PathVariable("memberId")@Positive Long memberId,
                                       @PathVariable("basketId")@Positive Long basketId){
        Basket target = basketService.findBasket(basketId);
        basketService.deleteReq(target, memberId);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "["+target.getMemberName()+"]님의 요청하신 ["+target.getProductName()+"]의 장바구니 삭제가 완료되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }
    // 장바구니 전체 조회
    @GetMapping("/all")
    public ResponseEntity findAllBasket(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size) {
        Page<Basket> baskets = basketService.findAllBasket(page-1, size);

        PageInfo pageInfo = new PageInfo(
                baskets.getNumber(),
                baskets.getSize(),
                baskets.getTotalElements(),
                baskets.getTotalPages());
        List<Basket> basketList = baskets.getContent();
        List<BasketDto.Response> responses = basketMapper.basketsToBasketDtoResponse(basketList);

        return new ResponseEntity(
                new MultiResponse<>(responses, pageInfo),HttpStatus.OK);
    }
    // 장바구니 개별 조회
    @GetMapping("/{basketId}")
    public ResponseEntity findBasket(@PathVariable("basketId")@Positive Long basketId) throws Exception{
        Basket basket = basketService.findBasket(basketId);
        BasketDto.Response response = basketMapper.basketToBasketDtoResponse(basket);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
