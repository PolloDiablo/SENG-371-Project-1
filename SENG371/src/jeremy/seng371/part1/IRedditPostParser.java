package jeremy.seng371.part1;

public interface IRedditPostParser {
	/** Returns name of this game, will be associated with each database row entry*/
	public abstract String getGameName();
	
	/** Returns name of the subreddit*/
	public abstract String getSubredditName();
	
}
