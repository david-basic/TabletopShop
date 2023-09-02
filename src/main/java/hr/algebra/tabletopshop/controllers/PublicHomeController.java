package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.dto.CartItemDto;
import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/public")
@AllArgsConstructor
@SessionAttributes("publicItems")
public class PublicHomeController {
    private ItemRepositoryMongo itemRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    private final Cart cart;
    private List<Item> itemsToDisplay;
    
    @GetMapping("/home")
    public ModelAndView openPublicHomePage(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        if (!Objects.nonNull(authentication)) {
            customSpringEventPublisher.publishCustomEvent("Anonymous entered public home page!");
            
            mav.addObject("publicItems", itemRepositoryMongo.findAll());
            mav.addObject("cartItemDto", new CartItemDto());
            mav.addObject("cartItemCount", cart.getCartItems().size());
            
            mav.setViewName("home");
        } else {
            mav.setViewName("redirect:/store/homePage");
        }
        return mav;
    }
    
    @GetMapping("/browse")
    public ModelAndView openPublicBrowserPage(Authentication authentication) {
        ModelAndView mav = new ModelAndView();
        if (!Objects.nonNull(authentication)) {
            customSpringEventPublisher.publishCustomEvent("Anonymous entered public browse page!");
            
            mav.addObject("cartItemDto", new CartItemDto());
            mav.addObject("cartItemCount", cart.getCartItems().size());
            
            //ako user dodje direktno na browse stranicu ili ako nije nađen niti jedan item sa traženom kategorijom
            if (itemsToDisplay.isEmpty()) {
                mav.addObject("publicItems", itemRepositoryMongo.findAll());
                mav.addObject("itemsToDisplay", itemRepositoryMongo.findAll());
            } else {
                mav.addObject("itemsToDisplay", itemsToDisplay);
            }
            
            mav.setViewName("browse");
        } else {
            mav.setViewName("redirect:/store/browse");
        }
        return mav;
    }
    
    @PostMapping("/categories")
    public ModelAndView findItemsInCategory(@RequestParam("selectedCategory") String categoryChosen) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Anonymous browsed by category!");
        
        itemsToDisplay = new ArrayList<>();
        
        if (Objects.equals(categoryChosen, "all")) {
            itemsToDisplay.addAll(itemRepositoryMongo.findAll());
        } else {
            itemsToDisplay.addAll(itemRepositoryMongo.findAllByCategory(categoryChosen));
        }
        
        mav.setViewName("redirect:/public/browse");
        return mav;
    }
}
