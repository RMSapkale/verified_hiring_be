package com.vhProject.controller;

import com.vhProject.model.Project;
import com.vhProject.service.ProjectService;
import com.vhProject.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<Object> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseHandler.generateResponse("Fetched all projects successfully", HttpStatus.OK, projects);
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project != null) {
            return ResponseHandler.generateResponse("Project found", HttpStatus.OK, project);
        } else {
            return ResponseHandler.generateResponse("Project not found", HttpStatus.NOT_FOUND, null);
        }
    }

    @PostMapping("/CreateProject")
    public ResponseEntity<Object> createProject(@RequestBody Project project) {
        Project createdProject = projectService.saveProject(project);
        return ResponseHandler.generateResponse("Project created successfully", HttpStatus.CREATED, createdProject);
    }

    @PutMapping("/UpdateProject/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Project project = projectService.updateProject(id, updatedProject);
        if (project != null) {
            return ResponseHandler.generateResponse("Project updated successfully", HttpStatus.OK, project);
        } else {
            return ResponseHandler.generateResponse("Project not found", HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("/DeleteProject/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
        boolean isDeleted = projectService.deleteProject(id);
        if (isDeleted) {
            return ResponseHandler.generateResponse("Project deleted successfully", HttpStatus.OK, null);
        } else {
            return ResponseHandler.generateResponse("Project not found", HttpStatus.NOT_FOUND, null);
        }
    }
}
