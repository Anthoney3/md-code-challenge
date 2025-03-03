package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

// Added lombok annotations to clean up boilerplate code and make model easier to read
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//added for quality of life as to not include null attributes in the REST endpoint's JSON response
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;
}
