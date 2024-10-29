package com.vh_project.vh_project.Controller;


import com.vh_project.vh_project.Controller.model.Internship;
import com.vh_project.vh_project.Controller.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

    @GetMapping
    public ResponseEntity<List<Internship>> getAllInternships() {
        List<Internship> internships = internshipService.getAllInternships();
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long id) {
        Optional<Internship> internship = internshipService.getInternshipById(id);
        return internship.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Internship> createInternship(@RequestBody Internship internship) {
        Internship savedInternship = internshipService.saveInternship(internship);
        return ResponseEntity.ok(savedInternship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Internship> updateInternship(@PathVariable Long id, @RequestBody Internship updatedInternship) {
        Optional<Internship> optionalInternship = internshipService.getInternshipById(id);
        if (optionalInternship.isPresent()) {
            Internship internship = optionalInternship.get();
            internship.setTitle(updatedInternship.getTitle());
            internship.setDescription(updatedInternship.getDescription());
            internship.setDuration(updatedInternship.getDuration());
            internship.setLocation(updatedInternship.getLocation());

            Internship savedInternship = internshipService.saveInternship(internship);
            return ResponseEntity.ok(savedInternship);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }
}

