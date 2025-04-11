package com.example.ingressjobs.mapper

import com.example.ingressjobs.dao.entity.JobEntity
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom


import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.PageImpl
import spock.lang.Specification

class JobMapperTest extends Specification{
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()


    def "BuildJobResponse"() {
        given:
        def jobEntity = random.nextObject(JobEntity)

        when:
        def response = JobMapper.JOB_MAPPER.buildJobResponse(jobEntity)

        then:
        jobEntity.title == response.title
        jobEntity.companyName == response.companyName
        jobEntity.location == response.location
        jobEntity.jobType == response.jobType
        jobEntity.salaryRange == response.salaryRange
        jobEntity.description == response.description
        jobEntity.requirements == response.requirements
        jobEntity.experienceLevel == response.experienceLevel
        jobEntity.educationLevel == response.educationLevel
        jobEntity.industry == response.industry
        jobEntity.postedDate == response.postedDate
        jobEntity.companyLogoUrl == response.companyLogoUrl
        jobEntity.sourceUrl == response.sourceUrl
    }




    def "BuildPageableResponse"() {
        given:
        def jobEntities = [new JobEntity(), new JobEntity()]

        def pageRequest = PageRequest.of(0, 10)
        def mockPage = new PageImpl<>(jobEntities, pageRequest, jobEntities.size())

        when:
        def response = JobMapper.JOB_MAPPER.pageableJobResponse(mockPage)  // Düzgün metodu istifadə edin

        then:
        response.currentPageNumber == mockPage.getNumber()
        response.totalPages == mockPage.getTotalPages()
        response.totalElements == mockPage.getTotalElements()
        response.numberOfElements == mockPage.getNumberOfElements()
        response.hasNextPage == mockPage.hasNext()
    }




}
