
import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI {

	public static void main(String[] args) throws TwitterException {
		
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
			System.out.println(trends.getTrends()[i].getName());
		}
		System.exit(0);
	}catch (TwitterException te) {
		te.printStackTrace();
		System.out.println("Failed to get trends: "+ te.getMessage());
		System.exit(-1);
		}
	}
	
	private static Integer getTrendLocationId (String locationName) {
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
