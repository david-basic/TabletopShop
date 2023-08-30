package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.dto.CartItemDto;
import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import hr.algebra.tabletopshop.service.CartService;
import hr.algebra.tabletopshop.service.ItemService;
import hr.algebra.tabletopshop.service.ItemValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/store")
@AllArgsConstructor
@SessionAttributes("items")
public class HomeController {
    private ItemRepositoryMongo itemRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    private ItemValidationService itemValidationService;
    private ItemService itemService;
    private CartService cartService;
    private List<Item> itemsToDisplay;
    
    @GetMapping("/homePage")
    public ModelAndView getStoreHomePage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
        mav.addObject("items", itemRepositoryMongo.findAll());
        mav.addObject("item", new CreateItemFormDto());
        mav.addObject("cartItemDto", new CartItemDto());
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.setViewName("homePage");
        return mav;
    }
    
    @GetMapping("/browse")
    public ModelAndView getBrowsePage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Authenticated user entered browse page!");
        
        mav.addObject("cartItemDto", new CartItemDto());
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        
        //ako user dodje direktno na browse stranicu ili ako nije nađen niti jedan item sa traženom kategorijom
        if (itemsToDisplay.isEmpty()) {
            mav.addObject("items", itemRepositoryMongo.findAll());
            mav.addObject("itemsToDisplay", itemRepositoryMongo.findAll());
        } else {
            mav.addObject("itemsToDisplay", itemsToDisplay);
        }
        
        mav.setViewName("browse");
        return mav;
    }
    
    @PostMapping("/categories")
    public ModelAndView findItemsInCategory(@RequestParam("selectedCategory") String categoryChosen) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Authenticated user browsed by category!");
        
        itemsToDisplay = new ArrayList<>();
        
        if (Objects.equals(categoryChosen, "all")) {
            itemsToDisplay.addAll(itemRepositoryMongo.findAll());
        } else {
            itemsToDisplay.addAll(itemRepositoryMongo.findAllByCategory(categoryChosen));
        }
        
        mav.setViewName("redirect:/store/browse");
        return mav;
    }
    
    @PostMapping("/admin/newItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveNewItem(@ModelAttribute @Valid CreateItemFormDto formItemDto, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        String duplicateError = itemValidationService.validateDuplicateItem(formItemDto, itemRepositoryMongo.findAll());
        
        if (!duplicateError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", duplicateError);
            bindingResult.addError(error);
        }
        
        if (bindingResult.hasErrors()) {
            mav.addObject("item", formItemDto);
            mav.setViewName("adminPage");
            return mav;
        }
        
        itemService.createItem(formItemDto);
        mav.setViewName("redirect:/store/admin/adminPage");
        return mav;
    }
    
    @PostMapping("/admin/deleteItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteItem(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        itemService.deleteItem(id);
        mav.setViewName("redirect:/store/admin/adminPage");
        return mav;
    }
    
    @GetMapping("/cleanSession")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/store/admin/adminPage";
    }
    
    @GetMapping("/admin/adminPage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getStoreAdminPage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Admin page opened!");
        mav.addObject("items", itemRepositoryMongo.findAll());
        mav.addObject("item", new CreateItemFormDto());
        mav.setViewName("adminPage");
        return mav;
    }
    
    @ResponseBody
    @GetMapping("/admin/getItemData")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getItemData() throws InterruptedException {
        List<Item> items = itemRepositoryMongo.findAll();
        Thread.sleep(10000);
        return "{\"message\": \"You have " + items.size() + " items in stock.\"}";
    }
}
