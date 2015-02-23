package jeremy.custom.parsers;

import jeremy.seng371.part1.IRedditPostParser;

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
