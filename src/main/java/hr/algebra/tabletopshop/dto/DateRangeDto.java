package hr.algebra.tabletopshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeDto {
    @NotNull(message = "Start date must be assigned!")
    private Date startDate;
    
    @NotNull(message = "End date must be assigned!")
    private Date endDate;
}
