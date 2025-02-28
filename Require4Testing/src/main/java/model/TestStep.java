package model;

public class TestStep {
	private Integer teststepId;
	private Integer testcase_Id;
	private String title;
	private String testcaseDescription;

	// Getters and Setters
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