package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.models.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

/**
 * Thoughts while working:
 *
 * During my initial attempt at this before re-reading the prompt to ensure requirements were met in the first task. I noticed
 * the requirement was to "fully" fill out the reporting structure object. Now my first thought was to just ensure
 * all attributes of the object were filled out, that being both the employee attribute and the numberOfReports attribute.
 * However, upon testing it was noticed that the employee object would return with partially filled out information in the direct reports array;
 * that being, just the employee id's and nothing else. I felt this didn't make sense from a data perspective to return the total number
 * of reports as being 4 but only show 2 employee ids that are linked. That said, I went back and redesigned my original queue approach
 * which just iterated through reports to find a count using a level order search, and made a recursive approach in which counts all reports
 * and updates all employee information to provide a fully filled out reporting structure as you would expect to see in a large
 * organization website.
 */
public class ReportingStructureServiceImpl implements ReportingStructureService {

    final EmployeeService employeeService;

    @Override
    public ReportingStructure getEmployeeReportingStructure(String employeeId) {

        log.debug("Generating report structure for employee with id: {}", employeeId);
        /*
            we start with -1 to ensure we don't include the root employee in the total report count,
            and we also use an atomic integer for the lambda updates in the recursion.
         */
        final AtomicInteger totalReportCount = new AtomicInteger(-1);

        // Entry point to populate an employee and their full direct reports recursively
        Employee employee = generateEmployeeReportStructure(employeeId, totalReportCount);

        // Return the fully populated employee object
        return ReportingStructure.builder()
                .employee(employee)
                .numberOfReports(totalReportCount.get())
                .build();

    }

    private Employee generateEmployeeReportStructure(final String employeeId, final AtomicInteger totalReportCount) {
        Employee employee = employeeService.read(employeeId);

        // If the employee has direct reports, recursively update them
        List<Employee> directReports = employee.getDirectReports();

        if (directReports != null && !directReports.isEmpty()) {

            /*
                I map all fully flushed out employee objects into a list
                before updating the direct reports list so all information
                is present in the REST endpoint return.

                During the mapping, we recursively call this method to handle all
                additional direct reports that any other employees may have when iterating
                through.
             */
            List<Employee> fullDirectReports = directReports.stream()
                    .map(emp -> generateEmployeeReportStructure(emp.getEmployeeId(), totalReportCount))
                    .collect(Collectors.toList());

            // Update the employee's direct reports with full objects
            employee.setDirectReports(fullDirectReports);
        }

        // each interation we increment the count by one
        totalReportCount.incrementAndGet();
        return employee;
    }
}
