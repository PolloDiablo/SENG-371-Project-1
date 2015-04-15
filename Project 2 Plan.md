# SENG-371-Project-2
Jeremy Kroeker and Brayden Arthur

<h2>Goal</h2> 
<b>Creating a tool to help developers analyze community discussions on Reddit.</b>

<h3>Context</h3>
Many popular online games have active communities in online forums (such as Reddit). People user forums as a way to find other gamers to play with, share stories about the game, suggest improvements, and give feedback regarding bugs/issues. Reddit is organized into "subreddits", where each subreddit is the hub of discussion for a certain topic. Generally, each game has its own dedicated subreddit.

The goal of Project 1 was to investigate any correlations between user bug reports in forums and the actual patches released by developers. In this project three games were analyzed: League of Legends (released 2009), Team Fortress 2 (released 2007), and World of Warcraft (released 2004). There seemed to be some correlation as a spike in user activity would often be followed by a patch, and conversely, after a patch was released there would often be a spike in activity as users discuss the changes. The following observations were made in regards to future work:

- Although the data from Project 1 was interesting, it did not provide much utility to developers.
- The Reddit API was incredibly easy to use, in comparison to manually scraping text from other forums. As such, data could easily be obtained for ANY subreddit, not just the three analyzed in Project 1.
- Patch Note data was difficult to obtain. A separate script needs to be written for each patch note repository (a process which is not easily scalable).

These led to the following design decisions for Project 2:
- Create a tool to assist developers. Game developers such as Riot Games (creators of League of Legends) are already active on the forums, regulairly reading and responding to user comments, but we want to streamline this process. 
- Focus just on the data that can be obtained from Reddit since it is easiest to gather and does not require the developer to write any custom scripts. Ideally, all search customization can be done from a UI and the tool can be released as a ready-to-go package.
- The tool should run on any subreddit. This means it is not limited to gaming-related subreddits, so developers on any kind of project could use it.

<h2>Methodology</h2>
This project was entirely programmed in Java. The code for Project 2 can be found in the Eclipse project in the Git repo. THe packge is "awesome/seng371/part2".
Note: the code for Project 1 can be found in the separate package: "jeremy/seng371/part1".

<h3>Main Idea</h3>
Given the context above, we chose the following features for Project 2:
- A subreddit monitoring feature which will periodically pull live data from a given subreddit and send the developer an email notification when a post on the forums reaches a certain threshold of popularity. This allows developers to be automatically informed if a bug/issue is gaining a lot of attention
- A visualization feature that allows the developer to view the trends of a particular keyword over-time in a subreddit. This should give the developer an idea of how much trouble people are having with a particular bug/issue and they can see if their patch releases caused any decrease in complaints.
- A visualization feature that allows the developer to compare the popularity of multiple keywords in a subreddit. The developer can use this to compare multiple items from within their application and see which ones are the most common source of user discussion. For example: in League of Legends there are over 100 "Champions" (playable characters), the developer could enter all of their names and see which Champion is garnering the most user feedback. They could use this data to plan their next development cycle.
- All of the above features are brought together into a single UI (Java Swing)

In addition to creating the tool. We also decided to play around a little more with the existing data from Project 1, just to see what other visualizations we could create and what other information could be extracted from the dataset.

TODO

<h3>How to Run the Code</h3>

TODO

1. Setup a local SQLEXPRESS database as described in https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/database-setup.txt
2. 

<h2>Analysis and Conclusion</h2>
TODO

<h3>Threats to Validity</h3>
TODO

<h3>Future Work</h3>
TODO

<h2>Project Management Information</h2>

<h3>Project Planning</h3>
<h4>Tasks</h4>
<table style="width:100%">
  <tr>
    <th>Task</th>
    <th>Importance[1-10]</th> 
    <th>Effort[1-10]</th>
    <th>Conclusion</th>
  </tr>
  <tr>
    <td>Subreddit Monitoring Tool</td>
    <td>10</td> 
    <td>10</td>
    <td>required</td>
  </tr>
  <tr>
    <td>Auotmated Graph Creation</td>
    <td>10</td> 
    <td>6</td>
    <td>required</td>
  </tr>
  <tr>
    <td>Research Alternate Visualizations</td>
    <td>7</td> 
    <td>2</td>
    <td>required</td>
  </tr>
    <tr>
    <td>Implement Alternate Visualizations</td>
    <td>7</td> 
    <td>6</td>
    <td>required</td>
  </tr>
  <tr>
    <td>Research Alternate Sources of Patch Data</td>
    <td>6</td> 
    <td>7</td>
    <td>optional</td>
  </tr>
  <tr>
    <td>Apply Methodology to Non-game Projects</td>
    <td>8</td> 
    <td>5</td>
    <td>required</td>
  </tr>
  <tr>
    <td>Tool/UI to Parse Reddit Data</td>
    <td>7</td> 
    <td>10</td>
    <td>required</td>
  </tr>
  <tr>
    <td>Final Report/Video</td>
    <td>10</td> 
    <td>10</td>
    <td>required</td>
  </tr>
</table>

<h4>Friday March 13</h4>
  - <b>Brayden</b>: <b>Subreddit Monitoring Tool</b> which will detect spikes in activity and notify developers
  - <b>Jeremy</b>: <b>Auotmated Graph Ceation</b> (for keyword graphs) using JFree Chart library. Also some refactoring (renaming, organization, etc. of project files in Eclipse project).

<h4>Tuesday March 17</h4>
  - <b>Brayden</b>: Begin work on creation of a GUI to make the tool easy to use and more robust
  - <b>Jeremy</b>: <b>Research Alternate Visualization</b> types which can be obtained from existing data. Create a few sample charts to demonstrate that the data analysis method is valid.
    - Compare bug mention activity between keywords of the same category (e.g. characters, maps, or items). 
    This list could be complete (e.g. all the maps), or a subset of the keywords in the category (e.g. a few of the maps).
      - Looking at patch notes and forum posts separately => <i>wordcloud</i>, <i>piechart</i>, or <i>barchart</i>
      - Comparing patch notes and forum posts => <i>stacked column chart</i>
    - Could normalize the above data, to get the proportion of activity for each keyword
      - Difference between patch notes and forum posts => <i>column chart</i> (which items get the most/least relative attention)
      - e.g. http://www2.le.ac.uk/offices/ld/images/study-guide-images/numeracy/bar2.gif
    - Note: Must consider what time-span to look at. Shouldn't look at keywords before they exist in the game.
    (Their proportion would be relatively low).
    
<h4>Friday March 20</h4>
  - <b>Brayden</b>: <b>Creation of GUI</b> for both monitoring tool and graph creator
  - <b>Jeremy</b>: <b>Implement Alternate Visualization</b> types (with a tool, ideally automated)
    - User could enter a list of terms (this is would be time-consuming, any better way to do this?)
    - Tool could automatically detect # of terms
      - If only 1 search term, do a graph of that term over time
      - If multiple search terms, autogenerate some of the alternative graphs
    - In absence of Patch Note data, what graphs can be generated from just Reddit data?
 
<h4>Tuesday March 24</h4>
  - <b>Jeremy</b>: <b>Apply Methodology to Non-game Projects</b>
    - Investigation of non-gaming projects using current toolset

<h4>Friday March 27</h4>
  - <b>Brayden and Jeremy</b>: Draft of project <b>Final Report/Video</b>

<h4>Monday March 30</h4>
  - <b>Brayden and Jeremy</b>: Final version of <b>Final Report/Video</b>
  
<h4>If we have extra time...</h4>
- <b>Research Alternate Sources of Patch Data</b>
  - The problem with our current data source is that a script must be created for each project to parse webpages and pull down the patch data 
  - Want want to find sources with a consistent interface/API that could be queried
  - e.g. internal bug tracking tools, Jira, GitHub issues, etc.
- <b>Tool/UI to Parse Reddit Data</b>
  - user inputs subreddit name, game name (not really necessary, could just use subreddit name), start date, end date, and keywords to look for (defaults are "bug", "issue", "and crash")
  - does the user also specify the database they would like to connect to?

<h3>Roles of Team Members</h3>
- Jeremy Kroeker: Data Wrangler
- Brayden Arthur: UI/tool Creator

<h3>Resources</h3>
- TODO
- Also, See Project 1 README for additional sources
