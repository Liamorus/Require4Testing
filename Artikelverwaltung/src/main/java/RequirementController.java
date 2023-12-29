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
public class RequirementController implements Serializable 
{
	private Integer requirementId;
	private Integer userNew_Id;
	private Integer testuser_Id;
	private String title;
	private String description;
	private boolean done;
	
	// Getter Setter
	public Integer getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Integer requirementId) {
		this.requirementId = requirementId;
	}
	public Integer getUserNew_Id() {
		return userNew_Id;
	}
	public void setUserNew_Id(Integer userNew_Id) {
		this.userNew_Id = userNew_Id;
	}
	public Integer getTestuser_Id() {
		return testuser_Id;
	}
	public void setTestuser_Id(Integer testuser_Id) {
		this.testuser_Id = testuser_Id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	
	
	
}