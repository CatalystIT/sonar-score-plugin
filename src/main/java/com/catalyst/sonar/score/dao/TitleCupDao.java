/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.ScorePlugin.TITLECUP;
import static com.catalyst.sonar.score.log.Logger.LOG;

import java.util.List;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;
import com.catalyst.sonar.score.api.TitleCup;

/**
 * The {link TitleCupDao} class implements methods from {@link AwardDao}{@code <}
 * {@link TitleCup}{@code >} to specifically retrieve and/or manipulate
 * {@link TitleCup} information in the database.
 * 
 * @author JDunn
 * 
 */
public class TitleCupDao extends AwardDao<TitleCup> {

	/**
	 * @param session
	 */
	public TitleCupDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * @see {@link TitleCupDao#assignToProject(Award, ScoreUser)	
	 */
	public boolean assign(TitleCup cup, ScoreProject project) {
		return assignToProject(cup, project);
	}

	/**
	 * @see {@link AwardDao#assignToUser(Award, ScoreUser)	
	 */
	@Override
	protected boolean assignToUser(TitleCup cup, ScoreUser user) {
		getTitleCupProperty(cup.getName()).setUserId(user.getId());
		return false;
	}

	/**
	 * @see {@link AwardDao#assignToProject(Award, ScoreUser)	
	 */
	@Override
	protected boolean assignToProject(TitleCup cup, ScoreProject project) {
		LOG.beginMethod("Assign Title Cup to Project");
		boolean successful;
		LOG.log("Project = " + project);
		if (project != null) {
			Property titleCupProperty = getTitleCupProperty(cup.getName());
			LOG.log("titleCupProperty = " + titleCupProperty);
			int resourceId = project.getId();
			LOG.log("resourceId = " + resourceId);
			titleCupProperty.setResourceId(resourceId);
			LOG.log("titleCupProperty = " + titleCupProperty).log(
					"Assigning " + cup + " to " + project.getName());
			getSession().save(titleCupProperty);
			successful = true;
		} else {
			LOG.log("Unassigning " + cup);
			unassign(cup);
			successful = false;
		}
		LOG.endMethod();
		return successful;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.catalyst.sonar.score.dao.AwardDao#getAssignedFromUser(com.catalyst
	 * .sonar.score.api.Award, com.catalyst.sonar.score.api.ScoreUser)
	 */
	@Override
	protected TitleCup getAssignedFromUser(TitleCup cup, ScoreUser user) {
		List<Property> properties = getSession().getResults(Property.class,
				"userId", user.getId());
		return getCupFromProperties(cup, properties);
	}

	/**
	 * Retrieves the {@link TitleCup} if it has been assigned to the project. If
	 * it has not been assigned, {@code null} is returned.
	 * 
	 * @see {@link AwardDao#getAssignedFromProject(Award, ScoreProject)}
	 */
	@Override
	protected TitleCup getAssignedFromProject(TitleCup cup, ScoreProject project) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", project.getId());
		return getCupFromProperties(cup, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AwardDao#getAllAssignedFromUser(ScoreUser)
	 */
	@Override
	protected AwardSet<TitleCup> getAllAssignedFromUser(ScoreUser user) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", user.getId());
		return getAllCupsFromProperties(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AwardDao#getAllAssignedFromProject(ScoreProject)
	 */
	@Override
	protected AwardSet<TitleCup> getAllAssignedFromProject(ScoreProject project) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", project.getId());
		return getAllCupsFromProperties(properties);
	}

	public Property getTitleCupProperty(String name) {
		LOG.beginMethod("Getting TitleCup Property");
		List<Property> properties = getSession().getResults(Property.class,
				"key", TITLECUP + ":" + name);
		LOG.log("Found " + properties.size() + " Properties:");
		for (Property property : properties) {
			System.out.println(property.getKey() + " , resourceId = "
					+ property.getResourceId());
		}
		Property property = (properties.size() > 0) ? properties.get(0) : null;
		LOG.log("We want the first one:");
		String printIt = (property != null) ? property.getKey()
				+ " , resourceId = " + property.getResourceId() : "null";
		LOG.log(printIt).endMethod();
		return property;
	}

	/**
	 * Unassigns the {@link TitleCup} from all Projects.
	 */
	private void unassign(TitleCup cup) {
		Property property = getTitleCupProperty(cup.getName());
		property.setResourceId(null);
		if (cup != null) {
			getSession().save(property);
		}
	}

	/**
	 * @see {@link AwardDao#get(String)}
	 */
	@Override
	public TitleCup get(String name) {
		// TODO implement
		return null;
	}

	/**
	 * @see {@link AwardDao#getAllAwards()}
	 */
	@Override
	public AwardSet<TitleCup> getAll() {
		LOG.beginMethod("TitleCupDao.getAll()");
		List<Property> properties = getSession().getResults(Property.class,
				"key", TITLECUP);
		AwardSet<TitleCup> titleCups = new AwardSet<TitleCup>();
		Property property = properties.get(0);
		LOG.log(property.getValue());
		for (String cupString : property.getValue().split(",")) {
			LOG.log(cupString);
			TitleCupParser parser = new TitleCupParser(getSession(), cupString);
			LOG.log(parser.parse());
		}
		LOG.log(titleCups).log("There are " + titleCups.size() + " TitleCups")
				.endMethod();
		return titleCups;
	}

	/**
	 * @see {@link AwardDao#create(Award)}
	 */
	@Override
	public boolean create(TitleCup cup) {
		LOG.beginMethod("creating a new cup: " + cup.getName());
		Property property = new Property("sonar.score.TitleCup:"
				+ cup.getName(), null, null);
		getSession().save(property);
		LOG.endMethod();
		return true;
	}

	/**
	 * @see {@link AwardDao#update(Award)}
	 */
	@Override
	public boolean update(TitleCup cup) {
		// TODO implement
		return false;
	}

	private TitleCup getCupFromProperties(TitleCup cup,
			List<Property> properties) {
		retainOnlyTitleCups(properties);
		for (Property property : properties) {
			if (property.getKey().contains(cup.getName())) {
				return get(cup);
			}
		}
		return null;
	}

	private AwardSet<TitleCup> getAllCupsFromProperties(
			List<Property> properties) {
		retainOnlyTitleCups(properties);
		AwardSet<TitleCup> cups = new AwardSet<TitleCup>();
		for (Property property : properties) {
			String cupName = property.getKey().split(":")[1];
			cups.add(get(cupName));
		}
		return cups;
	}

	private List<Property> retainOnlyTitleCups(List<Property> properties) {
		for (int i = 0; i < properties.size(); i++) {
			while (properties.get(i).getKey().contains(TITLECUP)) {
				properties.remove(i);
			}
		}
		return properties;
	}
}
