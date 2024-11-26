package com.vh_project.vh_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vh_project.vh_project.model.Candidate;
import com.vh_project.vh_project.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // Get all candidates
    @GetMapping("/getAllCandidates")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }

    // Get candidate by ID
    @GetMapping("/getCandidateById/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new candidate
    @PostMapping("/createCandidate")
    public ResponseEntity<String> createCandidate(@RequestParam String candidate,
                                                  @RequestParam MultipartFile profile,
                                                  @RequestParam MultipartFile resume) {
        try {
            // Convert the JSON string into a Candidate object
            ObjectMapper objectMapper = new ObjectMapper();
            Candidate candidateObject = objectMapper.readValue(candidate, Candidate.class);

            // Set profile and resume files
            candidateObject.setProfilePhoto(profile.getBytes());
            candidateObject.setResume(resume.getBytes());

            // Save candidate
            candidateObject.setFresher(true);
            Candidate savedCandidate = candidateService.saveCandidate(candidateObject);

            return ResponseEntity.ok("Candidate created successfully with ID: " + savedCandidate.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating candidate: " + e.getMessage());
        }
    }

    // Update an existing candidate
    @PutMapping("/updateCandidate/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate updatedCandidate) {
        Optional<Candidate> optionalCandidate = candidateService.getCandidateById(id);
        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            candidate.setSalutation(updatedCandidate.getSalutation());
            candidate.setFirstName(updatedCandidate.getFirstName());
            candidate.setMiddleName(updatedCandidate.getMiddleName());
            candidate.setLastName(updatedCandidate.getLastName());
            candidate.setMobileNumber(updatedCandidate.getMobileNumber());
            candidate.setEmail(updatedCandidate.getEmail());
            candidate.setPassword(updatedCandidate.getPassword());
            candidate.setWorkStatus(updatedCandidate.getWorkStatus());
//            candidate.setFresher(updatedCandidate.isFresher());
            candidate.setProfilePhoto(updatedCandidate.getProfilePhoto());
            candidate.setResume(updatedCandidate.getResume());

            Candidate savedCandidate = candidateService.saveCandidate(candidate);
            return ResponseEntity.ok(savedCandidate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a candidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}
