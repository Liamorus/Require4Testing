import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;




@Named
@ViewScoped
public class RequirementController implements Serializable 
{
	private Integer userNew_Id;
	private Integer testuser_Id;
	private String title;
	private String description;
	
	// Getter Setter Controller for Insert
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
	
private List<Requirement> requirements;	
public RequirementController() {
    requirements = new ArrayList<>();
    loadRequirements();
}
public List<Requirement> getRequirements() {
    return requirements;
}
public void loadRequirements() {
    String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    String dbUsername = "postgres";
    String dbPassword = "admin";

    try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {
        String sql = "SELECT requirementid, usernew_id, title, description, testuser_id, done FROM requirement";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(); 
        while (resultSet.next()) {
            Requirement req = new Requirement();
            req.setRequirementId(resultSet.getInt("requirementid"));
            req.setUserNew_Id(resultSet.getInt("usernew_id"));
            req.setTitle(resultSet.getString("title"));
            req.setDescription(resultSet.getString("description"));
            req.setTestuser_Id(resultSet.getInt("testuser_id"));
            req.setDone(resultSet.getBoolean("done"));
            requirements.add(req);
            System.out.println("Title = " + resultSet.getString("title"));
		 	System.out.println("Description = " + resultSet.getString("description"));
        }
        connection.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}




//Class for List Objects
public class Requirement {
	
	private int requirementId;
    private int userNew_Id;
    private String title;
    private String description;
    private int testUser_Id;
    private boolean done;
    
    
    // Getter Setter for Requirement Class
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
		return testUser_Id;
	}
	public void setTestuser_Id(Integer testuser_Id) {
		this.testUser_Id = testuser_Id;
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
	
	//Redirect
    public String createRequirement_redirect()
    {
    	return "requirementCreation?faces-redirect=true";
    }
	
	
    //create Requirement
    public String create()
    {
    	//Connect to DB
    			String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    			String dbUsername = "postgres";
    			String dbPassword = "admin";
    			
    			
    			try {
    				
    				Class.forName("org.postgresql.Driver");
    				Connection connection = DriverManager.getConnection(jdbcURL,dbUsername,dbPassword);
    				System.out.println("Verbunden");
    				
    				
    				String sql = "INSERT INTO requirement (usernew_id, title, description, testuser_id) VALUES (?, ?, ?, ?)";


    				 PreparedStatement preparedStatement = connection.prepareStatement(sql);
    				
    				 preparedStatement.setInt(1, userNew_Id);
    				 preparedStatement.setString(2, title);
    				 preparedStatement.setString(3, description);
    				 preparedStatement.setInt(4, testuser_Id);
    				
    				 preparedStatement.executeUpdate();
    				 connection.close();
    				} 
    			catch (Exception e) 
    			{
					System.out.println("Fehler bei Sqlverbindung");	
					e.printStackTrace();
				}
    	
    	return "dashboard_Re?faces-redirect=true";
    }   
}