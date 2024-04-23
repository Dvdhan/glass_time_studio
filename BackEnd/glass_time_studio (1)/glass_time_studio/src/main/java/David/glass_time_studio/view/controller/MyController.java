package David.glass_time_studio.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/main")
    public String index(Model model){
        return "index";
    }
    @GetMapping("/announcement")
    public String evenAnnouncement(){
        return "layouts/announcement/event_announcement";
    }
    @GetMapping("/class")
    public String load_class(){
        return "layouts/class/class";
    }
    @GetMapping("/product")
    public String to_product(){
        return "layouts/product/product";
    }
    @GetMapping("/review")
    public String to_review(){
        return "layouts/review/review";
    }
    @GetMapping("/reservation")
    public String to_reservation(){
        return "layouts/reservation/reservation";
    }
    @GetMapping("/product_detail")
    public String product_detail(){
        return "layouts/product/product_detail";
    }
}
