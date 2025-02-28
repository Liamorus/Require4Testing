package model;

public class Testcase {
	private Integer testcaseId;
	private Integer testrun_Id;
	private String status;
	private Integer user_Id;
	private String description;
	private Integer requirement_Id;
	private String requirementTitle;

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

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}
}
