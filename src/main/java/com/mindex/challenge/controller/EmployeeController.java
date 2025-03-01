package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee") //allows each endpoint to start with /employee without needing to add it to each endpoint
@Slf4j // adds logging to class without required logging boilerplate code
@Tag(name = "Employee Controller", description = "returns information relating to employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        log.debug("Received employee create request for [{}]", employee);
        return employeeService.create(employee);
    }

    @GetMapping("/{id}")
    public Employee read(@PathVariable String id) {
        log.debug("Received employee create request for id [{}]", id);
        return employeeService.read(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        log.debug("Received employee create request for id [{}] and employee [{}]", id, employee);
        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
}
