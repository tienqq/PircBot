import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

	public class WeatherAPI {

		public static void main(String[] args) {
			
			startWebRequest("Richardson");
		}
		
		static String startWebRequest(String city){
			String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=%22+city+%22&APPID=c8e872dbb4587a596093ab6f3157075c";
			StringBuilder result = new StringBuilder(); 
			try {	
				URL url = new URL(weatherURL); 
				HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
				conn.setRequestMethod("GET"); 
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
				String line;
			    	while ((line = rd.readLine()) != null) { 
			    		result.append(line);
			    	}
			    rd.close();
			    //what do we want to search the API for?
			    double mainTemp = parseJson(result.toString());
			    DecimalFormat f = new DecimalFormat("##.00");
			    System.out.printf("It is currently: " + f.format(mainTemp) + "\u00b0F");
			    return result.toString();
			}
			catch(Exception e){return "Error! Exception: " + e;}
		}
		
		static double parseJson(String json) {
	        JsonElement jelement = new JsonParser().parse(json);
	        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
	        JsonObject tempObject = MasterWeatherObject.getAsJsonObject("main"); 
	        double Ktemp = tempObject.get("temp").getAsDouble();  
	        double temp =  Ktemp*9/5 - 459.67;
		return temp;
		}
	}