package com.example.ingressjobs.service.concurate;

import com.example.ingressjobs.annotation.Log;
import com.example.ingressjobs.criteria.JobCriteria;
import com.example.ingressjobs.criteria.PageCriteria;
import com.example.ingressjobs.dao.repository.JobRepository;
import com.example.ingressjobs.model.response.JobResponse;
import com.example.ingressjobs.model.response.PageableResponse;
import com.example.ingressjobs.service.abstraction.JobService;
import com.example.ingressjobs.service.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.ingressjobs.mapper.JobMapper.JOB_MAPPER;

@Log
@Service
@RequiredArgsConstructor
public class JobServiceHandler implements JobService {
    private final JobRepository jobRepository;


    @Override
    public PageableResponse<JobResponse> getAllJobs(String sortBy,String order ,PageCriteria pageCriteria, JobCriteria jobCriteria) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        var page =  jobRepository.findAll(
                new JobSpecification(jobCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), sort)
        );

        return JOB_MAPPER.pageableJobResponse(page);
    }
}
