package com.example.ingressjobs.service.abstraction;

import com.example.ingressjobs.criteria.JobCriteria;
import com.example.ingressjobs.criteria.PageCriteria;
import com.example.ingressjobs.model.response.JobResponse;
import com.example.ingressjobs.model.response.PageableResponse;

public interface JobService {
    PageableResponse<JobResponse> getAllJobs(String sortBy,String order ,PageCriteria pageCriteria, JobCriteria jobCriteria);
}
