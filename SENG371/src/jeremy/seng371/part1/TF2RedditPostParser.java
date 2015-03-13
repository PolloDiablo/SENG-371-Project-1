package jeremy.seng371.part1;


public class TF2RedditPostParser implements IRedditPostParser {
	@Override
	public String getGameName() {
		return("Team Fortress 2");
	}

	@Override
	public String getSubredditName() {
		return("tf2");
	}
}
