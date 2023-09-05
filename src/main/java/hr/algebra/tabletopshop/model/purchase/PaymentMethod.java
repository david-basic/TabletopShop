package hr.algebra.tabletopshop.model.purchase;

public enum PaymentMethod {
    
    PAYPAL("PayPal"),
    PAY_ON_DELIVERY("Pay on delivery");
    
    private final String displayName;
    
    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
}
