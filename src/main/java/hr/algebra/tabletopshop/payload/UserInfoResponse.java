package hr.algebra.tabletopshop.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Integer id;
    private String username;
    private List<String> roles;
}
