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
@Table(name = "teststep")
public class TestStep {
	
	public TestStep() {}

	@Id
	@Column(name = "teststepid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer teststepId;
	
	@Column(name = "testcase_id")
	private Integer testcase_Id;
	
	@Column
	private String title;
	
	@Column(name = "testcaseDescription")
	private String testcaseDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="testcase_id", insertable=false, updatable=false)
	private Testcase testcase;
	
	// Getters and Setters
	public Testcase getTestcase() {
	    return testcase;
	}
	
	public void setTestcase(Testcase testcase) {
	    this.testcase = testcase;
	}
	
	public Integer getTeststepId() {
		return teststepId;
	}

	public void setTeststepId(Integer teststepId) {
		this.teststepId = teststepId;
	}

	public Integer getTestcase_Id() {
		return testcase_Id;
	}

	public void setTestcase_Id(Integer testcase_Id) {
		this.testcase_Id = testcase_Id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTestcaseDescription() {
		return testcaseDescription;
	}

	public void setTestcaseDescription(String testcaseDescription) {
		this.testcaseDescription = testcaseDescription;
	}

}