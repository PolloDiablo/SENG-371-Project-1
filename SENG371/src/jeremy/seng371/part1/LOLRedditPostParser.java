package jeremy.seng371.part1;


public class LOLRedditPostParser implements IRedditPostParser {
	@Override
	public String getGameName() {
		return("League of Legends");
	}

	@Override
	public String getSubredditName() {
		return("leagueoflegends");
	}
}
