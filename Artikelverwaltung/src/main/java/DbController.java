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
public class DbController implements Serializable 
{
	
	
// Die Login-Methode	
public String login() {
        
    	Boolean cond = true;
    	//Connect to DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "admin";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			
			Connection connection = DriverManager.getConnection(jdbcURL,username,password);
			System.out.println("Verbunden");		
			String sql = "INSERT INTO artikel (name, description, price) \r\n"
					+ "VALUES (?,?,?);";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1,"Simons Weg zum weniger erfülltem leben");
			statement.setString(2,"Der Titel nur in 5 mal trauriger");
			statement.setInt(3,23);
			
			/*Normal statement 
			 Statement statement = connection.createStatement();
			 int rows = statement.executeUpdate(sql);
			*/
			int rows = statement.executeUpdate();
			
			if (rows > 0) {
				System.out.println("Datensatz eingefügt");
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Fehler bei Sqlverbindung");	
			e.printStackTrace();
		}		
		if (cond) {
		    return "index.xhtml"; 
		} else {
		    return "login.xhtml";
		}
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









}