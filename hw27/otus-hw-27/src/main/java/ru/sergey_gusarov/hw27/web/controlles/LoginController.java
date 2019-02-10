package ru.sergey_gusarov.hw27.web.controlles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/loginPage")
    public String login(Model model){
        return "loginPage";
    }

    @RequestMapping("/loginPageError")
    public String loginError(Model model){
        model.addAttribute("error", true);
        model.addAttribute("errorText", "Ошибка входа");
        return "loginPageError";
    }

    @RequestMapping("/logoutPage")
    public String logout(Model model){
        return "logoutPage";
    }
}
