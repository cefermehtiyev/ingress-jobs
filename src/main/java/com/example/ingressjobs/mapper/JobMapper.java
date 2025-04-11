package com.example.ingressjobs.mapper;

import com.example.ingressjobs.dao.entity.JobEntity;
import com.example.ingressjobs.model.response.JobResponse;
import com.example.ingressjobs.model.response.PageableResponse;
import org.springframework.data.domain.Page;

public enum JobMapper {
    JOB_MAPPER;


    public JobResponse buildJobResponse(JobEntity jobEntity) {
        return JobResponse.builder()
                .title(jobEntity.getTitle())
                .companyName(jobEntity.getCompanyName())
                .location(jobEntity.getLocation())
                .jobType(jobEntity.getJobType())
                .salaryRange(jobEntity.getSalaryRange())
                .description(jobEntity.getDescription())
                .requirements(jobEntity.getRequirements())
                .experienceLevel(jobEntity.getExperienceLevel())
                .educationLevel(jobEntity.getEducationLevel())
                .industry(jobEntity.getIndustry())
                .postedDate(jobEntity.getPostedDate())
                .companyLogoUrl(jobEntity.getCompanyLogoUrl())
                .sourceUrl(jobEntity.getSourceUrl())
                .build();
    }

    public PageableResponse<JobResponse> pageableJobResponse(Page<JobEntity> jobEntityPage) {
        return PageableResponse.<JobResponse>builder()
                .list((jobEntityPage.map(this::buildJobResponse).toList()))
                .currentPageNumber(jobEntityPage.getNumber())
                .totalPages(jobEntityPage.getTotalPages())
                .totalElements(jobEntityPage.getTotalElements())
                .numberOfElements(jobEntityPage.getNumberOfElements())
                .hasNextPage(jobEntityPage.hasNext())
                .build();
    }
}
