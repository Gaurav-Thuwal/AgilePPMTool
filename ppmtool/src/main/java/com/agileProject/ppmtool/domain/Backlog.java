package com.agileProject.ppmtool.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Backlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer PTsequence = 0;
	
	private String projectIdentifier;

	//OnToOne with project
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="project_id", nullable = false)
	@JsonIgnore
	private Project project;
	
	//OneToMany with ProjectTask
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
	private List<ProjectTask> projectTasks = new ArrayList<>();
	
	public Backlog() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPTsequence() {
		return PTsequence;
	}

	public void setPTsequence(Integer pTsequence) {
		PTsequence = pTsequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public Backlog(Long id, Integer pTsequence, String projectIdentifier) {
		this.id = id;
		PTsequence = pTsequence;
		this.projectIdentifier = projectIdentifier;
	}
	
	
	
}
