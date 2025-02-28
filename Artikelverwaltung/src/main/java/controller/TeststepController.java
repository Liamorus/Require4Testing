package controller;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.TestStep;

@Named
@SessionScoped
public class TeststepController implements Serializable {

	private Integer teststepId;
	private Integer testcase_Id;
	private String testcaseDescription;
	private String title;

	private List<TestStep> testSteps;
	private List<TestStep> filteredTestSteps;

	@Inject
	private TestcaseController testcaseController;

	@Inject
	private TestrunController testrunController;

	@PostConstruct
	public void init() {
		testSteps = new ArrayList<>();
		filteredTestSteps = new ArrayList<>();
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is diasbled
		return (testcase_Id == null || title == null || title.trim().isEmpty());
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

			String sql = "INSERT INTO teststep (testcase_id, title) VALUES (?, ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, testcase_Id);
			preparedStatement.setString(2, title);

			preparedStatement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Fehler bei Sqlverbindung");
			e.printStackTrace();
		}
		loadTestSteps();
		return "dashboard_Testfall?faces-redirect=true";
	}

	public void loadTestSteps() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		testSteps.clear();

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			String sql = "SELECT ts.teststepid, ts.testcase_id, ts.title, tc.description FROM teststep ts left join testcase tc on tc.testcaseid = ts.testcase_id order by ts.testcase_id";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TestStep stepData = new TestStep();
				stepData.setTeststepId(resultSet.getInt("teststepid"));
				stepData.setTestcase_Id(resultSet.getInt("testcase_id"));
				stepData.setTitle(resultSet.getString("title"));
				stepData.setTestcaseDescription(resultSet.getString("description"));
				testSteps.add(stepData);
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadfilteredTestSteps() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";
		Integer testcaseId;
		filteredTestSteps.clear();
		if (testcaseController.getCurrentTestcase().getTestcaseId() != null) {
			testcaseId = testcaseController.getCurrentTestcase().getTestcaseId();
		} else {
			testcaseId = testrunController.getCurrentTestrun().getTestrunId();
		}

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			String sql = "SELECT ts.teststepid, ts.testcase_id, ts.title, tc.description FROM teststep ts left join testcase tc on tc.testcaseid = ts.testcase_id where ts.testcase_id = ? order by teststepid";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, testcaseId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TestStep stepData = new TestStep();
				stepData.setTeststepId(resultSet.getInt("teststepid"));
				stepData.setTestcase_Id(resultSet.getInt("testcase_id"));
				stepData.setTitle(resultSet.getString("title"));
				stepData.setTestcaseDescription(resultSet.getString("description"));
				filteredTestSteps.add(stepData);
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Getters and Setter
	public Integer getTeststepId() {
		return teststepId;
	}

	public void setTeststepId(Integer teststepId) {
		this.teststepId = teststepId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTestcase_Id() {
		return testcase_Id;
	}

	public void setTestcase_Id(Integer testcase_Id) {
		this.testcase_Id = testcase_Id;
	}

	public List<TestStep> getTestSteps() {
		loadTestSteps();
		return testSteps;
	}

	public List<TestStep> getfilteredTestSteps() {
		loadfilteredTestSteps();

		return filteredTestSteps;
	}

	public String getTestcaseDescription() {
		return testcaseDescription;
	}

	public void setTestcaseDescription(String testcaseDescription) {
		this.testcaseDescription = testcaseDescription;
	}

}