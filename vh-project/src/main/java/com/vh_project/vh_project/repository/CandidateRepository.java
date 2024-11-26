package com.vh_project.vh_project.repository;

import com.vh_project.vh_project.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
