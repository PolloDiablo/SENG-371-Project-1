# SENG-371-Project-1
<h2>Goal</h2> 
<b>Investigating the effects of user feedback on video game updates and patches.</b>

<h3>Context</h3>
Popular online games such as League of Legends (released 2009), Team Fortress 2 (released 2007), or World of Warcraft (released 2004) all have one thing in common: the developers consistently release patches to improve the game. These improvments can come in the form of bugfixes, minor balance tweaks, character reworks, performance improvements, and even entirely new features and game modes.
Players of these games often like to discuss with one another through online forums. Some developers have hosted their own forums for players to congregate, but players may also use 3rd-party websites (such as Reddit) to talk with one another.
These games each have an official avenue for reporting bugs/issues. However, players will often use forums instead. This allows them to determine if anyone else had the same issue, work together to reproduce bugs, find workarounds, and possibly find exploits. Discussing bugs in an open forum provides a valuable resource for developers, as they can acquire data as well as interact with the gaming community.

I am trying to investigate how seriously developers really take this data, and if you can see the common bug-related topics in forum posts are later addressed in official patches.

<h2>Project Question</h2>
Do developers respond to user feedback submitted through online forums by making changes to the product?
Expansions:

1. If so, how quickly?
2. Do developers read comments and listen to their community?
3. Does developer response to forums affect the popularity and life-span of a project?
4. Which social platforms are most common for developers to read? Reddit, Twitter, Facebook, dedicated forums?

<h3>Predictions</h3>
If there is NOT correlation between forum comments and patch notes, this could indicate a few things:

  - The developers are not paying attention to user feedback
  - The developers ARE paying attention to user feedback, but users are not finding actual "bugs", so no fixes are required (possibly revealing a disconnect between the priorities of the developers and the users)
  - The method used to parse/analyze data is ineffective (not looking at the right data points)
  
<h2>Methodology</h2>
This project was entirely programmed in Java (see Eclipse project in Git repo).

<h3>Main Idea</h3>
1. Gather patch note data:
  - Get the following for each patch: url, date/time, title, body (most important)
  - See: https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/pseudocode-patchnotes.txt for more details details
  - I was able to get parse through ALL the patch note data from my data sources
2. Forum/comment parsing:
  - I only had time to look at Reddit forums
  - I only looked back to the start of 2013, so I have data for approximately 2 years
  - Run one query per week, and grab the top 100 posts for that week which contain any of the keywords "bug", "issue", or "crash" in the post title
  - Throw out any posts with score <1 (which means they were downvoted)
  - Example search:  "http://www.reddit.com/r/leagueoflegends/search.json?q=(and+timestamp:1421698435..1421784835+title:'bug||issue||crash')&sort=top&restrict_sr=on&syntax=cloudsearch&limit=100"
  - The Reddit API can return search results in JSON, cool!
  - See: https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/pseudocode-forumposts.txt for more details
3. Data analytics:
  - Once the SQL database is populated, it can be accessed in a multitude of ways
  - I decided to query the database using Java (since I already had Java successfully communicating with the database, this was the quickest)
  - Using various queries it is possible to count the occurences of keywords in both the Patch Notes and Reddit Posts and plot these both against time (see Results section below for full details). 
  - For best results, keywords should be game-specific terminology that would not regularily appear in speech. Here are some examples:
    - For <b>League of Legends:</b> characters, skills/abilities/spells, maps, monsters, items, game types, patcher, PVP.Net, runes, masteries, bots, ping/connectino/latency, spectator mode, etc.
    - For <b>Team Fortress 2:</b> characters, maps, cosmetics, weapons, items, game types, source engine, store, crafting, bots, etc.
    - For <b>World of Warcraft:</b> classes, races, skills/spells, dungeons, raids, items/weaprons, pets, professions, talents, reputation, quests, monsters, areas/maps, achievements, PVP, PVE, auction house, guilds, arena, launcher, etc.
    - Note: since these terms are specific to each game, you must have some domain knowledge (of common game terminology) before you can perform any kind of analysis.
  
<h3>Using My Code - Instructions</h3>
1. This Git repo contains a Java Eclipse project titled "SENG371". It should contain all necessary library (jar) files required for the code to run.
2. The code requires access to a SQLEXPRESS database with 2 tables created (as defined in the "data.txt" document). Personally, I setup this database using Microsoft SQL Server 2014 Express. The connection string is a variable titled "databaseURL" in ForumPostMain.java, PatchNoteMain.java, and Analyzer.java.
3. The three files mentioned above are the "brains" of the project. 
  - ForumPostMain.java contains all the code necessary to scrape data from Reddit Posts and store it in the RedditPosts database table. The user (you or me) can implement the IRedditPostParser Interface. I have already created three examplar interfaces (LOLRedditPostParser, TF2RedditPostParser, and WOWRedditPostParser). However, by implementing your own interface, you should be able to scrape data from ANY subreddit.
  - PatchNoteMain.java contains all the code necessary to scrape data from Patch Notes and store it in the PatchNotes database table. The user (you or me) can implement the IPatchNoteParser Interface. I have already created three examplar interfaces (LOLPatchNoteParser, TF2PatchNoteParser, and WOWPatchNoteParser). However, by implementing your own interface, you should be able to scrape data from ANY patch note website.
  - Once you have populated the two database tables, you can use Analyzer.java to create .csv files. These csv files can be opened in Microsoft Excel where you can create graphs. To use Analyzer.java, look for the "Search Settings" near the top of the code. Here you can specify which game you would like to look at (this gameName should correspond to the gameName specified in the previous two interfaces. Then you can choose the search term. Analyzer.java will look for occurences of the search term in the RedditPost and PatchNote data and plot these against time, and output into a .csv file).
  - Note: I realize that the Interfaces and manual graph creation are both a bit clunky, these could both be improved as discussed in the "Future Work" section below.

<h3>Data Sources</h3>
- Patch Notes (League of Legends): http://leagueoflegends.wikia.com/wiki/Patch
- Patch Notes (World of Warcraft): http://www.wowwiki.com/Patches
- Patch Notes (Team Fortress 2): https://wiki.teamfortress.com/wiki/Patches
- Reddit (League of Legends): http://www.reddit.com/r/leagueoflegends
- Reddit (World of Warcraft): http://www.reddit.com/r/wow/
- Reddit (Team Fortress 2): http://www.reddit.com/r/tf2/

In total, the resulting PatchNote database table contains <b>770 rows</b>, where each rows corresponds to a single patch for a game. And the ForumPost database table contains <b>11523 rows</b>, where each row corresponds to a single Reddit post.
(See https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/data.txt for database format)

<h2>Results of Experiment</h2>
For the data analysis I graphed:
```
Weighted occurences of Reddit posts 
  where weight = log10(numberOfComments)+log10(popularity)
AND
Number of patches relased (usually just one at a time)

vs.

Time (weeks)
```
For example, here is the resulting graph for the keyword "Ashe" in the League of Legends data:
![LOL-ashe](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analytics/LOL-ashe.png)

"Mundo" in the League of Legends data:
![LOL-mundo](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analytics/LOL-mundo.png)

"Morgana" in the League of Legends data:
![LOL-morgana](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analytics/LOL-morgana.png)

Note: Ashe, Mundo, and Morgana are all playable characters in League of Legends.

This is for the keyword "soldier" in the Team Fortress 2 data:
![TF2-soldier](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analytics/TF2-soldier.png)

Find the rest of my graphs in:
https://github.com/PolloDiablo/SENG-371-Project-1/tree/master/SENG371/analytics

The graphs are titled "GameAbbreviation-SearchTerm.png"

<h2>Analysis and Conclusion</h2>
<i>Okay, I have pretty graphs, what now?...</i>

First I'd like comment on some prevalent patterns in the data:

1. Patches drive forum activity. It just makes sense. If the developers are making many changes to a feature, its bound to be a talking point for the game community. This is most clearly demonstrated in the difference between the LOL-sion graph and the LOL-morgana graph. Sion undergoes many changes, as a result there is a lot of activity in the forums. Morgana is much more stable and goes many weeks without any mention in the forums.
2. Large spikes in forum activity are usually followed by a patch. This can be seen in the LOL-ashe graph. There are large spikes in February and November, each of which is followed by a patch.
3. Successful patches are followed by a decrease in forum activity, whereas failed patches are followed by an increase. This is exemplified by the TF2-soldier data. You can see that some patches are followed by a spike in activity, whereas other patches are followed by a stretch of zero forum activity.

Because of (2) above, I would say that the answer to my project question is <b>YES</b>. In many of the graphs you can see the pattern where there are some large spikes in forum activity relating to a feature, following by a patch. 
This is especially visible for League of Legends (who's developers have a reputation for high community-involvement), but more difficult to see in the Team Fortress 2 and World of Warcraft data. Perhaps this is because developers are less active in these forums, so the users have less incentive to create posts regarding bugs. Therefore it really becomes a chicken-egg problem: if developers find valuable user feedback in the forums, then they will be more likely to visit frequently. But the community is not going to post valuable bug reports if they do not think that developers will ever read them.

Additionally, it should be noted that both Team Fortress 2 and World of Warcraft implement <b>in-game</b> bug-reporting as the primary way to give user feedback. League of Legends does not have this features, which perhaps encourages users to find other avenues of reaching the developers (such as Reddit).

There are definitely pros/cons to both methods of receiving user feedback (dedicated bug submission forms vs. open user forums). In-game Bug submission forms allow evelopers to format the data in a very particular way which is easy to sort, and prioritize later on. They can also simultaneously gather client system information as the bug is submitted. User forums however offer the ability to tell which bugs are the most common as well as which bugs are the most important to the userbase (based on popularity of posts). Ideally, a developer could gather information from both areas. Use the more "official" ingame bug reports to generate a list of bugs. Then use the forum comments to help prioritize each bug fix.

Well, did I answer my project question? Partly, but not yet. As I will speak to in the following sections, there are many threats to vadility that must be addressed, and there is much opportunity for future work.

<h3>Threats to Validity</h3>
- Not very much reddit forum data was obtained for the TF2 and WOW subreddits compared to the Leeague of Legends data.  The League of Legends Reddit community is highly active, whereas the community for the other two games shows low activity and must primarily congregate on different websites. Small data sets are less useful statistically, and it is more difficult to draw conclusions from the data.
- Developers may be getting their user feedback from sources besides forums. However, the forums might still be providing a reflection of the other data source (because users will discuss the same thing in multiple places). So just because Reddit shows a spike in activity before a patch release, doesn't necessarily mean Reddit was the primary cause. Essentially: correlation =/= causation.
- The keywords used to grab data from Reddit (bug, issue, and crash) will only capture a subset of user feedback. Users will discuss lots of other topics such as "game balance" which would not be represented in the data I acquired
- Keyword searches do not take meaning into account (sarcasm, etc.).
- The "score" of a forum post does not necessarily represent its validity/usefulness to developers, only its popularity
- I just look for the appearance of a term in the patch note data. They could just be mentioning the term, but not actually making any changes. Again: it is difficult to ascertain <i>context/meaning</i> from text analytics.
- Text matches may return false positives if a keyword is used for another purpose, or as a component of a larger word. For example "Ashe" could be part of "Crashes", and "Fizz" could describe a soda drink as well as a League of Legends character. One would have to hope that these false matches would only account for a small proportion of the overall data, so the results should still remain valid.

<h3>Future Work</h3>
- As mentioned in my instructions above, I created a primitive interface which allows users to pull data for ANY game. However, the interface is a little bit clunky, and there are some places where you actually need to go in and update my code (such as the database URL). With some modifications, I could turn this code into an actual Java library which would be much more useful and usable! 
- Similar to above, Analyzer.java (which creates the graphs from database data) currently requires you to manually change the search parameters in the code. But a simple UI could probabably be created which allows the user to just specify the search options. It could also generate the graphs directly as images, rather than writing to .csv files and forcing the user to use Excel to create the graphs manually.
- Look at a variety of other forums and social media which players use to discuss video games and provide feedback to developers
  - League of Legends Forums: http://boards.na.leagueoflegends.com/
  - TF2 Forums? (TF2 forums don't have a good search feature, bugs are reported in-game and sent directly to the developers)
  - Other social media? Twitter? Facebook? etc.
  - Each of these games
- Find games which have an open developer issue tracker. This would give a better idea of how developers respond to legitimate user feedback and the progression that an issue goes through internally from initial report to patch/fix release
- Parse data from Reddit (or other forums) that is older than 2013. This could allow us to see the evolution of a forum over time, as more game users start to gather there (do the developers follow the users?) Additionally, one could compare each forum for a given game and see usage trends as users migrate from one to another.
- Have the tool automatically detect domain-specific words from the Patch Note or Reddit Post text. This would find the game-specific slang that isn't in the common English dictionary. This could be useful for auto-generating graphs out of the database. However, this would be extremely difficult, because it would also need to recognize and ignore: abbreviations, slang, profanity, etc.
- DTW (Dynamic Time Warping) is an algorithm that could be very useful in measuring the response time of developers to user input on forums. It essentially measures the "average offset" between two data sets. See:

![DTW](http://ej.iop.org/images/0957-0233/23/5/055601/Full/mst411675f2_online.jpg)

<h2>Project Management Information</h2>

<h3>Initial Project Milestones</h3>
- (Feb 8) Create database
- (Feb 11) Data mine patch notes and forums/reddit
  - Write Selenium scripts to parse websites and place data into database
  - Obtain occurences of keywords associated with the game (characters, items, maps, etc.), and the word "bug" or "issue"
- (Feb 12) Obtain and gain familiarity with a text analytics tool
  - Make changes to data formats as necessary
- (Feb 15) Compare data from the patch notes to the data from the forums for a particular keyword
  - Expected Outcome: if there is a large burst of forum activity regarding a keyword, there should be a patch soon to address the problem
  - Expected Outcome: after a successful patch, activity should dissipate. An unsuccessful patch will see continued activity
  - Expected Outcome: the time period between the burst of activity and the actual patch/fix should vary based on the project size and release schedule of the development team
- (Feb 22) Report on results/findings

<h3>Roles of Team Members</h3>
- Jeremy Kroeker: all the things 
- Brayden Arthur: Anything else :p

<h3>Resources</h3>
- How to format Reddit searches: https://github.com/reddit/reddit/blob/master/r2/r2/lib/cloudsearch.py#L172
- Converting dates/times: http://www.epochconverter.com/
- HTTP requests in Java: http://hc.apache.org/
- Parsing HTML in Java: http://jsoup.org/
- Microsoft SQL Server Express 2014: http://www.microsoft.com/en-ca/server-cloud/products/sql-server-editions/sql-server-express.aspx
- Connecting with SQLEXPRESS from Java: http://www.microsoft.com/en-ca/download/details.aspx?id=2505
- JDBC Java tutorials: http://www.tutorialspoint.com/jdbc/



