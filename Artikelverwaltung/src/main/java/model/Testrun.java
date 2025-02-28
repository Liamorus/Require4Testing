package model;

public class Testrun {
	private Integer testrunId;
	private Integer runNr;
	private Integer user_Id;
	private Integer requirement_Id;
	private String requirementTitle;

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

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}
}