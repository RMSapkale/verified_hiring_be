package com.vhProject.repository;

import com.vhProject.model.InternshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface InternshipRepository extends JpaRepository<InternshipModel, Long> {
    Optional<InternshipModel> findByTitle(String title);
}
