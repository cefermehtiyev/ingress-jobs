package com.example.ingressjobs.controller

import com.example.ingressjobs.controller.JobController
import com.example.ingressjobs.criteria.JobCriteria
import com.example.ingressjobs.criteria.PageCriteria
import com.example.ingressjobs.service.abstraction.JobService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class JobControllerTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private JobService jobService
    private MockMvc mockMvc

    def setup() {
        jobService = Mock()
        def jobController = new JobController(jobService)
        mockMvc = MockMvcBuilders.standaloneSetup(jobController)
                .setControllerAdvice()
                .build()
    }


    def "TestGetAllJobs"() {
        given:
        def sortBy = "postedDate"
        def order = "asc"
        def pageCriteria = random.nextObject(PageCriteria)
        def jobCriteria = random.nextObject(JobCriteria)
        def url = "/v1/jobs"

        when:
          mockMvc
                .perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBy", sortBy)
                        .param("order", order)
                        .param("page", pageCriteria.page.toString())
                        .param("count", pageCriteria.count.toString())
                        .param("title", jobCriteria.title)
                        .param("companyName", jobCriteria.companyName)
                        .param("jobType",jobCriteria.jobType)
                        .param("location",jobCriteria.location)
                )
                .andReturn()

        then:
        1 * jobService.getAllJobs(sortBy, order, pageCriteria, jobCriteria)
    }


}
