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
import model.TestStep;

@Named
@SessionScoped
public class TeststepController implements Serializable {
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Require4TestingPU");
	
	private Integer teststepId;
	private Integer testcase_Id;
	private String testcaseDescription;
	private String title;

	private List<TestStep> testSteps;
	private List<TestStep> filteredTestSteps;

	@Inject
	private TestcaseController testcaseController;

	@Inject
	private TestrunController testrunController;

	@PostConstruct
	public void init() {
		testSteps = new ArrayList<>();
		filteredTestSteps = new ArrayList<>();
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is diasbled
		return (testcase_Id == null || title == null || title.trim().isEmpty());
	}

	public String create() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TestStep newTestStep = new TestStep();
            newTestStep.setTestcase_Id(testcase_Id);
            newTestStep.setTitle(title);
            em.persist(newTestStep);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        // Reload list after insertion
        loadTestSteps();
        return "dashboard_Testfall?faces-redirect=true";
    }

	public void loadTestSteps() {
	    EntityManager em = emf.createEntityManager();
	    try {
	        TypedQuery<TestStep> query = em.createQuery(
	            "SELECT t FROM TestStep t JOIN FETCH t.testcase ORDER BY t.testcase_Id", TestStep.class);
	        testSteps = query.getResultList();
	    } finally {
	        em.close();
	    }
	}

	public void loadfilteredTestSteps() {
	    Integer tcId = null;
	    if (testcaseController.getCurrentTestcase() != null && 
	        testcaseController.getCurrentTestcase().getTestcaseId() != null) {
	        tcId = testcaseController.getCurrentTestcase().getTestcaseId();
	    }

	    if (tcId != null) {
	        EntityManager em = emf.createEntityManager();
	        try {
	            TypedQuery<TestStep> query = em.createQuery(
	                "SELECT t FROM TestStep t JOIN FETCH t.testcase WHERE t.testcase_Id = :tcId ORDER BY t.teststepId",
	                TestStep.class);
	            query.setParameter("tcId", tcId);
	            filteredTestSteps = query.getResultList();
	        } finally {
	            em.close();
	        }
	    } else {
	        filteredTestSteps.clear();
	    }
	}

	// Getters and Setter
	public Integer getTeststepId() {
		return teststepId;
	}

	public void setTeststepId(Integer teststepId) {
		this.teststepId = teststepId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTestcase_Id() {
		return testcase_Id;
	}

	public void setTestcase_Id(Integer testcase_Id) {
		this.testcase_Id = testcase_Id;
	}

	public List<TestStep> getTestSteps() {
		loadTestSteps();
		return testSteps;
	}

	public List<TestStep> getfilteredTestSteps() {
		loadfilteredTestSteps();

		return filteredTestSteps;
	}

	public String getTestcaseDescription() {
		return testcaseDescription;
	}

	public void setTestcaseDescription(String testcaseDescription) {
		this.testcaseDescription = testcaseDescription;
	}

}