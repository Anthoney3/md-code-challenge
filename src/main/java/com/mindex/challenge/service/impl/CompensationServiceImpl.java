package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompensationServiceImpl implements CompensationService {

    final CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        //validate incoming compensation object data before creation
        if(compensation == null || compensation.getEmployeeId() == null) {
            throw new RuntimeException("Error creating new compensation for employee, employee id must not be null and compensation object must not be null");
        }
        log.debug("Creating new compensation for employee with id : {}", compensation.getEmployeeId());

        compensationRepository.insert(compensation);

        log.debug("Successfully inserted new compensation record for employee with id : {}", compensation.getEmployeeId());

        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {

        final Compensation compensation = compensationRepository.findByEmployeeId(employeeId);

        //throw error with recommended fix if compensation doesn't already exist for an employee
        if(compensation == null){
            throw new RuntimeException("No compensation found for employee with id : %s, please create a compensation first".formatted(employeeId));
        }
        return compensation;
    }
}
