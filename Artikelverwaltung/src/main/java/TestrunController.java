import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class TestrunController implements Serializable {
	private Integer testrunId;
	private Integer runNr;
	private Integer user_Id;
	private Integer requirement_Id;
	private String requirementTitle;

	private List<Testrun> testruns;

	private Testrun currentTestrun = new Testrun();

	@PostConstruct
	public void init() {
		testruns = new ArrayList<>();
		loadTestruns();

	}

	public void loadTestruns() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		testruns.clear();

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			String sql = "SELECT tr.testrunid, tr.runnr, tr.requirement_id , r.title FROM testrun tr left join requirement r on r.requirementid = tr.requirement_id order by tr.requirement_id asc, tr.runnr asc";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Testrun testrunData = new Testrun();
				testrunData.setTestrunId(resultSet.getInt("testrunid"));
				testrunData.setRunNr(resultSet.getInt("runnr"));
				testrunData.setRequirement_Id(resultSet.getInt("requirement_id"));
				testrunData.setRequirementTitle(resultSet.getString("title"));
				testruns.add(testrunData);
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is diasbled
		return (requirement_Id == null || user_Id == null);
	}

	public String create() {
		// Connect to DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			System.out.println("Verbunden");

			String sqlRunNr = "SELECT COALESCE(MAX(runnr), 0) + 1 as runnr FROM public.testrun WHERE requirement_id = ?";
			PreparedStatement preparedStatementRunNr = connection.prepareStatement(sqlRunNr);
			preparedStatementRunNr.setInt(1, requirement_Id);
			ResultSet resultSetRunNr = preparedStatementRunNr.executeQuery();
			resultSetRunNr.next();
			Integer newRunNr = resultSetRunNr.getInt("runnr");

			String sql = "INSERT INTO testrun (runnr, requirement_id, user_id) VALUES (?, ?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, newRunNr);
			preparedStatement.setInt(2, requirement_Id);
			preparedStatement.setInt(3, user_Id);

			preparedStatement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Fehler bei Sqlverbindung");
			e.printStackTrace();
		}

		return "dashboard_TestManager?faces-redirect=true";
	}

	public String openCurrentTestrun(Testrun testrunDS) {
		currentTestrun.setTestrunId(testrunDS.getTestrunId());
		currentTestrun.setRunNr(testrunDS.getRunNr());
		currentTestrun.setRequirement_Id(testrunDS.getRequirement_Id());

		return "testrunOpen?faces-redirect=true";
	}

	// Getters and Setters
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

	public List<Testrun> getTestruns() {
		loadTestruns();
		return testruns;
	}

	public Testrun getCurrentTestrun() {
		return currentTestrun;
	}

	// Nested Testrun class
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
}
