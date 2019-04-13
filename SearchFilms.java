package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchFilms {
	
	  public static final String SEARCH_URL = "http://www.omdbapi.com/?s=TITLE&y>YEAR&apikey=APIKEY";
	  public static final String API_KEY = "bdb2cbf7";
	  /*
	   * TEST PURPOSES
	  public static final String TITLE = "Harry Potter";
	  public static final String YEAR = "2000";
	  */
	  
	  public static String search(String requestUrl) throws IOException, JSONException {
		  StringBuffer response = new StringBuffer();
		  
		  try {
			  URL url = new URL(requestUrl);
			  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			  connection.setRequestMethod("GET");
			  connection.setRequestProperty("Accept", "*/*");
			  connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			  InputStream stream = connection.getInputStream();
			  InputStreamReader reader = new InputStreamReader(stream);
			  BufferedReader buffer = new BufferedReader(reader);
			  String line;
			  while((line = buffer.readLine()) != null) {
				  response.append(line);
			  }
			  buffer.close();
			  connection.disconnect();			  
		  } catch (MalformedURLException e) {
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  return response.toString();
	  }
	  
	  @RequestMapping("/films")
	  public static String searchByTitle(@RequestParam(name = "title") String title, @RequestParam(name = "year", required=false) String year) throws JSONException, IOException {
		  String requestUrl = SEARCH_URL.replaceAll("TITLE", title).replaceAll("APIKEY", API_KEY);
		  
		  return search(requestUrl);
	  }
	  
	  /*@RequestMapping("/film")
	  public static String searchByTitleAndYear(@RequestParam(name = "title") String title, @RequestParam(name = "year") String year) throws JSONException, IOException {
		  String requestUrl = SEARCH_URL.replaceAll("TITLE", title).replaceAll("YEAR", year).replaceAll("APIKEY", API_KEY);
		  
		  return search(requestUrl);
	  }*/
	  
}