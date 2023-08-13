package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.items.Item;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.mongodb.ItemRepositoryMongo;
import hr.algebra.tabletopshop.service.ItemValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping("/storeHome")
@AllArgsConstructor
@SessionAttributes("items")
public class HomeController {
//    private ItemRepository itemRepository;
    private ItemRepositoryMongo itemRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    private ItemValidationService itemValidationService;
    
    @GetMapping("/homePage.html")
    public String getStoreHomePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
//        model.addAttribute("items", itemRepository.getAllItems());
        model.addAttribute("items", itemRepositoryMongo.findAll());
        model.addAttribute("item", new Item());
        return "homePage";
    }
    
    @GetMapping("/browse.html")
    public String getBrowsePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
//        model.addAttribute("items", itemRepository.getAllItems());
        model.addAttribute("items", itemRepositoryMongo.findAll());
        return "browse";
    }
    
    @PostMapping("/newItem.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveNewItem(Model model, @ModelAttribute @Valid Item item, BindingResult bindingResult) {
        model.addAttribute("item", item);
        
//        String duplicateError = itemValidationService.validateDuplicateItem(item, itemRepository.getAllItems());
        String duplicateError = itemValidationService.validateDuplicateItem(item, itemRepositoryMongo.findAll());
        
        if (!duplicateError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", duplicateError);
            bindingResult.addError(error);
        }
        
        if (bindingResult.hasErrors()) {
            return "homePage";
        } else {
//            itemRepository.saveNewItem(item);
            
            
 
            //TODO vidi dal je oke ovako dobit Integer iz longa tj dal ce radit
            Integer newItemId = ((Number)itemRepositoryMongo.count()).intValue();
            
            itemRepositoryMongo.save(new Item(newItemId + 1, item.getName(), item.getCategory(), item.getDescription(), item.getQuantity(), item.getPrice()));
            return "redirect:/storeHome/homePage.html";
        }
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
//        List<Item> items = itemRepository.getAllItems();
        List<Item> items = itemRepositoryMongo.findAll();
        Thread.sleep(10000);
        return "{\"message\": \"You have " + items.size() + " items in stock.\"}";
    }
}
