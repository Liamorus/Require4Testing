package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "requirement")
public class Requirement {

	public Requirement() {}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requirementId;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String testuser_id;

	@Column
	private Boolean done;

	// Getter Setter for Requirement Class
	public Integer getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Integer requirementId) {
		this.requirementId = requirementId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTestuser_id() {
		return testuser_id;
	}

	public void setTestuser_id(String testuser_id) {
		this.testuser_id = testuser_id;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}
}