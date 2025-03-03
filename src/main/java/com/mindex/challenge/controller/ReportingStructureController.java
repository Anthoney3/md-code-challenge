package com.mindex.challenge.controller;

import com.mindex.challenge.models.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Additional thoughts:
 *
 *  Because spring allows for constructor injection, I decided to utilize the annotation @{@link RequiredArgsConstructor}
 *     which takes any final attribute and autowires it as a parameter into this class' constructor.
 *     Doing this allows for easier manual mock injection during junit testing if desirable and is the
 *     preferred way spring wishes to inject beans.
 */


@RestController
@RequestMapping("/reporting-structure")
@Slf4j // adds logger instead of doing static boilerplate logger code
@RequiredArgsConstructor
@Tag(name = "Employee Reporting Structure Controller", description = "returns information pertaining to employee reporting.")
public class ReportingStructureController {
    final ReportingStructureService reportingStructureService;

    @GetMapping("/{id}")
    public ReportingStructure getEmployeeReportingStructure(@PathVariable String id){
        log.debug("Received request to generate employee report structure for employee with id : {}", id);
        return reportingStructureService.getEmployeeReportingStructure(id);
    }

}
