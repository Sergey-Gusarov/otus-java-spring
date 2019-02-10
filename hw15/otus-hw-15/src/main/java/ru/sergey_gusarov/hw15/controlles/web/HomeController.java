package ru.sergey_gusarov.hw15.controlles.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homeInit(Model model) {
        return "index";
    }
}
