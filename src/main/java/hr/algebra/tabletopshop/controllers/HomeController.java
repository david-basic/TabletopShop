package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.dto.CreateItemFormDto;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
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

import java.util.List;

@Controller
@RequestMapping("/storeHome")
@AllArgsConstructor
@SessionAttributes("items")
public class HomeController {
    private ItemRepositoryMongo itemRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    private ItemValidationService itemValidationService;
    private ItemService itemService;
    
    @GetMapping("/homePage.html")
    public ModelAndView getStoreHomePage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
        mav.addObject("items", itemRepositoryMongo.findAll());
        mav.addObject("item", new CreateItemFormDto());
        mav.setViewName("homePage");
        return mav;
    }
    
    @GetMapping("/browse.html")
    public ModelAndView getBrowsePage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
        mav.addObject("items", itemRepositoryMongo.findAll());
        mav.setViewName("browse");
        return mav;
    }
    
    @PostMapping("/newItem.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveNewItem(@ModelAttribute @Valid CreateItemFormDto formItemDto, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("item", formItemDto);
        
        String duplicateError = itemValidationService.validateDuplicateItem(formItemDto, itemRepositoryMongo.findAll());
        
        if (!duplicateError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", duplicateError);
            bindingResult.addError(error);
        }
        
        if (bindingResult.hasErrors()) {
            mav.setViewName("homePage");
        } else {
            itemService.createItem(formItemDto);
            mav.setViewName("redirect:/storeHome/homePage.html");
        }
        return mav;
    }
    
    @GetMapping("/cleanSession.html")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/storeHome/homePage.html";
    }
    
    @GetMapping("/adminPage.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getStoreAdminPage() {
        return "adminPage";
    }
    
    @ResponseBody
    @GetMapping("/getItemData")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getItemData() throws InterruptedException {
        List<Item> items = itemRepositoryMongo.findAll();
        Thread.sleep(10000);
        return "{\"message\": \"You have " + items.size() + " items in stock.\"}";
    }
}
