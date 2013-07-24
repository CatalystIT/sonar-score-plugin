package com.catalyst.sonar.score.batch.trophies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import com.catalyst.sonar.score.ScorePlugin;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.Trophy;
import com.catalyst.sonar.score.dao.SnapShotDao;
import com.catalyst.sonar.score.util.SnapshotValue;
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
	private SnapShotDao measuresHelper;
	private TrophiesHelper trophiesHelper;
	private Property newProperty;
	int numberOfListsOfCriteriaPerTrophy;
	private TrophyAndCriteriaParser parser;
	private TrophySet trophySet;
	
	
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

	}
	
	/**
	 * 
	 * @returns a list of all the trophies
	 */
	public List<Trophy> listTrophies(Settings settings) {		
		parser = new TrophyAndCriteriaParser();
		String globalValues = parser.getGlobalProperty(settings);
		trophySet = TrophyAndCriteriaParser.parseTrophies(globalValues);
		List<Trophy> allTrophies = new ArrayList<Trophy>(trophySet);				
		return allTrophies;

	}
	
	/**
	 * This method is called when a build is scheduled for a project. It
	 * iterates through the list of trophies and their corresponding criteria and
	 * calls the awardTrophy method if any trophies have been earned.
	 * 
	 * @param context
	 * @param resource
	 */
	@SuppressWarnings("rawtypes")
	public void awardTrophies(final DecoratorContext context, Resource resource) {
		List<SnapshotValue> snapshotHistory = new ArrayList<SnapshotValue>();
		measuresHelper = new SnapShotDao(session, project);
		trophiesHelper = new TrophiesHelper(settings);
						
		double requirementAmount = 0.0;
		int days = 0;
				
		/*
		 * if the trophy property does not exist for the given project (resource), 
		 * the set up trophy method will save the property name and resource id
		 * to the database.  
		 */
		//TODO:  This method most likely can be removed.  Trophy persistence has changed.  More testing
		//needs to be done before removing.
		//setUpTrophyProperty(context, resource);			
		
		/*
		 * call the list trophy method to get all the trophies and then iterate
		 * through each trophy's criteria to see if a trophy was earned.
		 */
		for (Trophy items : listTrophies(settings)) {
			Set<Criterion> listOfCriteria = items.getCriteria();
			//represents the number of lists of criteria per trophy
			numberOfListsOfCriteriaPerTrophy = listOfCriteria.size();
			int numberOfCriteriaListsMet = 0;
			for (Criterion criteria : listOfCriteria) {
				/*
				 * For each trophy, retrieve the trophy name and its criteria:
				 * metric, required amount and days
				 */
				String trophyName = items.getName();				
				String metricName = criteria.getMetric().getName();
				requirementAmount = criteria.getAmount();
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
	private void setUpTrophyProperty(DecoratorContext context, @SuppressWarnings("rawtypes") Resource resource) {
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
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("rawtypes")
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
