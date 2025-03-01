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
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Test;

@Named
@SessionScoped
public class TestController implements Serializable {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Require4TestingPU");
	
	@Inject
	private UserController userController; // Inject UserController

	private Integer testrunId;
	private Integer testRunNr;
	private Integer testCaseId;
	private Integer user_Id;
	private Integer requirement_Id;

	private List<Test> tests;
	private List<Test> yourTests;

	private Test currentTest = new Test();

	// Postconstruct to Load Datasets
	@PostConstruct
	public void init() {
		tests = new ArrayList<>();
		yourTests = new ArrayList<>();
	}

	public List<Test> getTests() {
		if (tests.isEmpty()) {
			loadTests();
		}
		return tests;
	}

	public List<Test> getYourTests() {
		if (yourTests.isEmpty()) {
			loadYourTests();
		}
		return yourTests;
	}

	public void loadTests() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Test> query = em.createQuery(
                "SELECT t FROM Test t ORDER BY t.testrun_Id DESC", Test.class);
            tests = query.getResultList();
        } finally {
            em.close();
        }
    }

    public void loadYourTests() {
        Integer currentUserId = userController.getCurrentUser().getUserId();
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Test> query = em.createQuery(
                "SELECT t FROM Test t WHERE t.user_Id = :user_Id ORDER BY t.testrun_Id DESC", Test.class);
            query.setParameter("user_Id", currentUserId);
            yourTests = query.getResultList();
        } finally {
            em.close();
        }
    }

	public String openCurrentTest(Test testDS) {
		currentTest.setTestId(testDS.getTestId());
		currentTest.setUser_Id(testDS.getUser_Id());
		currentTest.setTestCase_Id(testDS.getTestCase_Id());

		return "testOpen?faces-redirect=true";
	}

	// Getter Setter
	public Integer getTestrunId() {
		return testrunId;
	}

	public void setTestrunId(Integer testrunId) {
		this.testrunId = testrunId;
	}

	public Integer getRequirement_Id() {
		return requirement_Id;
	}

	public void setRequirement_Id(Integer requirement_Id) {
		this.requirement_Id = requirement_Id;
	}

	public Integer getTestRunNr() {
		return testRunNr;
	}

	public void setTestRunNr(Integer testRunNr) {
		this.testRunNr = testRunNr;
	}

	public Integer getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public Test getCurrentTest() {
		return currentTest;
	}

	public void setCurrentTest(Test test) {
		this.currentTest = test;
	}
}