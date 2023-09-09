package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.dto.*;
import hr.algebra.tabletopshop.model.items.Category;
import hr.algebra.tabletopshop.model.items.Item;
import hr.algebra.tabletopshop.model.purchase.Purchase;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.CategoryRepositoryMongo;
import hr.algebra.tabletopshop.repository.ItemRepositoryMongo;
import hr.algebra.tabletopshop.repository.UserRepositoryMongo;
import hr.algebra.tabletopshop.service.*;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/store")
@AllArgsConstructor
@SessionAttributes("items")
public class HomeController {
    private ItemRepositoryMongo itemRepositoryMongo;
    private CategoryRepositoryMongo categoryRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    private UserRepositoryMongo userRepositoryMongo;
    
    private CategoryService categoryService;
    private ItemValidationService itemValidationService;
    private CategoryValidationService categoryValidationService;
    private PurchaseService purchaseService;
    private CurrentUserService currentUserService;
    private ItemService itemService;
    private CartService cartService;
    
    private List<Item> itemsToDisplay;
    private List<Purchase> purchasesToDisplay;
    
    @GetMapping("/homePage")
    public ModelAndView getStoreHomePage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
        mav.addObject("items", itemRepositoryMongo.findAll());
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
        mav.addObject("categories", categoryService.getAllCategories());
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
            itemsToDisplay.addAll(itemRepositoryMongo.findAllByCategory(categoryService.getCategoryById(categoryChosen)));
        }
        mav.setViewName("redirect:/store/browse");
        return mav;
    }
    
    @GetMapping("/purchaseHistory")
    public ModelAndView getPurchaseHistoryPage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Authenticated user opened their purchase history!");
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.addObject("purchases", purchaseService.getAllPurchasesByUser(currentUserService.getCurrentUser()));
        mav.setViewName("purchaseHistoryPage");
        return mav;
    }
    
    @PostMapping("/purchaseDetails")
    public ModelAndView purchaseDetails(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Authenticated user opened purchase details!");
        Purchase purchaseById = purchaseService.getPurchaseById(id);
        mav.addObject("purchaseDetails", purchaseById.getPurchaseItems());
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.setViewName("purchaseDetailsPage");
        return mav;
    }
    
    @GetMapping("/admin/adminPage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getStoreAdminPage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Admin page opened!");
        mav.addObject("items", itemRepositoryMongo.findAll());
        mav.addObject("createItemDto", new CreateItemFormDto());
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.addObject("categories", categoryService.getAllCategories());
        mav.setViewName("adminPage");
        return mav;
    }
    
    @GetMapping("/admin/manageCategories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getManageCategoriesPage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Admin opened manage categories page!");
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject("createCategoryDto", new CreateCategoryFormDto());
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.setViewName("manageCategoriesPage");
        return mav;
    }
    
    @PostMapping("/admin/newCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView saveNewCategory(@ModelAttribute @Valid CreateCategoryFormDto formCategoryDto, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        String duplicateError = categoryValidationService.validateDuplicateCategory(formCategoryDto, categoryRepositoryMongo.findAll());
        
        if (!duplicateError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", duplicateError);
            bindingResult.addError(error);
        }
        customSpringEventPublisher.publishCustomEvent("New category creation attempt...");
        if (bindingResult.hasErrors()) {
            customSpringEventPublisher.publishCustomEvent("New category creation fail. Errors occurred!");
            mav.addObject("items", itemRepositoryMongo.findAll());
            mav.addObject("createCategoryDto", formCategoryDto);
            mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
            mav.addObject("categories", categoryService.getAllCategories());
            mav.setViewName("adminPage");
            return mav;
        }
        categoryService.createCategory(formCategoryDto);
        customSpringEventPublisher.publishCustomEvent("Category creation success!");
        mav.setViewName("redirect:/store/admin/manageCategories");
        return mav;
    }
    
    @PostMapping("/admin/deleteCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCategory(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Category deletion attempt...");
        categoryService.deleteCategory(id);
        customSpringEventPublisher.publishCustomEvent("Category deletion success!");
        mav.setViewName("redirect:/store/admin/manageCategories");
        return mav;
    }
    
    @PostMapping("/admin/categoryUpdatePage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getCategoryUpdatePage(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Category update page opened by admin!");
        Category categoryToUpdate = categoryService.getCategoryById(id);
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.addObject("updateCategoryDto", new UpdateCategoryFormDto(categoryToUpdate.getId(), categoryToUpdate.getName()));
        mav.setViewName("updateCategoryPage");
        return mav;
    }
    
    @PostMapping("/admin/updateCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateCategory(@ModelAttribute @Valid UpdateCategoryFormDto updateCategoryFormDto, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Category update attempt...");
        if (bindingResult.hasErrors()) {
            customSpringEventPublisher.publishCustomEvent("Category update fail. Errors occurred!");
            mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
            mav.addObject("updateCategoryDto", updateCategoryFormDto);
            mav.setViewName("updateCategoryPage");
            return mav;
        }
        categoryService.updateCategory(updateCategoryFormDto);
        customSpringEventPublisher.publishCustomEvent("Category update success!");
        mav.setViewName("redirect:/store/admin/manageCategories");
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
        customSpringEventPublisher.publishCustomEvent("Item creation attempt...");
        if (bindingResult.hasErrors()) {
            customSpringEventPublisher.publishCustomEvent("Item creation fail. Errors occurred!");
            mav.addObject("items", itemRepositoryMongo.findAll());
            mav.addObject("createItemDto", formItemDto);
            mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
            mav.addObject("categories", categoryService.getAllCategories());
            mav.setViewName("adminPage");
            return mav;
        }
        itemService.createItem(formItemDto);
        customSpringEventPublisher.publishCustomEvent("Item creation success!");
        mav.setViewName("redirect:/store/admin/adminPage");
        return mav;
    }
    
    @PostMapping("/admin/deleteItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteItem(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Item deletion attempt...");
        itemService.deleteItem(id);
        customSpringEventPublisher.publishCustomEvent("Item deletion success!");
        mav.setViewName("redirect:/store/admin/adminPage");
        return mav;
    }
    
    @PostMapping("/admin/updatePage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getUpdatePage(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Item update page opened by admin!");
        Item itemToUpdate = itemService.getItemById(id);
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject("updateItemDto", new UpdateItemFormDto(itemToUpdate.getId(), itemToUpdate.getName(), itemToUpdate.getCategory().getId(), itemToUpdate.getDescription(), itemToUpdate.getQuantity(), itemToUpdate.getPrice()));
        mav.setViewName("updateItemPage");
        return mav;
    }
    
    @PostMapping("/admin/updateItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateItem(@ModelAttribute @Valid UpdateItemFormDto updateItemFormDto, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Item update attempt...");
        if (bindingResult.hasErrors()) {
            customSpringEventPublisher.publishCustomEvent("Item update fail. Errors occurred!");
            mav.addObject("categories", categoryService.getAllCategories());
            mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
            mav.addObject("updateItemDto", updateItemFormDto);
            mav.setViewName("updateItemPage");
            return mav;
        }
        itemService.updateItem(updateItemFormDto);
        customSpringEventPublisher.publishCustomEvent("Item update success!");
        mav.setViewName("redirect:/store/admin/adminPage");
        return mav;
    }
    
    @GetMapping("/admin/storeHistory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getStoreHistoryPage() {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Admin entered store history page!");
        mav.addObject("allUsers", userRepositoryMongo.findAll());
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.addObject("dateRangeDto", new DateRangeDto());
        if (purchasesToDisplay.isEmpty()) {
            mav.addObject("purchasesToDisplay", purchaseService.getAllPurchases());
        } else {
            mav.addObject("purchasesToDisplay", purchasesToDisplay);
        }
        mav.setViewName("adminHistory");
        return mav;
    }
    
    @PostMapping("/admin/userHistoryByDate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findPurchaseInDateRange(@ModelAttribute("dateRangeDto") DateRangeDto dateRangeDto, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Admin browsed purchases by date range!");
        customSpringEventPublisher.publishCustomEvent("Browse purchases by date attempt...");
        if (bindingResult.hasErrors()) {
            customSpringEventPublisher.publishCustomEvent("Browse by date fail. Errors occurred!");
            mav.addObject("allUsers", userRepositoryMongo.findAll());
            mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
            mav.addObject("dateRangeDto", dateRangeDto);
            mav.setViewName("adminHistory");
            return mav;
        }
        Date startDate = dateRangeDto.getStartDate();
        Date endDate = dateRangeDto.getEndDate();
        purchasesToDisplay = new ArrayList<>();
        purchasesToDisplay.addAll(purchaseService.getAllPurchasesBetweenDates(startDate, endDate));
        customSpringEventPublisher.publishCustomEvent("Browse purchases by date success!");
        mav.setViewName("redirect:/store/admin/storeHistory");
        return mav;
    }
    
    @PostMapping("/admin/userHistory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findPurchasesForUser(@RequestParam("selectedUser") Integer userChosen) {
        ModelAndView mav = new ModelAndView();
        customSpringEventPublisher.publishCustomEvent("Admin browsed purchases by user!");
        purchasesToDisplay = new ArrayList<>();
        if (Objects.equals(userChosen, 0)) {
            purchasesToDisplay.addAll(purchaseService.getAllPurchases());
        } else {
            purchasesToDisplay.addAll(purchaseService.getAllPurchasesByUser(userRepositoryMongo.findById(userChosen).orElse(currentUserService.getCurrentUser())));
        }
        mav.setViewName("redirect:/store/admin/storeHistory");
        return mav;
    }
    
    @GetMapping("/cleanSession")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session) {
        customSpringEventPublisher.publishCustomEvent("Admin cleaned session!");
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/store/admin/adminPage";
    }
    
    @ResponseBody
    @GetMapping("/admin/getItemData")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getItemData() throws InterruptedException {
        List<Item> items = itemRepositoryMongo.findAll();
        Thread.sleep(10000);
        int size = 0;
        for (var item : items) {
            if (item.getQuantity() > 0) {
                size++;
            }
        }
        return "{\"message\": \"You have " + size + " items in stock.\"}";
    }
}
