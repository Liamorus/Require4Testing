package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Testrun;

@Named
@SessionScoped
public class TestrunController implements Serializable {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Require4TestingPU");
	
	private Integer testrunId;
	private Integer runNr;
	private Integer user_Id;
	private Integer requirement_Id;
	private String requirementTitle;

	private List<Testrun> testruns;

	private Testrun currentTestrun = new Testrun();

	@PostConstruct
	public void init() {
		testruns = new ArrayList<>();
		loadTestruns();

	}

	public void loadTestruns() {
	    EntityManager em = emf.createEntityManager();
	    try {
	        TypedQuery<Testrun> query = em.createQuery(
	            "SELECT t FROM Testrun t LEFT JOIN FETCH t.requirement ORDER BY t.requirement.requirementId ASC, t.runNr ASC", 
	            Testrun.class);
	        testruns = query.getResultList();
	    } finally {
	        em.close();
	    }
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is diasbled
		return (requirement_Id == null || user_Id == null);
	}

	public String create() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Integer> query = em.createQuery(
                "SELECT COALESCE(MAX(t.runNr), 0) + 1 FROM Testrun t WHERE t.requirement_Id = :reqId", Integer.class);
            query.setParameter("reqId", requirement_Id);
            Integer newRunNr = query.getSingleResult();
            
            Testrun newTestrun = new Testrun();
            newTestrun.setRunNr(newRunNr);
            newTestrun.setRequirement_Id(requirement_Id);
            newTestrun.setUser_Id(user_Id);

            em.persist(newTestrun);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return "dashboard_TestManager?faces-redirect=true";
    }

	public String openCurrentTestrun(Testrun testrunDS) {
		currentTestrun.setTestrunId(testrunDS.getTestrunId());
		currentTestrun.setRunNr(testrunDS.getRunNr());
		currentTestrun.setRequirement_Id(testrunDS.getRequirement_Id());

		return "testrunOpen?faces-redirect=true";
	}

	// Getters and Setters
	public Integer getTestrunId() {
		return testrunId;
	}

	public void setTestrunId(Integer testrunId) {
		this.testrunId = testrunId;
	}

	public Integer getRunNr() {
		return runNr;
	}

	public void setRunNr(Integer run) {
		this.runNr = run;
	}

	public Integer getRequirement_Id() {
		return requirement_Id;
	}

	public void setRequirement_Id(Integer requirement_Id) {
		this.requirement_Id = requirement_Id;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}

	public List<Testrun> getTestruns() {
		loadTestruns();
		return testruns;
	}

	public Testrun getCurrentTestrun() {
		return currentTestrun;
	}
}
