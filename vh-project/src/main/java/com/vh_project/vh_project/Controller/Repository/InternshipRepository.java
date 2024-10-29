package com.vh_project.vh_project.Controller.Repository;


import com.vh_project.vh_project.Controller.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface InternshipRepository extends JpaRepository<Internship, Long> {
}
