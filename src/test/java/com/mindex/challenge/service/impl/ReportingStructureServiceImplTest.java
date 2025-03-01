package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.models.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {


    private String reportingStructureUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reporting-structure/{id}";
    }


    @Test
    public void testGetEmployeeReportingStructure_EmployeeHasTwoDirectReports(){

        // create simulated employee using data from json
        // I could've made a test json instead but just utilized the data provided
        final String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        final List<Employee> directReports = new ArrayList<>();
        final Employee firstReport = Employee.builder()
                .employeeId("b7839309-3348-463b-a7e3-5de1c168beb3")
                .build();
        final Employee secondReport = Employee.builder()
                .employeeId("03aa1462-ffa9-4978-901b-7c001562cf6f")
                .build();
        directReports.add(firstReport);
        directReports.add(secondReport);

        final Employee employee = Employee.builder()
                .employeeId(employeeId)
                .firstName("John")
                .lastName("Lennon")
                .position("Development Manager")
                .department("Engineering")
                .directReports(directReports)
                .build();

        // create an expected result returned from our service class
        final ReportingStructure expectedResult = ReportingStructure.builder()
                .numberOfReports(4)
                .employee(employee)
                .build();

        /*
            because both objects being compared are the same but have difference in memory addresses
            I utilize the assertJ library to recursively compare each attribute in both objects to ensure
            both are equal
         */
        assertThat(expectedResult).usingRecursiveComparison()
                .isEqualTo(testRestTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody());
    }

    @Test
    public void testGetEmployeeReportingStructure_EmployeeHasNoDirectReports(){
        // simulate employee with no direct reports
        final String employeeId = "b7839309-3348-463b-a7e3-5de1c168beb3";
        final Employee employee = Employee.builder()
                .employeeId(employeeId)
                .firstName("Paul")
                .lastName("McCartney")
                .position("Developer I")
                .department("Engineering")
                .build();

        // create an expected result returned from our service class
        final ReportingStructure expectedResult = ReportingStructure.builder()
                .numberOfReports(0)
                .employee(employee)
                .build();

        // use same method as above to ensure expected matches result object
        assertThat(expectedResult).usingRecursiveComparison()
                .isEqualTo(testRestTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody());
    }


}
