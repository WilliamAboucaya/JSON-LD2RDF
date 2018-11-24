import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utils.FileHelper;
import utils.ParameterStringBuilder;

public class Main {

	public static void main(String[] args) {
		try {
			if (args.length<3) {
				throw new Exception("This program requires 3 arguments : \n"
						+ "the name of the directory in which the files to convert are\n"
						+ "the format of the source files: rdfa | microdata | xml | n3 | nt | json-ld | detect"
						+ "the format in which the files have to be converted: rdfa | microdata | pretty-xml | xml | n3 | nt | json-ld");
			}
			
			File directory = new File(args[0]);
			if(!directory.isDirectory()) {
				throw new Exception("Please put as an argument the path to a directory containing your files to convert");
			}
			File outputDir = new File(args[0] + "RDFFormatConverter_out");
			outputDir.mkdir();
			
			String source = args[1];
			String target = args[2];
			
			Properties extensions = configReader(new File("extensions.properties"));
			
			for(File file : directory.listFiles()) {
				FileReader reader = new FileReader(file);
				URL url = new URL("http://rdf-translator.appspot.com/convert/" + source + "/" + target + "/content");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setDoOutput(true);

				StringBuilder content = new StringBuilder("");
				int buf = -1;

				while ((buf = reader.read()) != -1) {
					content.append((char) buf);
				}

				Map<String, String> body = new HashMap<>();
				body.put("content", content.toString());

				System.out.println(ParameterStringBuilder.getParamsString(body));

				DataOutputStream out = new DataOutputStream(con.getOutputStream());
				out.writeBytes(ParameterStringBuilder.getParamsString(body));
				out.flush();

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				File outputFile = new File(outputDir, file.getName().split("\\.")[0] + extensions.getProperty(target));
				PrintWriter pw = new PrintWriter(outputFile);

				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					pw.println(inputLine);
				}
				in.close();
				pw.flush();
				pw.close();
				con.disconnect();
				reader.close();
			}
			
			System.out.println("Conversion finished");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Properties configReader(final File propertyFile) throws IOException, Exception {
		Properties configuration = new Properties();
		FileHelper.checkFileConfiguration(propertyFile);
		configuration.load(new FileInputStream(propertyFile));
		return configuration;
	}
}
