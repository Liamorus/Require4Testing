import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class TestcaseController implements Serializable {
    private Integer testcaseId;
    private Integer testrun_Id;
    private String status;
    private Integer user_Id;
    private String description;
    private Integer requirement_Id;

    private List<Testcase> testcases;
    private List<Testcase> filteredTestcases;
    private List<Testcase> availableTestcases;
    private List<Testcase> myTestcases;
    
    private Testcase currentTestcase = new Testcase();
    
    @Inject
    private TestrunController testrunController; // Inject UserController
    
    @Inject
    private UserController userController; // Inject UserController
    
    @PostConstruct
    public void init() {
        testcases = new ArrayList<>();
        filteredTestcases = new ArrayList<>();
        availableTestcases = new ArrayList<>();
        myTestcases = new ArrayList<>();
        loadTestcases();
    }

    public boolean checkCreateCondition() {
    	//If one of the following cases appear create is diasbled
        return (requirement_Id == null || description == null || description.trim().isEmpty());
    }
    
    
    public void loadTestcases() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        testcases.clear();

        try {
        	Class.forName("org.postgresql.Driver");
        	Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            String sql = "SELECT testcaseid,testrun_id, status,  description FROM testcase order by testcaseid";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Testcase testcaseData = new Testcase();
                testcaseData.setTestcaseId(resultSet.getInt("testcaseid"));
                testcaseData.setTestrun_Id(resultSet.getInt("testrun_id"));
                testcaseData.setStatus(resultSet.getString("status"));
                testcaseData.setDescription(resultSet.getString("description"));
                testcases.add(testcaseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void loadFilteredTestcases() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        filteredTestcases.clear();
        Integer currentTestrun_Id = testrunController.getCurrentTestrun().getTestrunId();
        try {
        	Class.forName("org.postgresql.Driver");
        	Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            String sql = "SELECT testcaseid, testrun_id, status,  description FROM testcase where testrun_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentTestrun_Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Testcase testcaseData = new Testcase();
                testcaseData.setTestcaseId(resultSet.getInt("testcaseid"));
                testcaseData.setTestrun_Id(resultSet.getInt("testrun_id"));
                testcaseData.setStatus(resultSet.getString("status"));
                testcaseData.setDescription(resultSet.getString("description"));
                filteredTestcases.add(testcaseData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean checkConnectCondition() {
        return testcaseId != null;
    }
    
    
    public void loadMyTestcases() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        myTestcases.clear();
        //Integer currentTestrun_Id = testrunController.getCurrentTestrun().getTestrunId();
        try {
        	Class.forName("org.postgresql.Driver");
        	Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            String sql = "SELECT tc.testcaseid, tc.testrun_id, tc.status,  tc.description FROM testcase tc left join testrun tr on tr.testrunid = tc.testrun_id where tr.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userController.getCurrentUser().getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Testcase testcaseData = new Testcase();
                testcaseData.setTestcaseId(resultSet.getInt("testcaseid"));
                testcaseData.setTestrun_Id(resultSet.getInt("testrun_id"));
                testcaseData.setStatus(resultSet.getString("status"));
                testcaseData.setDescription(resultSet.getString("description"));
                myTestcases.add(testcaseData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getStatusText(Testcase testcase) {
        if ("SUCCESSFUL".equals(testcase.getStatus())) {
            return "Erledigt";
        } else if ("FAILED".equals(testcase.getStatus())) {
            return "Fehlgeschlagen";
        }
        return "";
    }
    
    public void loadAvailabTestcases() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        availableTestcases.clear();
        Integer currentTestrun_requirementId = testrunController.getCurrentTestrun().getRequirement_Id();
        try {
        	Class.forName("org.postgresql.Driver");
        	Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            String sql = "SELECT testcaseid, testrun_id, status,  description FROM testcase where testrun_id is null and requirement_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentTestrun_requirementId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Testcase testcaseData = new Testcase();
                testcaseData.setTestcaseId(resultSet.getInt("testcaseid"));
                testcaseData.setTestrun_Id(resultSet.getInt("testrun_id"));
                testcaseData.setStatus(resultSet.getString("status"));
                testcaseData.setDescription(resultSet.getString("description"));
                availableTestcases.add(testcaseData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String setStatusFailed(Testcase testcase)
    {
    	//Connect to DB
    	String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    	String dbUsername = "postgres";
    	String dbPassword = "admin";

    	try {
    		
    		Class.forName("org.postgresql.Driver");
    		Connection connection = DriverManager.getConnection(jdbcURL,dbUsername,dbPassword);
    		System.out.println("Verbunden");
    		
    		
    		String sql = "UPDATE testcase SET status = ? WHERE testcaseid = ?";


    		 PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		
    		 preparedStatement.setString(1, "FAILED"); 
    		 preparedStatement.setInt(2, testcase.getTestcaseId());//aus objekt holen
    		
    		 preparedStatement.executeUpdate();
    		 connection.close();
    		} 
    	catch (Exception e) 
    	{
    		System.out.println("Fehler bei Sqlverbindung");	
    		e.printStackTrace();
    	}
    	return "dashboard_testrunOpen?faces-redirect=true";
    }  
    
    
    public String setStatusSuccessful(Testcase testcase)
    {
    	//Connect to DB
    	String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    	String dbUsername = "postgres";
    	String dbPassword = "admin";

    	try {
    		
    		Class.forName("org.postgresql.Driver");
    		Connection connection = DriverManager.getConnection(jdbcURL,dbUsername,dbPassword);
    		System.out.println("Verbunden");
    		
    		
    		String sql = "UPDATE testcase SET status = ? WHERE testcaseid = ?";


    		 PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		
    		 preparedStatement.setString(1, "SUCCESSFUL"); 
    		 preparedStatement.setInt(2, testcase.getTestcaseId());//aus objekt holen
    		
    		 preparedStatement.executeUpdate();
    		 connection.close();
    		} 
    	catch (Exception e) 
    	{
    		System.out.println("Fehler bei Sqlverbindung");	
    		e.printStackTrace();
    	}
    	return "dashboard_testrunOpen?faces-redirect=true";
    }  
    
    
    
    public void setStatusDone() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        testcases.clear();

        try {
        	Class.forName("org.postgresql.Driver");
        	Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            String sql = "SELECT testcaseid,testrun_id, status,  description FROM testcase order by testcaseid";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Testcase testcaseData = new Testcase();
                testcaseData.setTestcaseId(resultSet.getInt("testcaseid"));
                testcaseData.setTestrun_Id(resultSet.getInt("testrun_id"));
                testcaseData.setStatus(resultSet.getString("status"));
                testcaseData.setDescription(resultSet.getString("description"));
                testcases.add(testcaseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
  
    public String connectCasewithRun()
    {
    	//Connect to DB
    	String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    	String dbUsername = "postgres";
    	String dbPassword = "admin";

    	try {
    		
    		Class.forName("org.postgresql.Driver");
    		Connection connection = DriverManager.getConnection(jdbcURL,dbUsername,dbPassword);
    		System.out.println("Verbunden");
    		
    		
    		String sql = "UPDATE testcase SET testrun_id = ? WHERE testcaseid = ?";


    		 PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		
    		 preparedStatement.setInt(1, testrunController.getCurrentTestrun().getTestrunId());
    		 preparedStatement.setInt(2, testcaseId);
    		
    		 preparedStatement.executeUpdate();
    		 connection.close();
    		} 
    	catch (Exception e) 
    	{
    		System.out.println("Fehler bei Sqlverbindung");	
    		e.printStackTrace();
    	}
    	return "dashboard_testrunOpen?faces-redirect=true";
    }  
   
    public String openCurrentTestcase(Testcase testcaseDS) 
	{
		currentTestcase.setTestcaseId(testcaseDS.getTestcaseId());
		return "testcaseOpen?faces-redirect=true";
	}
    
    
    
  //create testcase
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
    		
    		
    		String sql = "INSERT INTO testcase (description, status, requirement_id) VALUES (?, ?, ?)";


    		 PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		
    		 preparedStatement.setString(1, description);
    		 preparedStatement.setBoolean(2, false);
    		 preparedStatement.setInt(3, requirement_Id);

    		
    		 preparedStatement.executeUpdate();
    		 connection.close();
    		} 
    	catch (Exception e) 
    	{
    		System.out.println("Fehler bei Sqlverbindung");	
    		e.printStackTrace();
    	}
    	
    	return "dashboard_Testfall?faces-redirect=true";
    }  
    
    
   
 		
    
    
    
    // Getters and Setters
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
    
    public Integer getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
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
	
	public Testcase getCurrentTestcase() 
	{
		return currentTestcase;
	}
	public void setCurrentTestcase(Testcase testcase) 
	{
		this.currentTestcase = testcase;
	}

	public List<Testcase> getTestcases() {
		loadTestcases();
        return testcases;
    }
	public List<Testcase> getFilteredTestcases() {
		loadFilteredTestcases();
        return filteredTestcases;
    }
	public List<Testcase> getAvailableTestcases() {
		loadAvailabTestcases();
        return availableTestcases;
    }
	public List<Testcase> getMyTestcases() {
		loadMyTestcases();
        return myTestcases;
    }
	
    // Nested Testcase class
    public class Testcase {
        private Integer testcaseId;
        private Integer testrun_Id;
        private String status;
        private Integer user_Id;
        private String description;
        private Integer requirement_Id;

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
    }
}
