/**
 * 
 */
package com.catalyst.sonar.score.batch.util;

import com.catalyst.sonar.score.batch.SetupDecorator;
import com.catalyst.sonar.score.dao.PropertyDao;

/**
 * 
 * The SetupExecuter may eventually need to be deprecated or removed. The
 * purpose of the SetupExecuter is to setup certain conditions needed for the
 * Score Plugin to work properly. Currently, this means automatically adding
 * certain images to the SonarQube Server's images directory. Achieving this
 * through Java has proved elusive as the images are installed in a path
 * relative to the directory of the CI Engine (Hudson or Jenkins) rather than
 * Sonar's Directory.
 * 
 * @see {@link SetupDecorator}
 * @see {@link FileInstaller}
 * 
 * @author JDunn
 * 
 */
public class SetupExecuter {

	/**
	 * This constructor is only needed for development purposes, as it is easier
	 * to experiment with different paths for the source and destination of
	 * files to copy if their values are adjusted in the database rather than
	 * hard-coded. If this code was working as intended, the path values would
	 * be hard-coded and this constructor and the {@link PropertyDao} field that
	 * it sets would be removed.
	 */
	public SetupExecuter(PropertyDao dao) {
		this.dao = dao;
	}

	/**
	 * @see {@link SetupExecuter#SetupExecuter(PropertyDao)}
	 */
	private PropertyDao dao;

	/**
	 * Setups certain conditions needed for the Score Plugin to work properly.
	 * Currently, this means automatically adding certain images to the
	 * SonarQube Server's images directory. Achieving this through Java has
	 * proved elusive as the images are installed in a path relative to the
	 * directory of the CI Engine (Hudson or Jenkins) rather than Sonar's
	 * Directory.
	 * 
	 * @see {@link FileInstaller}
	 * 
	 * @return
	 */
	public boolean execute() {
		String src = dao.get("TestSrc").getValue();
		String dest = dao.get("TestDest").getValue();
		return new FileInstaller(src, dest).copyResourcesRecursively();
	}
}
