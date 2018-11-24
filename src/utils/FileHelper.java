package utils;

import java.io.File;

public class FileHelper {

	public static void checkFileConfiguration(final File file) throws Exception {
		if (!file.exists()) {
			throw new Exception(file + " does not exists");
		}
		if (!file.canRead()) {
			throw new Exception(file + " is not readable");

		}
	}
}
