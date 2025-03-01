package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {


    private String compensationCreationUrl;
    private String compensationFetchUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        compensationCreationUrl = "http://localhost:" + port + "/compensation";
        compensationFetchUrl = "http://localhost:" + port + "/compensation/{id}";
    }


    @Test
    public void testCreateCompensationForEmployeeAndFetchForEmployee(){
        final String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        final Compensation requestBody = Compensation.builder()
                .employeeId(employeeId)
                .earnedBonusForEOY(true)
                .effectiveDate(LocalDate.now())
                .salary(new BigDecimal("100000.00"))
                .build();

        // create compensation and persist to database
        final Compensation createdCompensation = testRestTemplate.postForEntity(compensationCreationUrl, requestBody, Compensation.class).getBody();

        // fetch created compensation from database
        final Compensation fetchedCompensation = testRestTemplate.getForEntity(compensationFetchUrl, Compensation.class, employeeId).getBody();

        //assert both objects match equally across all attributes
        assertThat(fetchedCompensation).usingRecursiveComparison().isEqualTo(createdCompensation);
    }

    @Test
    public void testCreateCompensation_EmployeeIdIsNull_500ResponseReturned(){
        final Compensation compensation = Compensation.builder().build();
        // check that 500 response is returned after sending request with no employee id
        assertEquals(HttpStatusCode.valueOf(500), testRestTemplate.postForEntity(compensationCreationUrl, compensation, Compensation.class).getStatusCode());
    }

    @Test
    public void testFetchCompensation_EmployeeDoesNotExist_500ResponseReturned(){

        final String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        final Compensation requestBody = Compensation.builder()
                .employeeId(employeeId)
                .earnedBonusForEOY(true)
                .effectiveDate(LocalDate.now())
                .salary(new BigDecimal("100000.00"))
                .build();

        // create compensation for employee
        final Compensation createdCompensation = testRestTemplate.postForEntity(compensationCreationUrl, requestBody, Compensation.class).getBody();

        // added an extra letter to the employee id to simulate the employee not existing
        assertEquals(HttpStatusCode.valueOf(500), testRestTemplate.getForEntity(compensationFetchUrl, Compensation.class, employeeId + "L").getStatusCode());
    }

    @Test
    public void testFetchCompensation_CompensationForEmployeeDoesNotExist_500ResponseReturned(){

        final String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        // fetch non existing employee and expect 500 response in return
        assertEquals(HttpStatusCode.valueOf(500), testRestTemplate.getForEntity(compensationFetchUrl, Compensation.class, employeeId).getStatusCode());
    }

}
