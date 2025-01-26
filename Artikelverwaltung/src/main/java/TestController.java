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

@Named
@SessionScoped
public class TestController implements Serializable {
	@Inject
	private UserController userController; // Inject UserController

	private Integer testrunId;
	private Integer testRunNr;
	private Integer testCaseId;
	private Integer user_Id;
	private Integer requirement_Id;

	private List<Test> tests;
	private List<Test> yourTests;

	private Test currentTest = new Test();

	// Postconstruct to Load Datasets
	@PostConstruct
	public void init() {
		tests = new ArrayList<>();
		yourTests = new ArrayList<>();
	}

	public List<Test> getTests() {
		if (tests.isEmpty()) {
			loadTests();
		}
		return tests;
	}

	public List<Test> getYourTests() {
		if (yourTests.isEmpty()) {
			loadYourTests();
		}
		return yourTests;
	}

	public void loadYourTests() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		tests.clear();

		Integer userId = userController.getCurrentUser().getUserId();

		try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {

			String sql = "SELECT testid, testrun_id, testcase_id, user_id, done FROM test where user_id = ? order by testrun_id desc";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Test testData = new Test();
				testData.setTestId(resultSet.getInt("testId"));
				testData.setTestrun_Id(resultSet.getInt("testrun_Id"));
				testData.setTestCase_Id(resultSet.getInt("testcase_Id"));
				testData.setUser_Id(resultSet.getInt("user_Id"));
				testData.setDone(resultSet.getBoolean("done"));
				yourTests.add(testData);
//	          System.out.println("Title = " + resultSet.getString("title"));
//			 	System.out.println("Description = " + resultSet.getString("description"));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String openCurrentTest(Test testDS) {
		currentTest.setTestId(testDS.getTestId());
		currentTest.setUser_Id(testDS.getUser_Id());
		currentTest.setTestCase_Id(testDS.getTestCase_Id());

		return "testOpen?faces-redirect=true";
	}

	public void loadTests() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		tests.clear();

		try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {
			String sql = "SELECT testid, testrun_id, testcase_id, user_id, done FROM test order by testrun_id desc";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Test testData = new Test();
				testData.setTestId(resultSet.getInt("testId"));
				testData.setTestrun_Id(resultSet.getInt("testrun_Id"));
				testData.setTestCase_Id(resultSet.getInt("testcase_Id"));
				testData.setUser_Id(resultSet.getInt("user_Id"));
				testData.setDone(resultSet.getBoolean("done"));
				tests.add(testData);
//	            System.out.println("Title = " + resultSet.getString("title"));
//			 	System.out.println("Description = " + resultSet.getString("description"));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String setTestDone(Test test) {
		// Connect to DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		try {

			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			System.out.println("Verbunden");

			String sql = "UPDATE test\r\n" + "SET done = ? \n" + "WHERE testid = ?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setBoolean(1, true);
			preparedStatement.setInt(2, test.getTestId());

			preparedStatement.executeUpdate();
			connection.close();
			test.setDone(true);
		} catch (Exception e) {
			System.out.println("Fehler bei Sqlverbindung");
			e.printStackTrace();
		}

		return "dashboard_Tester?faces-redirect=true";
	}

	// Getter Setter
	public Integer getTestrunId() {
		return testrunId;
	}

	public void setTestrunId(Integer testrunId) {
		this.testrunId = testrunId;
	}

	public Integer getRequirement_Id() {
		return requirement_Id;
	}

	public void setRequirement_Id(Integer requirement_Id) {
		this.requirement_Id = requirement_Id;
	}

	public Integer getTestRunNr() {
		return testRunNr;
	}

	public void setTestRunNr(Integer testRunNr) {
		this.testRunNr = testRunNr;
	}

	public Integer getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public Test getCurrentTest() {
		return currentTest;
	}

	public void setCurrentTest(Test test) {
		this.currentTest = test;
	}

	// Nested Class for List Objects
	public class Test {

		private Integer testId;
		private Integer testrun_Id;
		private Integer testCase_Id;
		private Integer user_Id;
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

}