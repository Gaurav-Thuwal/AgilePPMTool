package com.agileProject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agileProject.ppmtool.domain.Backlog;
import com.agileProject.ppmtool.domain.Project;
import com.agileProject.ppmtool.exceptions.ProjectIdException;
import com.agileProject.ppmtool.repositories.BacklogRepository;
import com.agileProject.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	@Autowired
	public BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		//logic
		String projectId = project.getProjectIdentifier().toUpperCase();
		try {
			project.setProjectIdentifier(projectId);
			
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectId);
			}
			if(project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectId));
			}
			
			return projectRepository.save(project);
		}catch(Exception e){
			throw new ProjectIdException("Project ID '"+projectId+"' already exists");
		}
	}
	
	public Project findProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' does not exists");
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Cannot find a project with Project ID '"+projectId.toUpperCase());
		}
		projectRepository.delete(project);
	}
	
}
