package utils;

import java.io.File;
/**
 * Simple helper allowing to get informations about a particular file.
 * 
 * @author William Aboucaya
 */
public class FileHelper {

	/**
	 * Checks whether a file exists and is readable or not
	 * 
	 * @param file the {@link File} we want to check
	 * @throws Exception if the file doesn't exist or isn't readable
	 */
	public static void checkFileConfiguration(final File file) throws Exception {
		if (!file.exists()) {
			throw new Exception(file + " does not exists");
		}
		if (!file.canRead()) {
			throw new Exception(file + " is not readable");

		}
	}
}
