package com.mindex.challenge.models;

import com.mindex.challenge.data.Employee;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;
}
