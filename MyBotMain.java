
import twitter4j.TwitterException;
public class MyBotMain {

	public static void main(String[] args) throws Exception, TwitterException{
		MyBot bot = new MyBot();
		bot.setVerbose(true);
		bot.connect("irc.freenode.net");
		bot.joinChannel("#testingbot");
		}
}
