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
public class TestStepController implements Serializable 
{
	private String testcase_Id;
	private String userNew_Id;
	private String title;
	
	
	
	// Getter Setter
	public String getUserNew_Id() {
		return userNew_Id;
	}
	public void setUserNew_Id(String userNew_Id) {
		this.userNew_Id = userNew_Id;
	}
	public String getTestcase_Id() {
		return testcase_Id;
	}
	public void setTestcase_Id(String testcase_Id) {
		this.testcase_Id = testcase_Id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}