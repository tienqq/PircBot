import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.jibble.pircbot.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
public class MyBot extends PircBot{
	
	public MyBot() {
		this.setName("tienBot");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.equalsIgnoreCase("weather")) { 
			startWebRequest("Richardson", channel);
		}
		if(message.equalsIgnoreCase("time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, "The time is now "+ time);
		}
		if (message.equalsIgnoreCase("trends")||message.equalsIgnoreCase("twitter")) {
			try {
				
				ConfigurationBuilder cf = new ConfigurationBuilder();
				
				cf.setDebugEnabled(true).setOAuthConsumerKey("oQZ7ZknyVf10Ni6JauJa1uL0J")
				.setOAuthConsumerSecret("APBV79oAGAX4tyMM0VE8aCv7F1azQSzixV7q5nDQMIwPYBFisR")
				.setOAuthAccessToken("876609385-HpglGzAzKuxOQsPo2jLgYRSvWNg7TRPaI7V23dS3")
				.setOAuthAccessTokenSecret("1AUNwaGc3WrwTGUBEUS5F4vOkpzZlz5IEki16CGW38owR");

				TwitterFactory tf = new TwitterFactory(cf.build());
				Twitter twitter = tf.getInstance();
				
				ResponseList<Location> locations;
				locations = twitter.getAvailableTrends();
				
				Integer idTrendLocation = getTrendLocationId("united states");
				
				if(idTrendLocation == null) {
					System.out.println("Trend Location Not Found");
					System.exit(0);
				}
				
				Trends trends = twitter.getPlaceTrends(idTrendLocation);
				for (int i = 0; i < 10; i++) {
					sendMessage(channel,trends.getTrends()[i].getName());
				}
			}catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Failed to get trends: "+ te.getMessage());
				}
			}
		}
	
	public String startWebRequest(String city, String channel){
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
		    double temp = parseJson(result.toString());
		    DecimalFormat f = new DecimalFormat("##.00");
		    sendMessage(channel,"It is currently "+f.format(temp)+"\u00b0");
		    return result.toString();
		} 
		catch(Exception e){return "Error! Exception: "+e;}
	}
	
	public double parseJson(String json) {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
        JsonObject tempObject = MasterWeatherObject.getAsJsonObject("main"); 
        double Ktemp = tempObject.get("temp").getAsDouble();  
        double temp =  Ktemp*9/5 - 459.67;
        return temp;
	}
	
	public Integer getTrendLocationId (String locationName) {
		int idTrendLocation = 0;
		
		try {
			ConfigurationBuilder cf = new ConfigurationBuilder();
			
			cf.setDebugEnabled(true).setOAuthConsumerKey("oQZ7ZknyVf10Ni6JauJa1uL0J")
			.setOAuthConsumerSecret("APBV79oAGAX4tyMM0VE8aCv7F1azQSzixV7q5nDQMIwPYBFisR")
			.setOAuthAccessToken("876609385-HpglGzAzKuxOQsPo2jLgYRSvWNg7TRPaI7V23dS3")
			.setOAuthAccessTokenSecret("1AUNwaGc3WrwTGUBEUS5F4vOkpzZlz5IEki16CGW38owR");

			TwitterFactory tf = new TwitterFactory(cf.build());
			Twitter twitter = tf.getInstance();
			
			ResponseList<Location> locations;
			locations = twitter.getAvailableTrends();
			
			for (Location location : locations) {
				if(location.getName().toLowerCase().equals(locationName.toLowerCase())) {
					idTrendLocation = location.getWoeid();
					break;
				}
				}
			
			if (idTrendLocation > 0) {
				return idTrendLocation;
			}
			
			return null;
		
		}catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get Trends: "+ te.getMessage());
			return null;
		}
	}
}
