package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.models.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportingStructureServiceImpl implements ReportingStructureService {

    final EmployeeService employeeService;

    @Override
    public ReportingStructure getEmployeeReportingStructure(String employeeId) {
        log.debug("Generating report structure for employee with id: {}", employeeId);
        /*
            I decided to do a level order search in order to get distinct direct
            reports and I utilize a queue in order to do so.
         */

        final Queue<Employee> employeeQueue = new LinkedList<>();

        // throws runtime if null, so no need to return null if employee doesn't exist
        final Employee employeeFound = employeeService.read(employeeId);

        // add employee found as root to queue
        employeeQueue.add(employeeFound);

        // I initialize this with -1 as to not include the original employee in the report count
        int numberOfReportsFound = -1;

        while (!employeeQueue.isEmpty()) {
            /*
                I check if the employee being passed in is complete, if not I retrieve the all the information
                of the employee utilizing the employee services read method.
             */
            Employee employee = (employeeQueue.peek().getEmployeeId() != null && employeeQueue.peek().getFirstName() == null) ?
                    employeeService.read(employeeQueue.poll().getEmployeeId()) : employeeQueue.poll();

            numberOfReportsFound++;

            /*
                we check to see if the current employee has additional direct reports
                if so we just add it to the queue to be counted on the future iterations
             */
            if (employee.getDirectReports() != null && !employee.getDirectReports().isEmpty()) {
                employeeQueue.addAll(employee.getDirectReports());
            }
        }
        return ReportingStructure.builder()
                .numberOfReports(numberOfReportsFound)
                .employee(employeeFound)
                .build();
    }

}
