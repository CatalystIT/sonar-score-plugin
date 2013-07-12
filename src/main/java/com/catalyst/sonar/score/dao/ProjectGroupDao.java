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
import com.catalyst.sonar.score.api.ScoreProject;

/**
 * @author JDunn
 *
 */
public class ProjectGroupDao extends BaseDao {
	
	private ScoreProjectDao projectDao;

	/**
	 * @param session
	 */
	public ProjectGroupDao(DatabaseSession session) {
		super(session);
		this.projectDao = new ScoreProjectDao(session);
	}
	
	//TODO javadoc
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
	
	//TODO javadoc
	public ProjectGroup get(String name) {
		List<Property> properties = getSession().getResults(Property.class, "key", PROJECT_GROUP, "value", name);
		//TODO implement
		return null;
	}

}
