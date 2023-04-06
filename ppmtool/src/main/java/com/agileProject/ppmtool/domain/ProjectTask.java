package com.agileProject.ppmtool.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;

@Entity
public class ProjectTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable = false, unique = true)
	private String projectSequence;
	
	@NotBlank(message = "Please include a project summery")
	private String summery;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	private Date dueDate;
	
	//ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog ;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	private Date create_At;
	private Date update_At;
	
	@PrePersist
	protected void onCreate() {
		this.create_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.update_At = new Date();
	}

	public ProjectTask() {
	}

	public ProjectTask(Long id, String projectSequence,
			@NotBlank(message = "Please include a project summery") String summery, String acceptanceCriteria,
			String status, Integer priority, Date dueDate, String projectIdentifier, Date create_At, Date update_At) {
		super();
		this.id = id;
		this.projectSequence = projectSequence;
		this.summery = summery;
		this.acceptanceCriteria = acceptanceCriteria;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
		this.projectIdentifier = projectIdentifier;
		this.create_At = create_At;
		this.update_At = update_At;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getCreate_At() {
		return create_At;
	}

	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}

	public Date getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String toString() {
		return "ProjectTask ["+
				"id=" + id + 
				", projectSequence=" + projectSequence + 
				", summery=" + summery+ 
				", acceptanceCriteria=" + acceptanceCriteria + 
				", status=" + status + 
				", priority=" + priority+ 
				", dueDate=" + dueDate + 
				", projectIdentifier=" + projectIdentifier + 
				", create_At=" + create_At+ 
				", update_At=" + update_At + "]";
	}
	
	
	
}
