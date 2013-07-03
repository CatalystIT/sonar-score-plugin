/**
 * 
 */
package com.catalyst.sonar.score.dao;

import java.util.List;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.ResourceModel;

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

	// TODO javadoc
	@Override
	public ScoreProject get(String key) {
		ResourceModel model = getSession().getResults(ResourceModel.class,
				"key", key).get(0);
		return new ScoreProject(model);
	}

	// TODO javadoc
	public ScoreProject getProjectById(Integer id) {
		System.out.println("\t<><><><><><><><>");
		System.out.println("\tIn the Project Dao.");
		if(id==null) {
			return null;
		}
		ScoreProject scoreProject = null;
		
		List<ResourceModel> models = getSession().getResults(
				ResourceModel.class, "id", id);
		if (models == null) {
			System.out.println("\tWARNING!  models is null!!!!");
		} else if (models.size() < 1) {
			System.out.println("\tWARNING!  models.size() = " + models.size());
		} else {
			System.out.println("\tWe are only here because models.size() = " + models.size());
			System.out.println("\tThere are " + models.size() + " Projects:");
			for (ResourceModel model : models) {
				System.out.println("\t" + model.getName() + " ("
						+ model.getKey() + ")");
			}
			ResourceModel model = models.get(0);
			if (model != null) {
				System.out.println("\tWARNING!  model is null!!!!");
				System.out.println("\tHere is the first Project:");
				System.out.println("\t" + model.getName() + " ("
						+ model.getKey() + ")");
				System.out.println("\t\t" + model);
				scoreProject = new ScoreProject(model);
				System.out.println("\tConverted to a ScoreProject:");
				System.out.println("\t" + scoreProject.getName() + " ("
						+ scoreProject.getKey() + ")");
				System.out.println("\t\t" + scoreProject);
			}
		}
		System.out.println("\tLeaving the Project Dao.");
		System.out.println("\t<><><><><><><><>");
		return scoreProject;
	}

	// TODO javadoc
	@Override
	public SearchableHashSet<ScoreProject> getAll() {
		// TODO implement.
		return new SearchableHashSet<ScoreProject>();
	}

	// TODO javadoc
	@Override
	public boolean create(ScoreProject project) {
		// TODO: log some sort of warning that this method should never be used.
		return false;
	}

	// TODO javadoc
	@Override
	public boolean update(ScoreProject entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
