package com.vhProject.controller;

import com.vhProject.model.InternshipModel;
import com.vhProject.repository.InternshipRepository;
import com.vhProject.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;
  @Autowired
   private InternshipRepository internshipRepository;


    @GetMapping("/getAllInternships")
    public ResponseEntity<List<InternshipModel>> getAllInternships() {
        List<InternshipModel> internships = internshipService.getAllInternships();
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/getInternshipById/{id}")
    public ResponseEntity<InternshipModel> getInternshipById(@PathVariable Long id) {
        Optional<InternshipModel> internship = internshipService.getInternshipById(id);
        return internship.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping("/createInternship")
//    public ResponseEntity<InternshipModel> createInternship(@RequestBody InternshipModel internship) {
//        InternshipModel savedInternship = internshipService.saveInternship(internship);
//        return ResponseEntity.ok(savedInternship);
//    }
    @PostMapping("/createInternship")
    public ResponseEntity<InternshipModel> createInternship(@RequestBody InternshipModel internship) {

        InternshipModel internshipModel = internshipRepository.findByTitle(internship.getTitle()).orElse(null);
        if (internshipModel != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        InternshipModel savedInternship = internshipService.saveInternship(internship);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInternship);
    }


    @PutMapping("/updateInternship/{id}")
    public ResponseEntity<InternshipModel> updateInternship(@PathVariable Long id, @RequestBody InternshipModel updatedInternship) {
        Optional<InternshipModel> optionalInternship = internshipService.getInternshipById(id);
        if (optionalInternship.isPresent()) {
            InternshipModel internship = optionalInternship.get();
            internship.setTitle(updatedInternship.getTitle());
            internship.setDescription(updatedInternship.getDescription());
            internship.setDuration(updatedInternship.getDuration());
            internship.setLocation(updatedInternship.getLocation());

            InternshipModel savedInternship = internshipService.saveInternship(internship);
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
