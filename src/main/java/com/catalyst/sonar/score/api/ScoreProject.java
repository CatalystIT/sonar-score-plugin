/**
 * 
 */
package com.catalyst.sonar.score.api;

import java.lang.reflect.Field;

import org.sonar.api.database.model.ResourceModel;

/**
 * {@link ScoreProject} extends {@link ResourceModel}, adding a description
 * field and implementing {@link Member}.
 * 
 * @author JDunn
 */
public class ScoreProject extends ResourceModel implements Member {

/**
	 * Calls {@code super()} and {@code super.setKey(key).
	 * @see {@link ResourceModel#ResourceModel()}
	 * @see {@link ResourceModel#setKey(String key)}
	 * @param key
	 */
	public ScoreProject(String key) {
		super();
		super.setKey(key);
	}

	/**
	 * Calls {@code super(scope, key, qualifier, rootId, name)}.
	 * 
	 * @see {@link ResourceModel#ResourceModel(String key, String name, String branch)}
	 * @param scope
	 * @param key
	 * @param qualifier
	 * @param rootId
	 * @param name
	 */
	public ScoreProject(String scope, String key, String qualifier,
			Integer rootId, String name) {
		super(scope, key, qualifier, rootId, name);
	}

	/**
	 * Constructs a ScoreProject with all fields set to equal the fields of the
	 * {@link ResourceModel} argument. (Remember, {@link ResourceModel} is the
	 * parent class of {@link ScoreProject}.
	 * 
	 * @param resourceModel
	 *            the {@link ResourceModel} from which to derive the field
	 *            values of the constructed {@link ScoreProject}
	 */
	public ScoreProject(ResourceModel resourceModel) {
		super(resourceModel.getScope(), resourceModel.getKey(), resourceModel
				.getQualifier(), resourceModel.getRootId(), resourceModel
				.getName());
		this.setId(resourceModel.getId());
		@SuppressWarnings("rawtypes")
		Class parentClass = ResourceModel.class;
		while (!parentClass.equals(Object.class)) {
			for (Field field : ResourceModel.class.getDeclaredFields()) {
				try {
					field.setAccessible(true);
					field.set(this, field.get(resourceModel));
				} catch (IllegalAccessException e) {
					// The caught exception should only have been thrown due to
					// static fields.
				}
			}
			parentClass = parentClass.getSuperclass();
		}
	}

	/**
	 * Gets the uniqueId, which for a ScoreProject is the key. In
	 * {@link ScoreProject}, calls super.getKey().
	 * 
	 * @see {@link Member#getUniqueId()}
	 */
	public String getUniqueId() {
		return super.getKey();
	}

}
