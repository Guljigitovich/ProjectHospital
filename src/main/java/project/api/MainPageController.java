package project.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {
    @GetMapping
    public String mainPage() {
        return "/hospital/mainPage";
    }

//    @GetMapping("/open")
//    public String openPage(){
//        return "hospital/open";
//    }


}
