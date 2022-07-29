package opgg.opgg.web.controller.user;

import lombok.RequiredArgsConstructor;
import opgg.opgg.service.summoner.UserService;
import opgg.opgg.web.dto.user.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입 페이지
    @GetMapping("/auth/join")
    public String join(Model model) {
        model.addAttribute("user", UserDTO.builder().build());
        return "user/join";
    }

    //회원가입 처리
    @PostMapping("/auth/joinProc")
    public String joinProc(UserDTO userDTO){
        userService.join(userDTO);
        return "redirect:/";
    }

    //로그인
    @GetMapping("/auth/login")
    public String login() {
        return "user/login";
    }

    //로그인
    @GetMapping("/loginProc")
    public String loginSuccess() {


        return "home";
    }
}
