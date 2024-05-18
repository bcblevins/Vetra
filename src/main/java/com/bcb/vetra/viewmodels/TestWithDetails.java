package com.bcb.vetra.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * View Model class for a test, including all results and parameters. Will be used to display test results.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestWithDetails {
    private int testId;
    private String name;
    private int patientID;
    private String doctorUsername;
    private List<ParameterWithResult> results;
    private LocalDateTime timestamp;
    public void addToResults(ParameterWithResult result) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
        results.add(result);
    }
}
