package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.dto.CreatePurchaseDto;
import hr.algebra.tabletopshop.dto.PurchaseFormDto;
import hr.algebra.tabletopshop.model.purchase.Purchase;
import hr.algebra.tabletopshop.model.users.User;

import java.util.Date;
import java.util.List;

public interface PurchaseService {
    List<Purchase> getAllPurchases();
    
    Purchase getPurchaseById(String id);
    
    Purchase getPurchaseByPayPalId(String paypalId);
    
    List<Purchase> getAllPurchasesByUser(User user);
    
    void cancelOrder(String token);
    
    CreatePurchaseDto createPurchase(PurchaseFormDto purchaseFormDto);
    
    List<Purchase> getAllPurchasesBetweenDates(Date startDate, Date endDate);
    
}
