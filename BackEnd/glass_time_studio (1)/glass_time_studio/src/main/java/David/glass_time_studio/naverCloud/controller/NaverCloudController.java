package David.glass_time_studio.naverCloud.controller;

import David.glass_time_studio.naverCloud.service.NaverCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cloud")
public class NaverCloudController {

    @Autowired
    private NaverCloudService naverCloudService;

    @PostMapping("/photo/upload")
    public String uploadPhoto(@RequestParam("file")MultipartFile file) throws IOException{
        return naverCloudService.uploadFile(file);
    }
}
