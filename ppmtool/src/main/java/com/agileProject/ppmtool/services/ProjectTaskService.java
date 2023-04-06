package com.agileProject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agileProject.ppmtool.domain.Backlog;
import com.agileProject.ppmtool.domain.Project;
import com.agileProject.ppmtool.domain.ProjectTask;
import com.agileProject.ppmtool.exceptions.ProjectNotFoundException;
import com.agileProject.ppmtool.repositories.BacklogRepository;
import com.agileProject.ppmtool.repositories.ProjectRepository;
import com.agileProject.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		//Exception : project not found
		try {
			//PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//set the BL to PT
			projectTask.setBacklog(backlog);
			//we want our projectSequence to be like this : IDPRO-1 , IDPRO-2 ... 100, 101...
			Integer BacklogSequence = backlog.getPTsequence();
			//update the backlog SEQUENCE
			BacklogSequence++;
			
			backlog.setPTsequence(BacklogSequence);
			//Add sequence to ProjectTask
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			
			//INITIAL priority when priority is null
			if(projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}
			
			//INITIAL STATUS when status is null
			if(projectTask.getStatus() == null || projectTask.getStatus()=="") {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepository.save(projectTask);
		}catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found with ID : "+projectIdentifier);
		}
		
		
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		
		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		if(project == null) {
			throw new ProjectNotFoundException("Project With ID : '"+backlog_id+"' does not exists");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	public ProjectTask findPTbyProjectSequence(String backlog_id, String pt_id) {
		//make sure we are searching on the right backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project With ID : '"+backlog_id+"' does not exists");
		}
		
		//make sure that task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task : '"+pt_id+"'  not found");
		}
		
		//make sure that backlog/project id is in the path corresponds to the right path
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task : '"+pt_id+"' does not exists in Project :'"+backlog_id+"'");
		}
		
		return projectTask;
	}

	//update project task
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		//find existing project task
		ProjectTask projectTask = findPTbyProjectSequence(backlog_id, pt_id);
		//replace it with updated task
		projectTask = updatedTask;
		//save update
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTbyProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTbyProjectSequence(backlog_id, pt_id);
		
		projectTaskRepository.delete(projectTask);
	}
}
