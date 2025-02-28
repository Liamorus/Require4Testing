package controller;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import model.Requirement;

@Named
@SessionScoped
public class RequirementController implements Serializable {
	private Integer requirementId;
	private String title;
	private String description;

	// Getter Setter Controller for Insert
	public int getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Integer requirementId) {
		this.requirementId = requirementId;
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

//Postconstruct to Load Datasets
	@PostConstruct
	public void init() {
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

		requirements.clear();

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			String sql = "SELECT requirementid,  title, description, testuser_id, done FROM requirement order by requirementid desc";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Requirement req = new Requirement();
				req.setRequirementId(resultSet.getInt("requirementid"));
				req.setTitle(resultSet.getString("title"));
				req.setDescription(resultSet.getString("description"));

				requirements.add(req);
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is diasbled
		return (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty());
	}

//Redirect
	public String createRequirement_redirect() {
		return "requirementCreation?faces-redirect=true";
	}

//create Requirement
	public String create() {
		// Connect to DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";

		try {

			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
			System.out.println("Verbunden");

			String sql = "INSERT INTO requirement (title, description) VALUES (?, ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, title);
			preparedStatement.setString(2, description);

			preparedStatement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println("Fehler bei Sqlverbindung");
			e.printStackTrace();
		}
		loadRequirements();
		return "dashboard_Re?faces-redirect=true";
	}
}