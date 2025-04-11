package com.example.ingressjobs.controller;

import com.example.ingressjobs.criteria.JobCriteria;
import com.example.ingressjobs.criteria.PageCriteria;
import com.example.ingressjobs.model.response.JobResponse;
import com.example.ingressjobs.model.response.PageableResponse;
import com.example.ingressjobs.service.abstraction.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/jobs")
public class JobController {
    private final JobService jobService;

    @GetMapping
    public PageableResponse<JobResponse> getAllJobs(
            @RequestParam(defaultValue = "postedDate") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            PageCriteria pageCriteria,
            JobCriteria jobCriteria) {
        return jobService.getAllJobs(sortBy, order, pageCriteria, jobCriteria);
    }
}
