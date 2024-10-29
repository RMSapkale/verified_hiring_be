package com.vh_project.vh_project.Controller.service;


import com.vh_project.vh_project.Controller.Repository.InternshipRepository;
import com.vh_project.vh_project.Controller.model.Internship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Optional<Internship> getInternshipById(Long id) {
        return internshipRepository.findById(id);
    }

    public Internship saveInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }
}
