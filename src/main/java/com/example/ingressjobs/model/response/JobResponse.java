package com.example.ingressjobs.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private String title;
    private String companyName;
    private String location;
    private String jobType;
    private String salaryRange;
    private String description;
    private String requirements;
    private String experienceLevel;
    private String educationLevel;
    private String industry;
    private LocalDate postedDate;
    private String companyLogoUrl;
    private String sourceUrl;
}
