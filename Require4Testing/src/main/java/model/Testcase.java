package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "testcase")
public class Testcase {

	public Testcase() {}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer testcaseId;
	
	@Column(name = "testrun_id")
	private Integer testrun_Id;
	
	@Column
	private String status;
	
	@Column(name = "user_id")
	private Integer user_Id;
	
	@Column
	private String description;
	
	@Column(name = "requirement_id")
	private Integer requirement_Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "testrun_id", insertable = false, updatable = false)
	private Testrun testrun;

	public Testrun getTestrun() {
	    return testrun;
	}

	public void setTestrun(Testrun testrun) {
	    this.testrun = testrun;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="requirement_id", insertable=false, updatable=false)
	private Requirement requirement;
	
	public Requirement getRequirement() {
	    return requirement;
	}
	
	public void setRequirement(Requirement requirement) {
	    this.requirement = requirement;
	}

	// Getters and Setters for Testcase properties
	public Integer getTestcaseId() {
		return testcaseId;
	}

	public void setTestcaseId(Integer testcaseId) {
		this.testcaseId = testcaseId;
	}

	public Integer getTestrun_Id() {
		return testrun_Id;
	}

	public void setTestrun_Id(Integer testrun_Id) {
		this.testrun_Id = testrun_Id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRequirement_Id() {
		return requirement_Id;
	}

	public void setRequirement_Id(Integer requirement_Id) {
		this.requirement_Id = requirement_Id;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}
}
