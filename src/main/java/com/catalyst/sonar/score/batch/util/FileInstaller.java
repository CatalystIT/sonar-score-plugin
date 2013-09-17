/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.catalyst.sonar.score.batch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The FileInstaller class is designed to copy a directory from inside the jar
 * of this plugin to a directory outside the plugin. The functionality of the
 * methods herein was first written by jabber in answer to this question on
 * StackOverflow:
 * {@code http://stackoverflow.com/questions/1386809/copy-directory-from-a-jar-file}
 * . The class itself was modified to be an object with two String fields for
 * the source and destination directories.
 * 
 * Unfortunately, the FileInstaller installs files in a directory relative to
 * the CI Engine(Hudson or Jenkins) rather than to the SonarQube Server's
 * directory. If this is not fixable, the logic may have to be put into Ruby
 * rather than Java.
 * 
 * @author JDunn
 * 
 */
public class FileInstaller {
	
	private static final Logger logger = LoggerFactory.getLogger(FileInstaller.class);
	

	private String sourcePath;
	private String destinationPath;

	/**
	 * This constructor sets the source and destination fields.
	 * 
	 * @param source
	 * @param destination
	 */
	public FileInstaller(String source, String destination) {
		this.sourcePath = source;
		this.destinationPath = destination;
	}

	/**
	 * Copies the File toCopy to the File destFile.
	 * 
	 * @param toCopy
	 * @param destFile
	 * @return true if copy is successful, false otherwise.
	 */
	public static boolean copyFile(final File toCopy, final File destFile) {
		logger.debug("copyFile()");
		boolean success = false;
		try {
			success = FileInstaller.copyStream(new FileInputStream(toCopy),
					new FileOutputStream(destFile));
			logger.debug("returning " + success);
			return success;
		} catch (final FileNotFoundException e) {
			logger.debug(e.getStackTrace().toString());
		}
		logger.debug("returning " + success);
		return success;
	}

	/**
	 * Copies files recursively, meaning that if the files are directories,
	 * their contents will also be copied.
	 * 
	 * @param toCopy
	 * @param destDir
	 * @return true if copy is successful, false otherwise.
	 */
	private static boolean copyFilesRecusively(final File toCopy,
			final File destDir) {
		assert destDir.isDirectory();
		logger.debug("copyFilesRecursively()");
		boolean success = true;
		if (!toCopy.isDirectory()) {
			success = FileInstaller.copyFile(toCopy,
					new File(destDir, toCopy.getName()));
			logger.debug("returning " + success);
			return success;
		} else {
			final File newDestDir = new File(destDir, toCopy.getName());
			if (!newDestDir.exists() && !newDestDir.mkdir()) {
				success = false;
				logger.debug("returning " + success);
				return success;
			}
			for (final File child : toCopy.listFiles()) {
				if (!FileInstaller.copyFilesRecusively(child, newDestDir)) {
					success = false;
					logger.debug("returning " + success);
					return success;
				}
			}
		}
		logger.debug("returning " + success);
		return success;
	}

	/**
	 * Copies a directory from a {@link JarURLConnection} to a destination
	 * Directory outside the jar.
	 * 
	 * @param destDir
	 * @param jarConnection
	 * @return true if copy is successful, false otherwise.
	 * @throws IOException
	 */
	public static boolean copyJarResourcesRecursively(final File destDir,
			final JarURLConnection jarConnection) throws IOException {
		logger.debug("copyJarResourcesRecursively()");
		boolean success = true;
		final JarFile jarFile = jarConnection.getJarFile();

		for (final Enumeration<JarEntry> e = jarFile.entries(); e
				.hasMoreElements();) {
			final JarEntry entry = e.nextElement();
			if (entry.getName().startsWith(jarConnection.getEntryName())) {
				final String filename = StringUtils.removeStart(
						entry.getName(), //
						jarConnection.getEntryName());

				final File f = new File(destDir, filename);
				if (!entry.isDirectory()) {
					final InputStream entryInputStream = jarFile
							.getInputStream(entry);
					if (!FileInstaller.copyStream(entryInputStream, f)) {
						success = false;
						logger.debug("returning " + success);
						return success;
					}
					entryInputStream.close();
				} else {
					if (!FileInstaller.ensureDirectoryExists(f)) {
						logger.debug("throwing an IOException");
						throw new IOException("Could not create directory: "
								+ f.getAbsolutePath());
					}
				}
			}
		}
		logger.debug("returning " + success);
		return success;
	}

	/**
	 * This method copies a directory from a URL to a destination File. The URL
	 * is equal to {@code getClass().getResource(getSourcePath())} and the
	 * destination File is equal to {@code new File(getDestinationPath())}.
	 * 
	 * @return true if copy is successful, false otherwise.
	 */
	public boolean copyResourcesRecursively() {
		logger.debug("copyResourcesRecursively()");
		URL originUrl = getClass().getResource(sourcePath);
		File destination = new File(destinationPath);
		boolean success = false;
		try {
			final URLConnection urlConnection = originUrl.openConnection();
			logger.debug("{}", originUrl);
			if (urlConnection instanceof JarURLConnection) {
				success = FileInstaller.copyJarResourcesRecursively(
						destination, (JarURLConnection) urlConnection);
				logger.debug("returning " + success);
				return success;
			} else {
				success = FileInstaller.copyFilesRecusively(
						new File(originUrl.getPath()), destination);
				logger.debug("returning " + success);
				return success;
			}
		} catch (final IOException e) {
			logger.debug(e.getStackTrace().toString());
		}
		logger.debug("returning " + success);
		return success;
	}

	/**
	 * Copies bytes from an InputStream to a File.
	 * 
	 * @param is
	 * @param os
	 * @return true if copy is successful, false otherwise.
	 */
	private static boolean copyStream(final InputStream is, final File f) {
		logger.debug("copyStream(InputStream is, File f)");
		boolean success = false;
		try {
			success = FileInstaller.copyStream(is, new FileOutputStream(f));
			logger.debug("returning " + success);
			return success;
		} catch (final FileNotFoundException e) {
			logger.debug(e.getStackTrace().toString());
		}
		logger.debug("returning " + success);
		return success;
	}

	/**
	 * Copies bytes from an InputStream to an OutputStream.
	 * 
	 * @param is
	 * @param os
	 * @return true if copy is successful, false otherwise.
	 */
	private static boolean copyStream(final InputStream is,
			final OutputStream os) {
		logger.debug("copyStream(InputStream is, OutputStream os)");
		boolean success = false;
		try {
			final byte[] buf = new byte[1024];

			int len = 0;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			os.close();
			success = true;
			logger.debug("returning " + success);
			return success;
		} catch (final IOException e) {
			logger.debug(e.getStackTrace().toString());
		}
		logger.debug("returning " + success);
		return false;
	}

	/**
	 * Checks if a directory exists; if it doesn't, it attempts to create one.
	 * 
	 * @param directory
	 * @return true if the directory exists or was successfully created, false
	 *         otherwise.
	 */
	private static boolean ensureDirectoryExists(final File directory) {
		logger.debug("ensureDirectoryExists)");
		logger.debug("Name = " + directory.getName());
		logger.debug("AbsolutePath = " + directory.getAbsolutePath());
		try {
			logger.debug("CanonicalPath = " + directory.getCanonicalPath());
		} catch (IOException e) {
			logger.debug(e.getStackTrace().toString());
		}
		boolean success = directory.exists() || directory.mkdir();
		logger.debug("returning " + success);
		return success;
	}

	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * @param sourcePath
	 *            the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	/**
	 * @return the destinationPath
	 */
	public String getDestinationPath() {
		return destinationPath;
	}

	/**
	 * @param destinationPath
	 *            the destinationPath to set
	 */
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
}