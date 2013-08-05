/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.ScorePlugin.*;

import java.util.List;

import org.sonar.api.database.configuration.Property;
import org.sonar.api.database.DatabaseSession;
import org.sonar.jpa.dao.BaseDao;

import com.catalyst.sonar.score.api.GroupSet;
import com.catalyst.sonar.score.api.ProjectGroup;
import com.catalyst.sonar.score.api.ReceiverEntity;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * @author JDunn
 *
 */
public class ProjectGroupDao extends AssignableScoreEntityDao<ProjectGroup> {
	
	private ScoreProjectDao projectDao;

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public ProjectGroupDao(DatabaseSession session) {
		super(session);
		this.projectDao = new ScoreProjectDao(session);
	}
	
	/**
	 * @See {@link ScoreEntityDao#getAll()}
	 */
	@Override
	public GroupSet<ProjectGroup> getAll() {
		GroupSet<ProjectGroup> groupSet = new GroupSet<ProjectGroup>();		
		List<Property> groups = getSession().getResults(Property.class, "key", PROJECT_GROUP);
		for(Property prop : groups) {
			ScoreProject project = new ScoreProject(projectDao.getProjectById(prop.getId()));
			ProjectGroup group = new ProjectGroup();
			group.add(project);
			groupSet.add(group);
		}
		
		return groupSet;
	}
	
	/**
	 * @See {@link ScoreEntityDao#get(String)}
	 */
	@Override
	public ProjectGroup get(String name) {
		List<Property> properties = getSession().getResults(Property.class, "key", PROJECT_GROUP, "value", name);
		//TODO Implement
		return null;
	}

	/**
	 * @see {@link AssignableScoreEntityDao#assign(AssignableScoreEntity, ReceiverScoreEntity)}
	 */
	@Override
	public boolean assign(ProjectGroup assignable, ReceiverEntity receiver) {
		// TODO Implement
		return false;
	}

	/**
	 * @see {@link AssignableScoreEntityDao#getAllAssigned(ReceiverScoreEntity)}
	 */
	@Override
	public SearchableHashSet<ProjectGroup> getAllAssigned(
			ReceiverEntity receiver) {
		// TODO Implement
		return null;
	}

	/**
	 * @see {@link AssignableScoreEntityDao#getAssigned(AssignableScoreEntity, ReceiverScoreEntity)}
	 */
	@Override
	public ProjectGroup getAssigned(ProjectGroup assignable,
			ReceiverEntity receiver) {
		// TODO Implement
		return null;
	}

	/**
	 * @see {@link ScoreEntityDao#update(ScoreEntity)}
	 */
	@Override
	public ProjectGroup update(ProjectGroup entity) {
		// TODO Implement
		return null;
	}

	@Override
	public ProjectGroup create(ProjectGroup entity) {
		// TODO Auto-generated method stub
		return null;
	}	
	

}
