package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Thoughts while working:
 *
 * It was desired to add some api response documentation to the below endpoints utilizing
 * the OpenAPI 3.0 mixed with spring swagger. This makes functional testing easier as well as
 * cleaner when testing endpoints. It also removes the requirement to utilize postman or other
 * http request sending software.
 */
@RestController
@Tag(name = "Employee Compensation Controller", description = "returns compensation information for employees")
@RequestMapping("/compensation")
@RequiredArgsConstructor
@Slf4j
public class CompensationController {

    final CompensationService compensationService;

    @Operation(
            method = "get",
            summary = "fetches a compensation for an employee using an employee's id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully fetched employee's compensation"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Failed to fetch compensation for employee, employee compensation either doesn't exist or the employee doesn't exist"
                    )
            }
    )
    @GetMapping(value = "/{id}")
    public Compensation getEmployeeCompensation(@PathVariable String id){
        log.debug("Received request to search for compensation regarding employee with id : {}", id);
        return compensationService.read(id);
    }
    @Operation(
            method = "post",
            summary = "creates a compensation for an employee",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created and persisted compensation for employee"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Compensation creation failed, employee doesn't exist or internal server error"
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Compensation createEmployeeCompensation(@RequestBody Compensation compensation){
        log.debug("Received request to create new compensation for employee with id : {}", compensation.getEmployeeId());
        return compensationService.create(compensation);
    }
}
