package David.glass_time_studio.domain.basket.service;

import David.glass_time_studio.domain.basket.entity.Basket;
import David.glass_time_studio.domain.basket.repository.BasketRepository;
import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.service.MemberService;
import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.service.ProductService;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@Slf4j
public class BasketService {
    private BasketRepository basketRepository;
    private MemberService memberService;
    private ProductService productService;

    public BasketService(BasketRepository basketRepository, MemberService memberService, ProductService productService){
        this.basketRepository=basketRepository;
        this.memberService=memberService;
        this.productService=productService;
    }

    // 장바구니 찾기
    public Basket findBasket(Long basketId){
        Optional<Basket> findBasket = basketRepository.findById(basketId);
        Basket foundBasket = findBasket.orElseThrow(()->new BusinessLogicException(ExceptionCode.BASKET_NOT_FOUND));
        return foundBasket;
    }
    // 장바구니 등록
    public Basket postBasket(Basket basket){
        Long memberId = basket.getMemberId();
        Member member = memberService.findMemberById(memberId); // memberId에 대한 예외 처리
        log.info("장바구니 요청한 고객명 : ["+member.getMemberName()+"]");

        Long productId = basket.getProductId();
        Product product = productService.findProduct(productId); // productId에 대한 예외 처리
        log.info("장바구니 요청한 제품명 : ["+product.getProductName()+"]");

        Basket target = basketRepository.findBasketByMemberIdAndProductId(memberId, productId);
        if(target != null){
            log.info("["+member.getMemberName()+"]님의 제품 ["+product.getProductName()+"] 에 대한 장바구니 이미 존재함");
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXISTS);
        }else {
            Basket postBasket = new Basket();
            postBasket.setMemberId(memberId);
            postBasket.setMemberName(member.getMemberName());
            postBasket.setProductId(productId);
            postBasket.setProductName(product.getProductName());
            return basketRepository.save(postBasket);
        }
    }

    public void deleteReq (Basket basket, Long memberId){
        Long target_memberId = basket.getMemberId();
        if(target_memberId != memberId){
            throw new BusinessLogicException(ExceptionCode.BASKET_MEMBER_ID_AND_MEMBER_ID_IS_DIFFERENT);
        } else {
            deleteBasket(basket);
        }
    }
    public void deleteBasket (Basket basket) {
        basketRepository.delete(basket);
    }
    public Page<Basket> findAllBasket(int page, int size){
        return basketRepository.findAllBasket(PageRequest.of(page, size));
    }
}
