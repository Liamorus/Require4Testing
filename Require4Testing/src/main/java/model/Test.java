package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class Test {

	public Test() {}
	
	@Id
	@Column(name = "testid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer testId;
	
	@Column(name = "testrun_id")
	private Integer testrun_Id;
	
	@Column(name = "testCase_id")
	private Integer testCase_Id;
	
	@Column(name = "user_id")
	private Integer user_Id;
	
	@Column
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