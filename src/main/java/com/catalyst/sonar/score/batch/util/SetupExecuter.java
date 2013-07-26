/**
 * 
 */
package com.catalyst.sonar.score.batch.util;

import static com.catalyst.sonar.score.log.Logger.LOG;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

/**
 * @author JDunn
 * 
 */
public class SetupExecuter {
	
	private static final int TUPAL = 2;
	private static final int FILES = 0;
	private static final int FOLDERS = 1;

	/**
	 * 
	 */
	public SetupExecuter() {

	}

	public boolean execute() {
		LOG.beginMethod("Executing Setup");
		boolean success = true;
		File srcDir;
		File destDir;
		URL inputUrl = getClass().getResource("src/main/resources/images");
		LOG.log("Input Url set to:").log(inputUrl);
		destDir = new File("images");
		srcDir = FileUtils.toFile(inputUrl);
		success = copyDir(srcDir, destDir);
		LOG.log("returning " + success).endMethod();
		return success;
	}
	
	private boolean copyDir(File srcDir, File destDir) {
		LOG.beginMethod("Copying Directory");
		boolean success = true;
		int[] srcCount = checkDir(srcDir, "(Source)");
		checkDir(destDir, "(Destination)");
		try {
			FileUtils.copyDirectory(srcDir, destDir);
			int[] destCount = checkDir(destDir, "(Destination after copy)");
			success = Arrays.equals(srcCount, destCount);
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		LOG.log("returning " + success).endMethod();
		return success;
	}
	
	private int[] checkDir(File dir, String... context) {
		String arg = (context.length > 0) ? context[0] : "";
		int[] contents = new int[TUPAL];
		if (!dir.exists()) {
			contents[FILES] = 0; contents[FOLDERS] = 0;
		} else {
			LOG.log("Found " + arg + ' ' + dir.getName() + " = " + dir.exists());
			LOG.log("Confirm " + arg + ' ' + dir.getName() + " is directory = "
					+ dir.isDirectory());
			LOG.log("Current Contents of " + dir.getName() + ':');
			contents = listAndCountDir(dir);			
		}
		LOG.log("There are " + contents[FILES] + " files and " + contents[FOLDERS] + " folders.");
		return contents;
	}
	
	private int[] listAndCountDir(File dir) {
		int[] count = new int[TUPAL];
		LOG.addTab(1);
		for (File file: dir.listFiles()) {
			LOG.log(file.getName());
			if(file.isDirectory()) {
				count[FOLDERS]++;
				int[] add = listAndCountDir(file);
				count[FILES] += add[FILES];
				count[FOLDERS] += add[FOLDERS];
			} else {
				count[FILES]++;
			}
		}
		LOG.removeTab(1);
		return count;
	}
}
