package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Requirement;

@Named
@SessionScoped
public class RequirementController implements Serializable {
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Require4TestingPU");
	
	private Integer requirementId;
	private String title;
	private String description;
	
	private List<Requirement> requirements;

	//Postconstruct to Load Datasets
	@PostConstruct
	public void init() {
		requirements = new ArrayList<>();
		loadRequirements();
	}

	// Getter Setter Controller for Insert
	public int getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Integer requirementId) {
		this.requirementId = requirementId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void loadRequirements() {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Requirement> result = new ArrayList<>();
        try {
            tx.begin();
            Query q = em.createQuery("SELECT r FROM Requirement r ORDER BY r.requirementId DESC", Requirement.class);
            result = q.getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
		requirements = result;
	}

	public boolean checkCreateCondition() {
		// If one of the following cases appear create is disabled
		return (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty());
	}

	//Redirect
	public String createRequirement_redirect() {
		return "requirementCreation?faces-redirect=true";
	}

	//create Requirement
	public String create() {
		EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Requirement req = new Requirement();
	        req.setTitle(title);
	        req.setDescription(description);
	        em.persist(req);
	        tx.commit();
	    } catch (Exception e) {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    loadRequirements();  // Refresh the list of requirements after insertion
	    return "dashboard_Re?faces-redirect=true";
	}
		//delete Requirement
		public String delete(Requirement requirement) {
			EntityManager em = emf.createEntityManager();
		    EntityTransaction tx = em.getTransaction();
		    try {
		    	tx.begin();

		        // Delete TestStep-Datasets connected to Requirement
		    	Query deleteTeststeps = em.createQuery(
		            "DELETE FROM TestStep ts WHERE ts.testcase.testrun.requirement.requirementId = :reqId"
		        );
		        deleteTeststeps.setParameter("reqId", requirement.getRequirementId());
		        deleteTeststeps.executeUpdate();

		        // Delete TestCase-Datasets connected to Requirement
		        Query deleteTestcases = em.createQuery(
		            "DELETE FROM Testcase tc WHERE tc.testrun.requirement.requirementId = :reqId"
		        );
		        deleteTestcases.setParameter("reqId", requirement.getRequirementId());
		        deleteTestcases.executeUpdate();

		        // Delete Testrun-Datasets connected to Requirement
		        Query deleteTestruns = em.createQuery(
		            "DELETE FROM Testrun tr WHERE tr.requirement.requirementId = :reqId"
		        );
		        deleteTestruns.setParameter("reqId", requirement.getRequirementId());
		        deleteTestruns.executeUpdate();

		        // Delete requirement itself
		        if (!em.contains(requirement)) {
		            requirement = em.merge(requirement);
		        }
		        em.remove(requirement);

		        tx.commit();
		    } catch (Exception e) {
		        if (tx.isActive()) {
		            tx.rollback();
		        }
		        e.printStackTrace();
		    } finally {
		        em.close();
		    }
		    loadRequirements();  // Refresh the list of requirements after deletion
		    return "dashboard_Re?faces-redirect=true";
		}
}