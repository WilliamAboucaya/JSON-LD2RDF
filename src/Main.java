import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		File directory = new File(args[0]);
		if(!directory.isDirectory()) {
			System.err.println("Please put as an argument the path to a directory containing your files to convert");
		} else {
			for(File file : directory.listFiles()) {
				try (FileReader reader = new FileReader(file)) {
					URL url = new URL("http://rdf-translator.appspot.com/convert/json-ld/xml/content");
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
					
					File outputDir = new File(args[0] + "JSON-LD2RDF_out");
					outputDir.mkdir();
					File outputFile = new File(outputDir, file.getName().split("\\.")[0] + ".xml");
					PrintWriter pw = new PrintWriter(outputFile);
					
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
					    pw.println(inputLine);
					}
					in.close();
					pw.flush();
					pw.close();
					con.disconnect();
					
					System.out.println("Conversion finished");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
