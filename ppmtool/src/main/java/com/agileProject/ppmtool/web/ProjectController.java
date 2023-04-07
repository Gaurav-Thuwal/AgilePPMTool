package com.agileProject.ppmtool.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agileProject.ppmtool.domain.Project;
import com.agileProject.ppmtool.services.MapValidationErrorService;
import com.agileProject.ppmtool.services.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }


    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){return projectService.findAllProjects();}


    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
    }
    @PostMapping("/{projectId}")
	public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, 
			BindingResult result){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		String projectId = project.getProjectIdentifier();		
		
		Project newProject = projectService.findProjectByIdentifier(projectId);
		
		projectService.deleteProjectByIdentifier(projectId);
		
		newProject.setId(project.getId());
		newProject.setDescription(project.getDescription());
		newProject.setProjectIdentifier(project.getProjectIdentifier());
		newProject.setProjectName(project.getProjectName());
		newProject.setCreated_At(project.getCreated_At());
		newProject.setEnd_date(project.getEnd_date());
		newProject.setStart_date(project.getStart_date());
		newProject.setUpdated_At(project.getUpdated_At());
		
		projectService.saveOrUpdateProject(newProject);
		return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
	}
}
