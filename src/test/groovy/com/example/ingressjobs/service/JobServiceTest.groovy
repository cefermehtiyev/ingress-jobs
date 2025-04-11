package com.example.ingressjobs.service

import com.example.ingressjobs.criteria.JobCriteria
import com.example.ingressjobs.criteria.PageCriteria
import com.example.ingressjobs.dao.entity.JobEntity
import com.example.ingressjobs.dao.repository.JobRepository
import com.example.ingressjobs.service.concurate.JobServiceHandler
import com.example.ingressjobs.service.specification.JobSpecification
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import org.springframework.data.domain.Sort


class JobServiceTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    JobRepository jobRepository
    JobServiceHandler jobServiceHandler

    def setup() {
        jobRepository = Mock()
        jobServiceHandler = new JobServiceHandler(jobRepository)
    }

    def "TestGetAllJobs"() {
        given:
        def sortBy = "postedDate"
        def order = "asc"
        def pageCriteria = new PageCriteria(0, 10)
        def jobCriteria = new JobCriteria()
        def jobEntities = [new JobEntity(), new JobEntity()]

        def mockPage = new PageImpl<>(jobEntities, PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()), jobEntities.size())

        when:
        jobServiceHandler.getAllJobs(sortBy, order, pageCriteria, jobCriteria)

        then:
        1 * jobRepository.findAll(_ as JobSpecification, _ as PageRequest) >> mockPage
    }

}
