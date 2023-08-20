package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/public")
@AllArgsConstructor
@SessionAttributes("publicItems")
public class PublicHomeController {
    private ItemRepositoryMongo itemRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    
    @GetMapping("/home")
    public String openPublicHomePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Anonymous entered public home page!");
        model.addAttribute("publicItems", itemRepositoryMongo.findAll());
        return "home";
    }
    
    @GetMapping("/browse")
    public String openPublicBrowserPage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Anonymous entered public browse page!");
        model.addAttribute("publicItems", itemRepositoryMongo.findAll());
        return "browse";
    }
}
