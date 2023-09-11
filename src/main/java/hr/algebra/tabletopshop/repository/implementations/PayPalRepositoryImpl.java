package hr.algebra.tabletopshop.repository.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.tabletopshop.model.cart.Cart;
import hr.algebra.tabletopshop.payload.paypal.*;
import hr.algebra.tabletopshop.repository.PayPalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PayPalRepositoryImpl implements PayPalRepository {
    private final ObjectMapper objectMapper;
    
    private final String clientId = "Ac3P3xez_V9kLHwtqlyND0kwt9imCGM22-h_w_iEKzLHDlY2tb6eumNVkIGs5wFfOEGSymwyf5rQAZsV";
    private final String clientSecret = "ECv6iiclYbk-0hoHSErre1loOlLUmJACtzH5XNGZUiMLi9JgRus6dsenrslxG0XADvlqBvVPjcVeUky4";
    
    private WebClient purchaseWebClient() {
        return WebClient.builder()
                .baseUrl("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                .defaultHeaders(httpHeaders ->
                                {
                                    httpHeaders.setBasicAuth(clientId, clientSecret);
                                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                    httpHeaders.set("Prefer", "return=representation");
                                })
                .build();
    }
    
    @Override
    public Optional<CreatePurchaseResponse> createOrder(Cart cart) {
        
        List<PurchaseUnitItemRequest> purchaseUnitItems = cart.getCartItems().stream()
                .map(it -> PurchaseUnitItemRequest.builder()
                        .name(it.getItem().getName())
                        .quantity(it.getQuantity().toString())
                        .description(it.getItem().getDescription())
                        .unitAmount(
                                PurchaseUnitItemUnitAmountRequest.builder()
                                        .currencyCode(CurrencyCode.EUR)
                                        .value(String.valueOf(it.getItem().getPrice()))
                                        .build()
                        )
                        .category(PurchaseUnitItemCategory.PHYSICAL_GOODS)
                        .build()
                )
                .toList();
        
        PurchaseUnitAmountBreakdownItemTotalRequest purchaseUnitAmountBreakdownItemTotal = PurchaseUnitAmountBreakdownItemTotalRequest.builder()
                .currencyCode(CurrencyCode.EUR)
                .value(String.valueOf(cart.getTotalPrice()))
                .build();
        
        PurchaseUnitAmountBreakdownRequest purchaseUnitAmountBreakdown = PurchaseUnitAmountBreakdownRequest.builder()
                .itemTotal(purchaseUnitAmountBreakdownItemTotal)
                .build();
        PurchaseUnitAmountRequest purchaseUnitAmount = PurchaseUnitAmountRequest.builder()
                .currencyCode(CurrencyCode.EUR)
                .value(String.valueOf(cart.getTotalPrice()))
                .breakdown(purchaseUnitAmountBreakdown)
                .build();
        PurchaseUnitRequest purchaseUnit = PurchaseUnitRequest.builder()
                .referenceId(cart.getId())
                .items(purchaseUnitItems)
                .amount(purchaseUnitAmount)
                .build();
        
        String returnUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/purchase/success")
                .build()
                .toUriString();
        String cancelUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/purchase/cancel")
                .build()
                .toUriString();
        
        ApplicationContextRequest context = ApplicationContextRequest.builder()
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .build();
        
        BodyInserter<CreatePurchaseRequest, ReactiveHttpOutputMessage> body = BodyInserters.fromValue(
                CreatePurchaseRequest.builder()
                        .purchaseUnit(purchaseUnit)
                        .intent(PayPalIntent.CAPTURE)
                        .applicationContext(context)
                        .build()
        );
        
        return purchaseWebClient().post()
                .body(body)
                .exchangeToMono(clientResponse ->
                                        clientResponse.statusCode().is2xxSuccessful()
                                                ? clientResponse.bodyToMono(Object.class)
                                                : Mono.empty()
                )
                .blockOptional()
                .map(it -> objectMapper.convertValue(it, new TypeReference<>() {
                }));
    }
}
