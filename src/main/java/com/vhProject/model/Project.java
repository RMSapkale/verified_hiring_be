package com.vhProject.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
public  class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String collegeName;

    @Column(nullable = false)
    private String projectTitle;

    @Column(nullable = false)
    private String role;

    private int teamSize;

    @Column(nullable = false)
    private LocalDate projectStartDate;

    @Column(nullable = false)
    private String skillsUsed;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getSkillsUsed() {
        return skillsUsed;
    }

    public void setSkillsUsed(String skillsUsed) {
        this.skillsUsed = skillsUsed;
    }


}

