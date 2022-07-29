package opgg.opgg.web.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    //홈 화면으로(Index)
    @GetMapping
    public String home() {
        return "home";
    }

}
