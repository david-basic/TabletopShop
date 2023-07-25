package hr.algebra.tabletopshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicHomeController {
    
    @GetMapping("/home.html")
    public String openPublicHomePage() {
        return "home";
    }
}
