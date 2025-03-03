package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
//added for quality of life as to not include null attributes in the REST endpoint's JSON response
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Compensation {
    private String employeeId;
    private BigDecimal salary;
    private LocalDate effectiveDate;
    private boolean earnedBonusForEOY;
}
