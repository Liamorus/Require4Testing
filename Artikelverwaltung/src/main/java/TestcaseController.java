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
public class TestcaseController implements Serializable {
    private String testcaseId;
    private String testrun_Id;
    private String status;
    private String userNew_Id;
    private String description;

    private List<Testcase> testcases;

    @PostConstruct
    public void init() {
        testcases = new ArrayList<>();
        loadTestcases();
    }

    public void loadTestcases() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";

        testcases.clear();

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {
            String sql = "SELECT testcaseid, testrun_id, status,  description FROM testcase";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Testcase testcaseData = new Testcase();
                testcaseData.setTestcaseId(resultSet.getString("testcaseid"));
                testcaseData.setTestrun_Id(resultSet.getString("testrun_id"));
                testcaseData.setStatus(resultSet.getString("status"));
                testcaseData.setDescription(resultSet.getString("description"));
                testcases.add(testcaseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getTestcaseId() {
        return testcaseId;
    }
    public void setTestcaseId(String testcaseId) {
        this.testcaseId = testcaseId;
    }
    public String getTestrun_Id() {
        return testrun_Id;
    }
    public void setTestrun_Id(String testrun_Id) {
        this.testrun_Id = testrun_Id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUserNew_Id() {
        return userNew_Id;
    }
    public void setUserNew_Id(String userNew_Id) {
        this.userNew_Id = userNew_Id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Testcase> getTestcases() {
        return testcases;
    }

    // Nested Testcase class
    public class Testcase {
        private String testcaseId;
        private String testrun_Id;
        private String status;
        private String userNew_Id;
        private String description;

        // Getters and Setters for Testcase properties
        public String getTestcaseId() {
            return testcaseId;
        }
        public void setTestcaseId(String testcaseId) {
            this.testcaseId = testcaseId;
        }
        public String getTestrun_Id() {
            return testrun_Id;
        }
        public void setTestrun_Id(String testrun_Id) {
            this.testrun_Id = testrun_Id;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getUserNew_Id() {
            return userNew_Id;
        }
        public void setUserNew_Id(String userNew_Id) {
            this.userNew_Id = userNew_Id;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }
}
