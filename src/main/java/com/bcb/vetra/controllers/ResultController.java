package com.bcb.vetra.controllers;

import com.bcb.vetra.daos.PatientDao;
import com.bcb.vetra.daos.ResultDao;
import com.bcb.vetra.daos.TestDao;
import com.bcb.vetra.daos.UserDao;
import com.bcb.vetra.models.Result;
import com.bcb.vetra.models.Test;
import com.bcb.vetra.services.ValidateAccess;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class ResultController {

    private TestDao testDao;
    private ResultDao resultDao;
    private PatientDao patientDao;
    private UserDao userDao;
    private ValidateAccess validateAccess;
    public ResultController(ResultDao resultDao, TestDao testDao, UserDao userDao, PatientDao patientDao) {
        this.resultDao = resultDao;
        this.testDao = testDao;
        this.validateAccess = new ValidateAccess(patientDao, userDao, testDao);
    }

    @GetMapping("/patients/{patientId}/tests/{testId}/results")
    public List<Result> getAll(@PathVariable int patientId, @PathVariable int testId, Principal principal) {
        if (!validateAccess.canAccessPatient(patientId, principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this patient.");
        }
        return resultDao.getResultsForTest(testId);
    }
    @GetMapping("/patients/{patientId}/tests/{testId}/results/{resultId}")
    public Result get(@PathVariable int patientId, @PathVariable int testId, @PathVariable int resultId, Principal principal) {
        Result result = resultDao.getResultById(resultId);
        if (result.getTestID() != testId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Result does not belong to test.");
        }
        if (!validateAccess.canAccessResult(result, testId, principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this result.");
        }
        return result;
    }

    // No create or update methods as I plan to supply the test data from another API.

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/patients/{patientId}/tests/{testId}/results/{resultId}")
    public void delete(@PathVariable int patientId, @PathVariable int testId) {
        testDao.deleteTestOfPatient(testId, patientId);
    }

}