package com.catalyst.sonar.score.batch.trophies;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import com.catalyst.sonar.score.ScorePlugin;
import com.catalyst.sonar.score.metrics.MeasuresHelper;
import com.catalyst.sonar.score.util.SnapshotHistory;
import com.catalyst.sonar.score.util.TrophiesHelper;
/**
 * 
 * @author mwomack
 *
 */
public class AwardTrophies {

	private DatabaseSession session;
	private Project project;
	private Settings settings;
	private MeasuresHelper measuresHelper;
	private TrophiesHelper trophiesHelper;
	private Property newProperty;
	int numberOfListsOfCriteriaPerTrophy;
	
	private String metric1 = "Lines of code"; //non-commenting lines of code (int value in the db, direction -1)
	private double req1 = 500.00;
	private int days1 = 10;

	private String metric2 = "Points";
	private double req2 = 80.0;
	private int days2 = 10;

	private String metric3 = "Coverage";
	private double req3 = 90.0;
	private int days3 = 10;
	
	private int days4 = 30;
	private double req4 = 50.0;
	
	private String metric5 = "Major violations";
	private double req5 = 5;
	private int days5 = 30;
	
	private String metric6 = "Minor violations";
	private double req6 = 30;
	private int days6 = 30;
	
	private String metric7 = "Complexity /class";
	private double req7 =15.0;
	private int days7 = 30;

	// creating new criteria objects
	private Criteria criteria1 = new Criteria(metric1, req1, days1);
	private Criteria criteria2 = new Criteria(metric2, req2, days2);
	private Criteria criteria3 = new Criteria(metric3, req3, days3);
	private Criteria criteria4 = new Criteria(metric3, req4, days4);
	private Criteria criteria5 = new Criteria(metric5, req5, days5);
	private Criteria criteria6 = new Criteria(metric6, req6, days6);
	private Criteria criteria7 = new Criteria(metric7, req7, days7);
	
	// creating new trophies...and setting their names
	private Trophy trophy1 = new Trophy("Trophy1");
	private Trophy trophy2 = new Trophy("Trophy2");
	private Trophy trophy3 = new Trophy("Trophy3");
	private Trophy trophy4 = new Trophy("Trophy4");
	private Trophy trophy5 = new Trophy("Trophy5");
	private Trophy trophy6 = new Trophy("Trophy6");
	private Trophy trophy7 = new Trophy("Trophy7");

	// creating new trophy set objects
	private TrophySet trophySet1 = new TrophySet();
	private TrophySet trophySet2 = new TrophySet();
	private TrophySet trophySet3 = new TrophySet();
	private TrophySet trophySet4 = new TrophySet();
	private TrophySet trophySet5 = new TrophySet();
	private TrophySet trophySet6 = new TrophySet();
	private TrophySet trophySet7 = new TrophySet();

	/**
	 * AwardTrophies constructor
	 * @param session
	 * @param project
	 * @param settings
	 */
	public AwardTrophies (DatabaseSession session, Project project, Settings settings){
	this.session = session;
	this.project = project;
	this.settings = settings;
		
	// adding the trophies to the trophySet objects
	trophySet1.add(trophy1);
	trophySet2.add(trophy2);
	trophySet3.add(trophy3);
	trophySet4.add(trophy4);
	trophySet5.add(trophy5);
	trophySet6.add(trophy6);
	trophySet6.add(trophy7);

	// adds criteria to the criteria list
	trophy1.addCriteria(criteria1);
	trophy2.addCriteria(criteria2);
	trophy3.addCriteria(criteria3);
	
	
	//trophy four has three lists of criteria
	trophy4.addCriteria(criteria1);
	trophy4.addCriteria(criteria2);
	trophy4.addCriteria(criteria4);
	
	trophy5.addCriteria(criteria5);
	trophy6.addCriteria(criteria6);
	trophy7.addCriteria(criteria7);
	
	}	
	
	/**
	 * 
	 * @returns a list of all the trophies
	 */
	public List<Trophy> listTrophies() {
		List<Trophy> allTrophies = new ArrayList<Trophy>();
		allTrophies.add(trophy1);
		allTrophies.add(trophy2);
		allTrophies.add(trophy3);
		allTrophies.add(trophy4);
		allTrophies.add(trophy5);
		allTrophies.add(trophy6);
		allTrophies.add(trophy7);

		return allTrophies;

	}
	
	/**
	 * This method is called when a build is scheduled for a project. It
	 * iterates through list of trophies and their corresponding criteria and
	 * calls the awardTrophy method if any trophies have been earned.
	 * 
	 * @param context
	 * @param resource
	 */
	public void awardTrophies(final DecoratorContext context, Resource resource) {
		List<SnapshotHistory> snapshotHistory = new ArrayList<SnapshotHistory>();
		measuresHelper = new MeasuresHelper(session, project);
		trophiesHelper = new TrophiesHelper(settings);
						
		double requirementAmount = 0.0;
		int days = 0;
				
		/*
		 * if the trophy property does not exist for the given project (resource),
		 * the set up trophy method will save the property name and resource id
		 * to the database.  
		 */
		setUpTrophyProperty(context, resource);			
		
		/*
		 * call the list trophy method to get all the trophies and then iterate
		 * through each trophy's criteria to see if a trophy was earned.
		 */
		for (Trophy items : listTrophies()) {
			List<Criteria> listOfCriteria = items.getCriteria();
			//represents the number of lists of criteria per trophy
			numberOfListsOfCriteriaPerTrophy = listOfCriteria.size();
			int numberOfCriteriaListsMet = 0;
			for (Criteria criteria : listOfCriteria) {
				/*
				 * For each trophy, retrieve the trophy name and its criteria:
				 * metric, required amount and days
				 */
				String trophyName = items.getTrophyName();				
				String metricName = criteria.getMetric();
				requirementAmount = criteria.getRequiredAmt();
				days = criteria.getDays();
				
				/*
				 * Retrieve the project's measure values for the given metric				 
				 */

				snapshotHistory = measuresHelper.getMeasureCollection(metricName);	
				

				/*
				 * Compare the snapshot history (build dates and measure values)
				 * against the trophy criteria. A trophy will be awarded only if all the criteria
				 * have been met.  For example, if the number of lists of criteria for a trophy is two,
				 * then in order to earn that trophy, the 'numberOfCriteriaListsMet' must be equal to
				 * the 'numberOfListsOfCriteriaPerTrophy'.
				 */
								
				if ((trophiesHelper.criteriaMet(snapshotHistory, requirementAmount, days, metricName, session)) && (!snapshotHistory.isEmpty())) {
					numberOfCriteriaListsMet++;
					/*
					 * if the number of criteria lists that have been met are equal to the 
					 * number of criteria lists there are for a given trophy, award the the trophy
					 */
					if (numberOfCriteriaListsMet == numberOfListsOfCriteriaPerTrophy){					
						awardTrophy(trophyName, resource);
					}

				}

			}
		}

	}

	
	/**
	 * This method persists the trophy property and project id to the database if the
	 * property does not exist.  If no trophies are earned, then the text_value field
	 * will be empty
	 * @param context
	 * @param resource
	 */
	private void setUpTrophyProperty(DecoratorContext context, Resource resource) {
		int projectId = resource.getId();
		//create a new trophy property for the given project if it does not already exist
		if (!trophiesHelper.trophyPropertyExists(ScorePlugin.PROJECT_TROPHY)){
			newProperty = new Property(ScorePlugin.PROJECT_TROPHY, "", projectId);
			session.save(newProperty);
		}
		
	}

	
	/**
	 * Persists any awarded trophies for a given project.  Checks to make
	 * sure there are no duplicate trophies
	 * @param trophyName
	 * @param resource
	 */
	public void awardTrophy(String trophyName, Resource resource) {
		/*
		 * if the project property already exists in the database and the
		 * project hasn't earned this particular trophy, add the new trophy
		 * name
		 */
		if (trophiesHelper.newTrophyForThisProject(trophyName)) {
			createAnotherTrophy(trophyName, resource);

			}
	
	}

	/**
	 * This method adds a trophy name to the sonar.score.projectTrophy property
	 * 
	 * @param trophyName
	 * @param resource
	 */
	public void createAnotherTrophy(String trophyName, Resource resource) {
		// retrieve a list of all the properties for the given resource
		List<Property> properties = session.getResults(Property.class,
				"resourceId", resource.getId());
		/*
		 * if the property key is equal to the sonar.score.projectTrophy
		 * property, then add the new trophy name to the text value field in the
		 * database separated by a semi-colon..if the text value field is not empty otherwise 
		 * set the trophy name
		 */
		for (Property items : properties) {
			String propKey = items.getKey();
			String textValue = items.getValue();
			if (propKey.equals(ScorePlugin.PROJECT_TROPHY)) {
				if (!textValue.isEmpty()){
					String newTextValue = textValue + ";" + trophyName;
					items.setValue(newTextValue);
				}else{
					items.setValue(trophyName);
				}
			}
		}

	}

}
