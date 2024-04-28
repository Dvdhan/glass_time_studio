//package David.glass_time_studio.domain.permit.controller;
//
//import David.glass_time_studio.domain.permit.repository.PermitRepository;
//import David.glass_time_studio.domain.product.service.ProductService;
//import jakarta.validation.constraints.Positive;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Validated
//@RequestMapping("/permit")
//@Slf4j
//public class PermitController {
//    @Autowired
//    private PermitRepository permitRepository;
//
//    public String findPermit(@PathVariable("memberId")@Positive Long memberId){
//        String permit = permitRepository.findPermit(memberId);
//        return permit;
//    }
//}
