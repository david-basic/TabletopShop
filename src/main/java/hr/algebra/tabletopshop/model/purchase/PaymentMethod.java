package hr.algebra.tabletopshop.model.purchase;

public enum PaymentMethod {
    
    PAYPAL("PayPal"),
    PAY_ON_DELIVERY("Pay on delivery");
    
    private final String name;
    
    PaymentMethod(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
