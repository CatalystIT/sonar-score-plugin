/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.log.Logger.LOG;

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
		LOG.beginMethod("getProjectById()");
		if (id == null) {
			return null;
		}
		ScoreProject scoreProject = null;

		List<ResourceModel> models = getSession().getResults(
				ResourceModel.class, "id", id);
		if (models == null) {
			LOG.warn("models is null!!!!");
		} else if (models.size() < 1) {
			LOG.warn("models.size() = " + models.size());
		} else {
			LOG.log("We are only here because models.size() = " + models.size())
					.log("\tThere are " + models.size() + " Projects:");
			for (ResourceModel model : models) {
				LOG.log(model.getName() + " (" + model.getKey() + ")");
			}
			ResourceModel model = models.get(0);
			if (model != null) {
				LOG.warn("model is null!!!!").log("Here is the first Project:")
						.log(model.getName() + " (" + model.getKey() + ")")
						.log(model);
				scoreProject = new ScoreProject(model);
				LOG.log("Converted to a ScoreProject:")
						.log(scoreProject.getName() + " ("
								+ scoreProject.getKey() + ")")
						.log(scoreProject);
			}
		}
		LOG.log("returning " + scoreProject).endMethod();
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
