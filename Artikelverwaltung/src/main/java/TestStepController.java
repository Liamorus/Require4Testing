import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class TestStepController implements Serializable {
    private String testcase_Id;
    private String userNew_Id;
    private String title;

    private List<TestStep> testSteps;

    @PostConstruct
    public void init() {
        testSteps = new ArrayList<>();
        loadTestSteps();
    }

    public void loadTestSteps() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        testSteps.clear();

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {
            String sql = "SELECT teststepid, testcase_id, title FROM teststep order by teststepid";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(); 
            while (resultSet.next()) {
                TestStep stepData = new TestStep();
                stepData.setTeststepId(resultSet.getString("teststepid"));
                stepData.setTestcase_Id(resultSet.getString("testcase_id"));
                stepData.setTitle(resultSet.getString("title"));
                testSteps.add(stepData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
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
    public List<TestStep> getTestSteps() {
        return testSteps;
    }

    // Nested Class for TestStep
    public class TestStep {
        private String teststepId;
        private String testcase_Id;
        private String userNew_Id;
        private String title;

        // Getters and Setters
        public String getTeststepId() {
            return teststepId;
        }
        public void setTeststepId(String teststepId) {
            this.teststepId = teststepId;
        }
        public String getTestcase_Id() {
            return testcase_Id;
        }
        public void setTestcase_Id(String testcase_Id) {
            this.testcase_Id = testcase_Id;
        }
        public String getUserNew_Id() {
            return userNew_Id;
        }
        public void setUserNew_Id(String userNew_Id) {
            this.userNew_Id = userNew_Id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
    }
}