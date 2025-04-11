package com.example.ingressjobs.dao.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JobEntity jobEntity)) return false;
        return Objects.equals(id, jobEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
