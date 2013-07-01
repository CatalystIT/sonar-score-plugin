/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.ResourceModel;
import org.sonar.jpa.dao.BaseDao;

import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.SearchableHashSet;

//TODO javadoc
public class ScoreProjectDao extends ScoreEntityDao<ScoreProject> {

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 * 
	 * @see {@link ScoreEntityDao#ScoreEntityDao(DatabaseSession)}
	 */
	public ScoreProjectDao(DatabaseSession session) {
		super(session);
	}
	
	//TODO javadoc
	@Override
	public ScoreProject get(String key) {
		ResourceModel model = getSession()
				.getResults(ResourceModel.class, "key", key).get(0);
		return new ScoreProject(model);
	}
	
	//TODO javadoc
	public ScoreProject getProjectById(int id) {
		ResourceModel model = getSession()
				.getResults(ResourceModel.class, "id", id).get(0);
		return new ScoreProject(model);
	}
	
	//TODO javadoc
	@Override
	public SearchableHashSet<ScoreProject> getAll() {
		//TODO implement.
		return new SearchableHashSet<ScoreProject>();
	}
	
	//TODO javadoc
	@Override
	public boolean create(ScoreProject project) {
		//TODO: log some sort of warning that this method should never be used.
		return false;
	}

	//TODO javadoc
	@Override
	public boolean update(ScoreProject entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
