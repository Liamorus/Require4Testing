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
@Table(name = "testrun")
public class Testrun {
	
	public Testrun() {}
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer testrunId;
	
	@Column(name = "runnr")
	private Integer runNr;
	
	@Column(name = "user_id")
	private Integer user_Id;
	
	@Column(name = "requirement_id")
	private Integer requirement_Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="requirement_id", insertable=false, updatable=false)
	private Requirement requirement;
	
	public Requirement getRequirement() {
	    return requirement;
	}
	
	public void setRequirement(Requirement requirement) {
	    this.requirement = requirement;
	}

	// Getters and Setters for Testrun properties
	public Integer getTestrunId() {
		return testrunId;
	}

	public void setTestrunId(Integer testrunId) {
		this.testrunId = testrunId;
	}

	public Integer getRunNr() {
		return runNr;
	}

	public void setRunNr(Integer runNr) {
		this.runNr = runNr;
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