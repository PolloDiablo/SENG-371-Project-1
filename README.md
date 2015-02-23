# SENG-371-Project-1
<h2>Goal</h2> 
<b>Investigating the effects of user feedback on video game updates and patches.</b>

Context: Popular online games such as League of Legends (released 2009), Team Fortress 2 (released 2007), or World of Warcraft (released 2004) all have one thing in common: the developers consistently release patches to improve the game. These improvments can come in the form of bugfixes, minor balance tweaks, character reworks, performance improvements, and even entirely new features and game modes.
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
  - The developers ARE paying attention to user feedback, but users are not finding actual "bugs", so no fixes are required
    - This could indicate a disconnect between the priorities of the developers and the users
  - The method used to parse/analyze data is ineffective (not looking at the right data points)
  
<h2>Methodology</h2>
1. Gather patch note data:
  - Get the following for each patch: url, date/time, title, body (most important)
2. Forum/comment parsing:
  - Reddit comment search (by time period, title, and content)
    - http://www.reddit.com/r/leagueoflegends/search?q=(and timestamp:1421698435..1421784835 title:'Volibear' text:'bug')&syntax=cloudsearch&restrict_sr=on
    - http://www.epochconverter.com/
    - more info: https://github.com/reddit/reddit/blob/master/r2/r2/lib/cloudsearch.py#L172
    - DATA: # of posts during a given time period, popularity of these posts
  - League of Legends forum search (by time period, title, and content)
    - http://boards.na.leagueoflegends.com/en/search? , &created_from=now-5d&created_to=now-4d
- Text analytics: http://provalisresearch.com/products/qualitative-data-analysis-software/freeware/
    - Can generate various graphs, looks for trends in the data

Codebases/Systems to Analyze:
- League of Legends Patch Notes: http://leagueoflegends.wikia.com/wiki/Patch
- League of Legends Reddit: http://www.reddit.com/r/leagueoflegends
- League of Legends Forums: http://boards.na.leagueoflegends.com/
- Team Fortress 2 Patch Notes: https://wiki.teamfortress.com/wiki/Patches (also includes a list of files changed)
- Team Fortress 2 Reddit: http://www.reddit.com/r/tf2/
- (TF2 forums don't have a good search feature, bugs are reported in-game)
- World of Warcraft Patch Notes: http://www.wowwiki.com/Patches
- World of Warcraft Reddit: http://www.reddit.com/r/wow/
- Other social media? Twitter? Facebook? etc.


<h2>Results of Experiment</h2>

<h2>Analysis</h2>

<h3>Possible flaws in experiment logic</h3>
- Developers may be getting their information from sources other than the forums. However, the forums might be providing a reflection of the other data source (because users will discuss the same thing in multiple places). Essentially: correlation =/= causation


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
