package com.mindex.challenge.data;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compensation {
    private String employeeId;
    private BigDecimal salary;
    private LocalDate effectiveDate;
    private boolean earnedBonusForEOY;
}
