package com.vhProject.service;

import com.vhProject.model.InternshipModel;
import com.vhProject.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    public List<InternshipModel> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Optional<InternshipModel> getInternshipById(Long id) {
        return internshipRepository.findById(id);
    }

    public InternshipModel saveInternship(InternshipModel internship) {
        return internshipRepository.save(internship);
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }
}

