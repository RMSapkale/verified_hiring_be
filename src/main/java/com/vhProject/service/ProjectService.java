package com.vhProject.service;

import com.vhProject.model.Project;
//import com.vhProject.model.UserModel;
import com.vhProject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = getProjectById(id);
        existingProject.setCollegeName(updatedProject.getCollegeName());
        existingProject.setProjectTitle(updatedProject.getProjectTitle());
        existingProject.setRole(updatedProject.getRole());
        existingProject.setTeamSize(updatedProject.getTeamSize());
        existingProject.setProjectStartDate(updatedProject.getProjectStartDate());
        existingProject.setSkillsUsed(updatedProject.getSkillsUsed());
        return projectRepository.save(existingProject);
    }
    public boolean deleteProject(Long id) {
            if (!projectRepository.existsById(id)) {
                throw new RuntimeException("Project with ID " + id + " not found");
            }
            projectRepository.deleteById(id);
        return false;
    }
    }


