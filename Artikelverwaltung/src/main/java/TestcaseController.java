import java.io.Serializable;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class TestcaseController implements Serializable 
{
	private String testcaseId;
	private String testrun_Id;
	private String status;
	private String userNew_Id;
	private String description;
	
	// Getter Setter
	public String getTestcaseId() {
		return testcaseId;
	}
	public void setTestcaseId(String testcaseId) {
		this.testcaseId = testcaseId;
	}
	public String getTestrun_Id() {
		return testrun_Id;
	}
	public void setTestrun_Id(String testrun_Id) {
		this.testrun_Id = testrun_Id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserNew_Id() {
		return userNew_Id;
	}
	public void setUserNew_Id(String userNew_Id) {
		this.userNew_Id = userNew_Id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}