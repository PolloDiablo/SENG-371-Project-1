package jeremy.seng371.part1;


public class WOWRedditPostParser implements IRedditPostParser{
	@Override
	public String getGameName() {
		return("World of Warcraft");
	}

	@Override
	public String getSubredditName() {
		return("wow");
	}
}
