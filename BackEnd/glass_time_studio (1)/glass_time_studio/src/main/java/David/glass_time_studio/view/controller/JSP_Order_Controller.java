package David.glass_time_studio.view.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class JSP_Order_Controller {
    @Value("${app.api.endpoint}")
    private String apiEndPoint;
}
