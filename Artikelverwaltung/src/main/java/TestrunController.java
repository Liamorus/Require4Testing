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
public class TestrunController implements Serializable 
{
	private String testrunId;
	private String run;
	private String userNew_Id;
	private String requirement_Id;
	
	
	
	// Getter Setter
	public String getTestrunId() {
		return testrunId;
	}
	public void setTestrunId(String testrunId) {
		this.testrunId = testrunId;
	}
	public String getRun() {
		return run;
	}
	public void setRun(String run) {
		this.run = run;
	}
	public String getUserNew_Id() {
		return userNew_Id;
	}
	public void setUserNew_Id(String userNew_Id) {
		this.userNew_Id = userNew_Id;
	}
	public String getRequirement_Id() {
		return requirement_Id;
	}
	public void setRequirement_Id(String requirement_Id) {
		this.requirement_Id = requirement_Id;
	}
	
	
}