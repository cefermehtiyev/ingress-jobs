package com.example.ingressjobs.service.specification;

import com.example.ingressjobs.criteria.JobCriteria;
import com.example.ingressjobs.dao.entity.JobEntity;
import com.example.ingressjobs.model.constants.CriteriaConstants;
import com.example.ingressjobs.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.example.ingressjobs.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class JobSpecification implements Specification<JobEntity> {
    private final JobCriteria jobCriteria;

    @Override
    public Predicate toPredicate(Root<JobEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates =  PredicateUtil.builder()
                .addNullSafety(jobCriteria.getTitle(),
                        title -> cb.like(root.get(CriteriaConstants.TITLE),applyLikePattern(title)))
                .addNullSafety(jobCriteria.getCompanyName(),
                        companyName -> cb.like(root.get(CriteriaConstants.COMPANY_NAME),applyLikePattern(companyName)))
                .addNullSafety(jobCriteria.getJobType(),
                        jobType -> cb.like(root.get(CriteriaConstants.JOB_TYPE),applyLikePattern(jobType))
                        )
                .addNullSafety(jobCriteria.getLocation(),
                        location -> cb.like(root.get(CriteriaConstants.LOCATION),applyLikePattern(location)))
                .build();

        return cb.and(predicates);
    }
}
