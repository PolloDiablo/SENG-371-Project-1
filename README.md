# SENG-371-Project-1
<h2>Goal</h2> 
<b>Investigating the effects of user feedback on video game updates and patches.</b>

<h3>Context</h3>
Popular online games such as League of Legends (released 2009), Team Fortress 2 (released 2007), or World of Warcraft (released 2004) all have one thing in common: the developers consistently release patches to improve the game. These improvments can come in the form of bugfixes, minor balance tweaks, character reworks, performance improvements, and even entirely new features and game modes.
Players of these games often like to discuss with one another through online forums. Some developers have hosted their own forums for players to congregate, but players may also use 3rd-party websites (such as Reddit) to talk with one another.
These games each have an official avenue for reporting bugs/issues. However, players will often use forums instead. This allows them to determine if anyone else had the same issue, and to work together to reproduce bugs, find workarounds, and possibly find exploits. Discussing bugs in an open forum provides a valuable resource for developers, as they can acquire data as well as interact with the gaming community.
I am trying to investigate how seriously developers really take this data, and if you can see the issues brought-up in forum posts later addressed in official patch notes.

<h2>Project Question</h2>
- Do developers respond to user feedback submitted through online forums
- If so, how quickly?
- Expansion: Do developers read comments and listen to their community?
- Expansion: Does developer response to forums affect the popularity and life-span of a project?
- Expansion: Which social platforms are most common for developers to read? Reddit, Twitter, Facebook, dedicated forums?

<h3>Predictions</h3>
- If there is NOT correlation between forum comments and patch notes, this could possibly indicate a few things:
  - The developers are not paying attention to user feedback
  - The developers ARE paying attention to user feedback, but users are not finding actual "bugs", so no fixes are required. This could indicate a disconnect between the priorities of the developers and the users
  - The method used to parse/analyze data is ineffective (not looking at the right data points)
  
<h2>Methodology</h2>
Parts 1 and 2 are all done in Java (see Eclipse project in Git repo).
TODO

1. Gather patch note data:
  - Get the following for each patch: url, date/time, title, body (most important)
  - See: https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/pseudocode-patchnotes.txt for more details details
  - I was able to get parse through ALL the patch note data from my data sources
2. Forum/comment parsing:
  - I only had time to look at Reddit forums
  - I only looked back to the start of 2013, so I have data for approximately 2 years
  - I ran one query per week, and grabbed the top 100 posts for that week
  - I threw out any posts with score <1 (which means they were downvoted)
  - Example search:  "http://www.reddit.com/r/leagueoflegends/search?q=(and+timestamp:1421698435..1421784835+title:'bug||issue')&sort=top&restrict_sr=on&syntax=cloudsearch&limit=100"
  - See: https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/pseudocode-forumposts.txt for more details
3. Data analytics:
  - TODO
  - Text analytics: http://provalisresearch.com/products/qualitative-data-analysis-software/freeware/
  - Can generate various graphs, looks for trends in the data

<h3>Data Sources</h3>
- Patch Notes (League of Legends): http://leagueoflegends.wikia.com/wiki/Patch
- Patch Notes (World of Warcraft): http://www.wowwiki.com/Patches
- Patch Notes (Team Fortress 2): https://wiki.teamfortress.com/wiki/Patches
- Reddit (League of Legends): http://www.reddit.com/r/leagueoflegends
- Reddit (World of Warcraft): http://www.reddit.com/r/wow/
- Reddit (Team Fortress 2): http://www.reddit.com/r/tf2/

(See https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/data.txt for database format)


<h2>Results of Experiment</h2>
TODO

<h2>Analysis</h2>
TODO - answers to questions

<h3>Threats to Validity</h3>
- Developers may be getting their information from sources other than the forums. However, the forums might be providing a reflection of the other data source (because users will discuss the same thing in multiple places). Essentially: correlation =/= causation
- The keywords used (bug, issue, and crash) will only capture a subset of user feedback. Users will discuss lots of other topics such as "game balance" which would not be represented in the data I acquired
- Keyword searches do not take meaning into account (sarcasm, etc.)
- The "score" of a forum post does not necessarily represent its validity/usefulness to developers, only its popularity

<h3>Future Work</h3>
- Look at a variety of other forums and social media which players use to discuss video games and provide feedback to developers
  - League of Legends Forums: http://boards.na.leagueoflegends.com/
  - TF2 Forums? (TF2 forums don't have a good search feature, bugs are reported in-game and sent directly to the developers)
  - Other social media? Twitter? Facebook? etc.
- Find games which have an open developer issue tracker. This would give a better idea of how developers respond to legitimate user feedback and the progression that an issue goes through from initial report to patch/fix release
- Parse data from Reddit (or other forums) that is older than 2013. This could allow us to see the evolution of a forum over time, as more game users start to gather there. Additionally, one could compare each forum for a given game and see usage trends as users migrate from one to another

<h2>Project Management Information</h2>
TODO

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

<h3>Resources</h3>
- How to format Reddit searches: https://github.com/reddit/reddit/blob/master/r2/r2/lib/cloudsearch.py#L172
- Converting dates/times: http://www.epochconverter.com/
- HTTP requests in Java: http://hc.apache.org/
- Parsing HTML in Java: http://jsoup.org/
- Microsoft SQL Server Express 2014: http://www.microsoft.com/en-ca/server-cloud/products/sql-server-editions/sql-server-express.aspx
- Connecting with SQLEXPRESS from Java: http://www.microsoft.com/en-ca/download/details.aspx?id=2505
- JDBC Java tutorials: http://www.tutorialspoint.com/jdbc/



