package com.mindex.challenge.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindex.challenge.data.Employee;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
//added for quality of life as to not include null attributes in the REST endpoint's JSON response
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;
}
