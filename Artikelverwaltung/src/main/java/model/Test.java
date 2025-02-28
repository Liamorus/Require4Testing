package model;

public class Test {

	private Integer testId;
	private Integer testrun_Id;
	private Integer testCase_Id;
	private Integer user_Id;
	private boolean done;

	// Getter Setter
	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getTestrun_Id() {
		return testrun_Id;
	}

	public void setTestrun_Id(Integer testrun_Id) {
		this.testrun_Id = testrun_Id;
	}

	public Integer getTestCase_Id() {
		return testCase_Id;
	}

	public void setTestCase_Id(Integer testCase_Id) {
		this.testCase_Id = testCase_Id;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
}