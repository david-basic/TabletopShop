package hr.algebra.tabletopshop.model.logging;

import hr.algebra.tabletopshop.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("loginlogs")
public class LoginLog implements Serializable {
    @Id
    private String id;
    
    private Integer logId;
    
    @DBRef
    private User user;
    
    private String ipAddress;
    
    @CreatedDate
    private Date loginAt;
    
    @Builder
    public LoginLog(Integer logId, User user, String ipAddress, Date loginAt) {
        this.logId = logId;
        this.user = user;
        this.ipAddress = ipAddress;
        this.loginAt = loginAt;
    }
}
