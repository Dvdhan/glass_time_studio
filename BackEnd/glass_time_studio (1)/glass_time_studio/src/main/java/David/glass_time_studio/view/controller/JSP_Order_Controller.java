package David.glass_time_studio.view.controller;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.domain.order.entity.Order;
import David.glass_time_studio.domain.order.service.OrderService;
import David.glass_time_studio.domain.product.dto.ProductDto;
import David.glass_time_studio.domain.product.entity.Product;
import David.glass_time_studio.domain.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@Slf4j
public class JSP_Order_Controller {

    @Value("${app.api.endpoint}")
    private String apiEndPoint;

    private ProductService productService;
    private MemberRepository memberRepository;
    private OrderService orderService;

    public JSP_Order_Controller (ProductService productService, MemberRepository memberRepository, OrderService orderService){
        this.productService=productService;
        this.memberRepository=memberRepository;
        this.orderService=orderService;
    }

    @GetMapping("/post_order/{productId}")
    public String post_order(@PathVariable("productId")@Positive Long productId,
                                   Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Product product = productService.findProduct(productId);
        model.addAttribute("product", product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/orders/post_order";
    }
    @GetMapping("/myOrders")
    public String myOrders(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/orders/myOrders";
    }
    @GetMapping("/viewAllOrders")
    public String viewAllOrders(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }
        return "layouts/orders/ViewAllOrders";
    }
    @GetMapping("/order/{memberId}/{orderId}")
    public String viewOrderDetail(@PathVariable("orderId")@Positive Long orderId,
                                    Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
                Order order = orderService.findMyOrder(member.getMemberId(), orderId);
                if(order != null){
                    model.addAttribute("order", order);
                }else{
                    model.addAttribute("order", null);
                }
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }

        return "layouts/orders/orderDetail";
    }
    @GetMapping("/patchOrder/{orderId}")
    public String updateOrders(@PathVariable("orderId")@Positive Long orderId,
                                  Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
                Order order = orderService.findMyOrder(member.getMemberId(), orderId);
                if(order != null){
                    model.addAttribute("order", order);
                }else{
                    model.addAttribute("order", null);
                }
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }

        return "layouts/orders/updateOrders";
    }
    @GetMapping("/updateOrderManager/{orderId}")
    public String updateOrderManager(@PathVariable("orderId")@Positive Long orderId,
                               Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
                Order order = orderService.findMyOrder(member.getMemberId(), orderId);
                if(order != null){
                    model.addAttribute("order", order);
                }else{
                    model.addAttribute("order", null);
                }
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }

        return "layouts/orders/updateOrders_manager";
    }

    @GetMapping("/order/{orderId}")
    public String viewOrder(@PathVariable("orderId")@Positive Long orderId,
                               Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
                Order order = orderService.findOrderByOrderId(orderId);
                if(order != null){
                    model.addAttribute("order", order);
                }else{
                    model.addAttribute("order", null);
                }
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }

        return "layouts/orders/orderDetail_manager";
    }
    @GetMapping("/Sales_Report")
    public String Sales_Report(Model model, HttpServletRequest request){
        model.addAttribute("apiEndPoint", apiEndPoint);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if(authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            String email = String.valueOf(responseAttributes.get("email"));
            Member member = memberRepository.findMemberByEmail(email);
            if(member != null){
                model.addAttribute("member", member);

                boolean isAdmin = "ADMIN".equals(member.getPermit());
                model.addAttribute("isAdmin", isAdmin);
            }else {
                model.addAttribute("member",null);
            }
        }else {
            System.out.println("Pricipal Type: "+authentication.getPrincipal().getClass());
        }

        return "layouts/orders/Sales_Report";
    }

}
