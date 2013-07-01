/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.ScorePlugin.TITLECUP;

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
		if (project != null) {
			getTitleCupProperty(cup.getName()).setResourceId(project.getId());
		} else {
			unassign(cup);
		}
		return false;
	}

	private Property getTitleCupProperty(String name) {
		List<Property> properties = getSession().getResults(Property.class,
				"key", TITLECUP + ":" + name);
		return properties.get(0);
	}

	/**
	 * Unassigns the {@link TitleCup} from all Projects.
	 */
	private void unassign(TitleCup cup) {
		// TODO implement
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
	 * Gets the Property containing the information about who currently holds
	 * the TitleCup with the given name.
	 */
	public Property getCurrentlyHeld(TitleCup cup) {
		// TODO implement
		return null;
	}

	/**
	 * @see {@link AwardDao#getAllAwards()}
	 */
	@Override
	public AwardSet<TitleCup> getAll() {
		List<Property> properties = getSession().getResults(Property.class,
				"key", TITLECUP);
		AwardSet<TitleCup> titleCups = new AwardSet<TitleCup>();
		for(Property property : properties) {
			TitleCupParser parser = new TitleCupParser(getSession(), property.getValue());			
			titleCups.add(parser.parse());
		}
		return new AwardSet<TitleCup>();
	}

	/**
	 * @see {@link AwardDao#create(Award)}
	 */
	@Override
	public boolean create(TitleCup cup) {
		// TODO implement
		return false;
	}

	/**
	 * @see {@link AwardDao#update(Award)}
	 */
	@Override
	public boolean update(TitleCup cup) {
		// TODO implement
		return false;
	}

}
