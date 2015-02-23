package jeremy.custom.parsers;

import jeremy.seng371.part1.IRedditPostParser;

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
