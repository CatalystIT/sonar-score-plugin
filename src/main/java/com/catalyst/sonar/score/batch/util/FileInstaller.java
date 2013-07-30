package com.catalyst.sonar.score.batch.util;

import static com.catalyst.sonar.score.log.Logger.LOG;
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

/**
 * The FileInstaller class is designed to copy a directory from inside the jar
 * of this plugin to a directory outside the plugin. The functionality of the
 * methods herein was first written by jabber in answer to this question on
 * StackOverflow: {@link http
 * ://stackoverflow.com/questions/1386809/copy-directory-from-a-jar-file}. The
 * class itself was modified to be an object with two String fields for the
 * source and destination directories.
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
		LOG.beginMethod("copyFile()");
		boolean success = false;
		try {
			success = FileInstaller.copyStream(new FileInputStream(toCopy),
					new FileOutputStream(destFile));
			LOG.log("returning " + success).endMethod();
			return success;
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		LOG.log("returning " + success).endMethod();
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
		LOG.beginMethod("copyFilesRecursively()");
		boolean success = true;
		if (!toCopy.isDirectory()) {
			success = FileInstaller.copyFile(toCopy,
					new File(destDir, toCopy.getName()));
			LOG.log("returning " + success).endMethod();
			return success;
		} else {
			final File newDestDir = new File(destDir, toCopy.getName());
			if (!newDestDir.exists() && !newDestDir.mkdir()) {
				success = false;
				LOG.log("returning " + success).endMethod();
				return success;
			}
			for (final File child : toCopy.listFiles()) {
				if (!FileInstaller.copyFilesRecusively(child, newDestDir)) {
					success = false;
					LOG.log("returning " + success).endMethod();
					return success;
				}
			}
		}
		LOG.log("returning " + success).endMethod();
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
		LOG.beginMethod("copyJarResourcesRecursively()");
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
						LOG.log("returning " + success).endMethod();
						return success;
					}
					entryInputStream.close();
				} else {
					if (!FileInstaller.ensureDirectoryExists(f)) {
						// LOG.log("throwing an IOException").endMethod();
						// throw new IOException("Could not create directory: "
						// + f.getAbsolutePath());
						LOG.logEmf("Cannot ensure that Directory Exists!");
					}
				}
			}
		}
		LOG.log("returning " + success).endMethod();
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
		LOG.beginMethod("copyResourcesRecursively()");
		URL originUrl = getClass().getResource(sourcePath);
		File destination = new File(destinationPath);
		boolean success = false;
		try {
			final URLConnection urlConnection = originUrl.openConnection();
			LOG.log(originUrl);
			if (urlConnection instanceof JarURLConnection) {
				success = FileInstaller.copyJarResourcesRecursively(
						destination, (JarURLConnection) urlConnection);
				LOG.log("returning " + success).endMethod();
				return success;
			} else {
				success = FileInstaller.copyFilesRecusively(
						new File(originUrl.getPath()), destination);
				LOG.log("returning " + success).endMethod();
				return success;
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		LOG.log("returning " + success).endMethod();
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
		LOG.beginMethod("copyStream(InputStream is, File f)");
		boolean success = false;
		try {
			success = FileInstaller.copyStream(is, new FileOutputStream(f));
			LOG.log("returning " + success).endMethod();
			return success;
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		LOG.log("returning " + success).endMethod();
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
		LOG.beginMethod("copyStream(InputStream is, OutputStream os)");
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
			LOG.log("returning " + success).endMethod();
			return success;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		LOG.log("returning " + success).endMethod();
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
		LOG.beginMethod("ensureDirectoryExists)");
		LOG.log("Name = " + directory.getName());
		LOG.log("AbsolutePath = " + directory.getAbsolutePath());
		try {
			LOG.log("CanonicalPath = " + directory.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean success = directory.exists() || directory.mkdir();
		LOG.log("returning " + success).endMethod();
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