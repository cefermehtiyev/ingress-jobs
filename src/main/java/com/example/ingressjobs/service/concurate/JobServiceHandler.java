package com.example.ingressjobs.service.concurate;

import com.example.ingressjobs.criteria.JobCriteria;
import com.example.ingressjobs.criteria.PageCriteria;
import com.example.ingressjobs.dao.repository.JobRepository;
import com.example.ingressjobs.mapper.JobMapper;
import com.example.ingressjobs.model.response.JobResponse;
import com.example.ingressjobs.model.response.PageableResponse;
import com.example.ingressjobs.service.abstraction.JobService;
import com.example.ingressjobs.service.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.example.ingressjobs.mapper.JobMapper.JOB_MAPPER;

@Service
@RequiredArgsConstructor
public class JobServiceHandler implements JobService {
    private final JobRepository jobRepository;


    @Override
    public PageableResponse<JobResponse> getAllJobs(PageCriteria pageCriteria, JobCriteria jobCriteria) {
        var page =  jobRepository.findAll(
                new JobSpecification(jobCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );

        return JOB_MAPPER.pageableBookResponse(page);
    }
}
