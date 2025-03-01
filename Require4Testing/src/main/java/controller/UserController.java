package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.User;

@Named
@SessionScoped
public class UserController implements Serializable {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Require4TestingPU");
	
	private String username;
	private String password;
	private Integer usertype;

	private User currentUser = new User();

	private List<User> users;

	private List<User> tester;

	@PostConstruct
	public void init() {
		users = new ArrayList<>();
		tester = new ArrayList<>();
	}

	public List<User> getTester() {
		loadTester();
		return tester;
	}

	public List<User> getUsers() {
		loadUsers();
		return users;
	}

	public void loadTester() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.usertype = :utype", User.class);
            query.setParameter("utype", 4);
            tester = query.getResultList();
        } finally {
            em.close();
        }
    }

    public void loadUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
        } finally {
            em.close();
        }
    }

    public String login() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :uname", User.class);
            query.setParameter("uname", username);
            List<User> result = query.getResultList();
            if (!result.isEmpty()) {
                User user = result.get(0);
                if (user.getPassword().equals(password)) {
                    System.out.println("Login successful");
                    currentUser = user;
                    String destination = "login"; // default destination
                    int utype = user.getUsertype();
                    switch (utype) {
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
                            destination = "dashboard_Testfall";
                            break;
                        case 4:
                            destination = "dashboard_Tester";
                            break;
                        default:
                            break;
                    }
                    return destination + "?faces-redirect=true";
                } else {
                    System.out.println("Wrong password");
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Falsches Passwort"));
                }
            } else {
                System.out.println("Username not found");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Benutzername nicht gefunden"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return "";
    }


	public String logout() {
		return "login?faces-redirect=true";
	}

	// Validator
	// Checks the Inputs
	public void loginValidator(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String username = (String) value;
		if (username.equals("1")) {
			throw new ValidatorException(new FacesMessage("if", ""));
		} else {
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

	public User getCurrentUser() {
		return this.currentUser;
	}
}
