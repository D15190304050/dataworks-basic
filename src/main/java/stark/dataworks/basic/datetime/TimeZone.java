package stark.dataworks.basic.datetime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeZone
{
    private String name;
    private BigDecimal offsetHoursToUtc;
}
