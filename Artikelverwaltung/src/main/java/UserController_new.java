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
public class UserController_new implements Serializable
{
	private String username;
    private String password;
    private Integer usertype;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
    //
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
}
