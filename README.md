# SENG-371-Project-1
Investigating the effects of user feedback on video game updates and patches

Project Question:
- Do developers respond to user feedback and bug reports?
- If so, how quickly?

Methodology (+ tools):
- Parse through patch notes:
  - DATA: change events
- Forum/comment parsing:
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

Project milestones:
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

Other notes:
- If there is not correlation between forum comments and patch notes, this could possibly indicate a few things:
  - The developers are not paying attention to user feedback
  - The developers ARE paying attention to user feedback, but users are not finding actual "bugs", so no fixes are required
    - This could indicate a disconnect between the priorities of the developers and the users
  - The method used to parse/analyze data is ineffective (not looking at the right data points)
- The primary question could be refined to focus on the effects of social networking (i.e. Reddit) on development.
  - Do developers read comments and listen to their community
  - Does this affect the popularity and life-span of a project
  - Which platforms are most common? Reddit, Twitter, Facebook, dedicated forums, etc.
