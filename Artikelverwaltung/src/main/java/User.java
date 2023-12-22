import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
    private String userName;
    private String password;

    public User()
    {
    	
    }

    public User(String userName, String password)
    {
        this.setUserName(userName);
        this.setPassword(password);
    }
    
    public void getUserArray() {
		
	}
    
    
    
    
    
    
    
    

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
