package hr.algebra.tabletopshop.payload.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HrefResponse {
    @JsonProperty("href")
    private String href;
    
    @JsonProperty("rel")
    private String rel;
    
    @JsonProperty("method")
    private HttpMethod method;
    
    @JsonProperty("status")
    private PayPalStatus status;
}
