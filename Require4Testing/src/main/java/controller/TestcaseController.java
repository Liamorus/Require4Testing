package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Testcase;

@Named
@SessionScoped
public class TestcaseController implements Serializable {
	
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Require4TestingPU");
	
	private Integer testcaseId;
	private Integer testrun_Id;
	private String status;
	private Integer user_Id;
	private String description;
	private Integer requirement_Id;
	private String requirementTitle;

	private List<Testcase> testcases;
	private List<Testcase> filteredTestcases;
	private List<Testcase> availableTestcases;
	private List<Testcase> myTestcases;

	private Testcase currentTestcase = new Testcase();

	@Inject
	private TestrunController testrunController; // Inject UserController

	@Inject
	private UserController userController; // Inject UserController

	@PostConstruct
	public void init() {
		testcases = new ArrayList<>();
		filteredTestcases = new ArrayList<>();
		availableTestcases = new ArrayList<>();
		myTestcases = new ArrayList<>();
		loadTestcases();
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is diasbled
		return (requirement_Id == null || description == null || description.trim().isEmpty());
	}

	public void loadTestcases() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Testcase> query = em.createQuery("SELECT t FROM Testcase t ORDER BY t.testcaseId", Testcase.class);
            testcases = query.getResultList();
        } finally {
            em.close();
        }
    }
	 public void loadFilteredTestcases() {
	        Integer currentTestrunId = testrunController.getCurrentTestrun().getTestrunId();
	        EntityManager em = emf.createEntityManager();
	        try {
	            TypedQuery<Testcase> query = em.createQuery(
	                "SELECT t FROM Testcase t WHERE t.testrun_Id = :testrun_Id", Testcase.class);
	            query.setParameter("testrun_Id", currentTestrunId);
	            filteredTestcases = query.getResultList();
	        } finally {
	            em.close();
	        }
	    }

	public boolean checkConnectCondition() {
		return testcaseId != null;
	}

	
	public void loadMyTestcases() {
	    Integer currentUserId = userController.getCurrentUser().getUserId();
	    EntityManager em = emf.createEntityManager();
	    try {
	        TypedQuery<Testcase> query = em.createQuery(
	            "SELECT t FROM Testcase t JOIN FETCH t.requirement r JOIN FETCH t.testrun tr " +
	            "WHERE tr.user_Id = :userId ORDER BY r.requirementId", Testcase.class);
	        query.setParameter("userId", currentUserId);
	        myTestcases = query.getResultList();
	    } finally {
	        em.close();
	    }
	}

	public String getStatusText(Testcase testcase) {
		if ("SUCCESSFUL".equals(testcase.getStatus())) {
			return "Erledigt";
		} else if ("FAILED".equals(testcase.getStatus())) {
			return "Fehlgeschlagen";
		}
		return "";
	}
	
	public void loadAvailabTestcases() {
        Integer currentRequirementId = testrunController.getCurrentTestrun().getRequirement_Id();
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Testcase> query = em.createQuery(
                "SELECT t FROM Testcase t WHERE t.testrun_Id IS NULL AND t.requirement_Id = :reqId", Testcase.class);
            query.setParameter("reqId", currentRequirementId);
            availableTestcases = query.getResultList();
        } finally {
            em.close();
        }
    }
	public String setStatusFailed(Testcase testcase) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Testcase t = em.find(Testcase.class, testcase.getTestcaseId());
            if (t != null) {
                t.setStatus("FAILED");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return "dashboard_testrunOpen?faces-redirect=true";
    }

	public String setStatusSuccessful(Testcase testcase) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Testcase t = em.find(Testcase.class, testcase.getTestcaseId());
            if (t != null) {
                t.setStatus("SUCCESSFUL");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return "dashboard_testrunOpen?faces-redirect=true";
    }
	
	public String connectCasewithRun() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Testcase t = em.find(Testcase.class, testcaseId);
            if (t != null) {
                t.setTestrun_Id(testrunController.getCurrentTestrun().getTestrunId());
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return "dashboard_testrunOpen?faces-redirect=true";
    }

	public String openCurrentTestcase(Testcase testcaseDS) {
		currentTestcase.setTestcaseId(testcaseDS.getTestcaseId());
		return "testcaseOpen?faces-redirect=true";
	}

	// create testcase
	public String create() {
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Testcase newTestcase = new Testcase();
	        newTestcase.setDescription(description);
	        newTestcase.setStatus("false");
	        newTestcase.setRequirement_Id(requirement_Id);
	        em.persist(newTestcase);
	        tx.commit();
	    } catch (Exception e) {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    return "dashboard_Testfall?faces-redirect=true";
	}

	// Getters and Setters
	public Integer getTestcaseId() {
		return testcaseId;
	}

	public void setTestcaseId(Integer testcaseId) {
		this.testcaseId = testcaseId;
	}

	public Integer getTestrun_Id() {
		return testrun_Id;
	}

	public void setTestrun_Id(Integer testrun_Id) {
		this.testrun_Id = testrun_Id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRequirement_Id() {
		return requirement_Id;
	}

	public void setRequirement_Id(Integer requirement_Id) {
		this.requirement_Id = requirement_Id;
	}

	public Testcase getCurrentTestcase() {
		return currentTestcase;
	}

	public void setCurrentTestcase(Testcase testcase) {
		this.currentTestcase = testcase;
	}

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}

	public List<Testcase> getTestcases() {
		loadTestcases();
		return testcases;
	}

	public List<Testcase> getFilteredTestcases() {
		loadFilteredTestcases();
		return filteredTestcases;
	}

	public List<Testcase> getAvailableTestcases() {
		loadAvailabTestcases();
		return availableTestcases;
	}

	public List<Testcase> getMyTestcases() {
		loadMyTestcases();
		return myTestcases;
	}
}
