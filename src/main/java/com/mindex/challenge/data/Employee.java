package com.mindex.challenge.data;

import lombok.*;

import java.util.List;

// Added lombok annotations to clean up boilerplate code and make model easier to read
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;
}
