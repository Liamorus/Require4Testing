import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class UserController implements Serializable
{
	
	@Inject
	UserVerwaltung userVerwaltung;
	
	private static UserVerwaltung instance = new UserVerwaltung();

	private String username;
    private String password;
	
	
	
    public User getUser()
    {
        return userVerwaltung.getUserMenge().get(0);
    }
    
    public String checkUserPasswort()
    {
        return "test";
    }

    
	public static UserVerwaltung getInstance() {
		return instance;
	}
	
	public void test()
    {
		System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
	
	public void sqlTest()
    {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Connect to DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "admin";
		
		try {
			
			Connection connection = DriverManager.getConnection(jdbcURL,username,password);
			System.out.println("Verbunden");		
			String sql = "INSERT INTO artikel (name, description, price) \r\n"
					+ "VALUES ('Example Artikel', 'This is a description of the example artikel.', 19.99);";
			Statement statement = connection.createStatement();
			
			int rows = statement.executeUpdate(sql);
			
			if (rows > 0) {
				System.out.println("Datensatz eingefügt");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Fehler bei Sqlverbindung");	
			e.printStackTrace();
		}		
    }	
	
	
    public void loginValidator(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
    	String username = (String) value;
    	if (username.equals("1")) {
    		throw new ValidatorException(new FacesMessage("if", ""));
		}
    	else
    	{
    		throw new ValidatorException(new FacesMessage("else", ""));
    	}
    	       
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
