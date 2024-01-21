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
	private Integer testrunId;
	private Integer runNr;
	private Integer userNew_Id;
	private Integer requirement_Id;
	
	
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
					
					String sql = "INSERT INTO testrun ( runnr, requirement_id) VALUES (?, ?)";


					 PreparedStatement preparedStatement = connection.prepareStatement(sql);
					
					 preparedStatement.setInt(1, runNr);
					 preparedStatement.setInt(2, requirement_Id);

					
					 preparedStatement.executeUpdate();
					 connection.close();
					} 
				catch (Exception e) 
				{
					System.out.println("Fehler bei Sqlverbindung");	
					e.printStackTrace();
				}
		
		return "dashboard_TestManager?faces-redirect=true";
	}   
	
	
	// Getter Setter
	public Integer getTestrunId() {
		return testrunId;
	}
	public void setTestrunId(Integer testrunId) {
		this.testrunId = testrunId;
	}
	public Integer getRunNr() {
		return runNr;
	}
	public void setRunNr(Integer run) {
		this.runNr = run;
	}
	public Integer getUserNew_Id() {
		return userNew_Id;
	}
	public void setUserNew_Id(Integer userNew_Id) {
		this.userNew_Id = userNew_Id;
	}
	public Integer getRequirement_Id() {
		return requirement_Id;
	}
	public void setRequirement_Id(Integer requirement_Id) {
		this.requirement_Id = requirement_Id;
	}
	
	
	
}