package opgg.opgg.controller;


import opgg.opgg.config.Version;
import opgg.opgg.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;


@Controller
public class UserController {
    final static String API_KEY = "RGAPI-520f99f7-0f5a-4dbc-9971-48f5838e4213";

    @RequestMapping(value="/search", method= RequestMethod.GET)
    public String searchUser(HttpServletRequest httpServletRequest, Model model){
        Version.version();
        BufferedReader bufferedReader = null;
        User temporary = null;
        return "";
    }


}
