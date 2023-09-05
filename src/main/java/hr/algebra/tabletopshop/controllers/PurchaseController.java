package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.dto.CreatePurchaseDto;
import hr.algebra.tabletopshop.dto.PurchaseFormDto;
import hr.algebra.tabletopshop.model.purchase.PaymentMethod;
import hr.algebra.tabletopshop.service.CartService;
import hr.algebra.tabletopshop.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final CartService cartService;

    
    @Value("${paypal.client-id}")
    private String clientId;
    
    @Value("${paypal.secret}")
    private String secret;
    
    
    @GetMapping("/get")
    public ModelAndView getPurchasePage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
        mav.addObject("cart", cartService.getCurrentUserCart());
        mav.addObject("purchaseFormDto", new PurchaseFormDto());
        mav.addObject("clientId", clientId);
        mav.setViewName("purchasePage");
        return mav;
    }
    
    @PostMapping("/checkout")
    public ModelAndView checkout(@ModelAttribute @Valid PurchaseFormDto purchaseFormDto, Errors errors) {
        ModelAndView mav = new ModelAndView();
        if (errors.hasErrors()) {
            mav.addObject("cartItemCount", cartService.getCurrentUserCart().getCartItems().size());
            mav.addObject("cart", cartService.getCurrentUserCart());
            mav.addObject("purchaseFormDto", purchaseFormDto);
            mav.addObject("clientId", clientId);
            mav.setViewName("purchasePage");
            return mav;
        }
        
        CreatePurchaseDto createPurchaseDto = purchaseService.createPurchase(purchaseFormDto);
        
        if (createPurchaseDto.getPurchase().getPaymentMethod().equals(PaymentMethod.PAY_ON_DELIVERY)) {
            mav.setViewName("redirect:/purchase/success");
        } else {
            mav.setViewName("redirect:" + createPurchaseDto.getPayPalLink());
        }
        return mav;
    }
    
    @GetMapping("/success")
    public ModelAndView getSuccess() {
        ModelAndView mav = new ModelAndView();
        cartService.cleanCart();
        mav.setViewName("purchaseSuccessPage");
        return mav;
    }
    
    @GetMapping("/cancel")
    public ModelAndView getCancel(@RequestParam String token) {
        ModelAndView mav = new ModelAndView();
        purchaseService.cancelOrder(token);
        mav.setViewName("redirect:/purchase/get");
        
        return mav;
    }
}
