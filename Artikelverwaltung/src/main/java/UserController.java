import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
@SessionScoped
public class UserController implements Serializable
{
	private String username;
    private String password;
    private Integer usertype;
   
    private User currentUser = new User();
    
    private List<User> users = new ArrayList<>();
    
    public List<User> getUsers() {
    	loadUsers();
        return users;
    }
    
//    @PostConstruct
//    public void init() {
//    	users = new ArrayList<>();
//    	loadUsers();
//    }
    public void loadUsers() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "admin";
        
        users.clear();
        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {
            String sql = "SELECT userid,username,usertype FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(); 
            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setUsertype(resultSet.getInt("usertype"));
 
                users.add(user);
                System.out.println("username = " + resultSet.getString("username"));
    		 	System.out.println("usertype = " + resultSet.getInt("usertype"));
    		 	System.out.println("username1 = " + currentUser.getUsername());
    		 	System.out.println("usertype1 = " + currentUser.getUsertype());
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public String login() 
    {  	
    	//Connect to DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String dbUsername = "postgres";
		String dbPassword = "admin";
		
		
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL,dbUsername,dbPassword);
			
			
			
			System.out.println("Verbunden");		
			String sql = "SELECT * FROM users WHERE username = '" + username + "';";
					//+ "WHERE USERNAME = '" + username + "'";

			
			

			 Statement statement = connection.createStatement();
			 ResultSet rs = statement.executeQuery(sql);
			 
			 connection.close();
			 if(rs.next())
			 {
				System.out.println("Username gefunden");
				if (rs.getString("PASSWORD").equals(password)) 
				{
					String destination = "login";
					
					System.out.println("Name = " + rs.getString("USERNAME"));
				 	System.out.println("id = " + rs.getInt("USERID"));
					System.out.println("Login erfolgreich");
					currentUser.setUserId(rs.getInt("USERID"));
					currentUser.setUsername(rs.getString("USERNAME"));
					currentUser.setUsertype(rs.getInt("USERTYPE"));
					
					switch (rs.getInt("USERTYPE")) {
					case 0:
						destination = "dashboard_Admin";
						break;
					case 1:
						destination = "dashboard_Re";
						break;
					case 2:
						destination = "dashboard_TestManager";
						break;
					case 3:
						destination = "dashboard_Tester";
						break;
					default:
						break;
					}
					
					return destination + "?faces-redirect=true";
					
					
					
					
				}
				else 
				{
					System.out.println("Passwort falsch");
				}
				
			 	
			 }
			 else
			 {
				 System.out.println("Username nicht gefunden");
			 }
		} 
		catch (Exception e) {
			System.out.println("Fehler bei Sqlverbindung");	
			e.printStackTrace();
		}     
		return "";
    
    
    }
    
    public String logout()
    {
    	return "login?faces-redirect=true";
    }
    
    
    // Validator
    // Überprüft die Inputs
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
    
    
    // GETTER SETTER
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
	
	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	
	// Embedded User Class
	public class User
	{
		private Integer userId;
		private String username;
	    private String password;
	    private Integer usertype;
	    
	    
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public Integer getUsertype() {
			return usertype;
		}
		public void setUsertype(Integer usertype) {
			this.usertype = usertype;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

	    
	}

}
