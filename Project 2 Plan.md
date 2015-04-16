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
This project was entirely programmed in Java. The code for Project 2 can be found in the Eclipse project in the Git repo. The packge is "awesome/seng371/part2".
Note: the code for Project 1 can be found in the separate package: "jeremy/seng371/part1".

<h3>Main Idea</h3>
Given the context above, we chose the following features for Project 2:
- A subreddit monitoring feature which will periodically pull live data from a given subreddit and send the developer an email notification when a post on the forums reaches a certain threshold of popularity. This allows developers to be automatically informed if a bug/issue is gaining a lot of attention
- A visualization feature that allows the developer to view the trends of a particular keyword over-time in a subreddit. This should give the developer an idea of how much trouble people are having with a particular bug/issue and they can see if their patch releases caused any decrease in complaints.
- A visualization feature that allows the developer to compare the popularity of multiple keywords in a subreddit. The developer can use this to compare multiple items from within their application and see which ones are the most common source of user discussion. For example: in League of Legends there are over 100 "Champions" (playable characters), the developer could enter all of their names and see which Champion is garnering the most user feedback. They could use this data to plan their next development cycle.
- All of the above features are brought together into a single UI (Java Swing)

In addition to creating the tool. We also decided to play around a little more with the existing data from Project 1, just to see what other visualizations we could create and what other information could be extracted from the dataset.

<h3>How to Run the Code</h3>

1. Setup a local SQLEXPRESS database as described in https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/docs/database-setup.txt
2. Download the project and open it in Eclipse
3. Navigate to "src/myGUI.java" and press run

The UI has 4 Panels, this is a brief overview of each:

1. <b>Monitor Subreddit</b> - This feature lets you monitor a subreddit in realtime. It will look for posts which contatin the given keywords and that are above the given threshold. If it finds any posts from the last 24 hours that meet these criteria, it will send out an email. NOTE: this feature does not require the database to be setup, since it just looks at live Reddit data.
2. <b>Graph Data</b> - Assuming you have setup AND populated the database (see #4), you can use this feature to create graphs. There are two primary graph types that you can create:
  - Track the variations in popularity of a single keyword over time 
  - Compare the occurences of multiple keywords over the entire time span
As a time-saving feature, after you successfully create a graph, your current query parameters are stored to a data file. The next time you launch the application, each of the query fields will automatically be populated for you. This means, for example, that you would not need to enter the URL of your database each time you use the tool. Some of this data is also populated into panel 4.
3. <b>Display Graphs</b> - This page provides a link to the folder where the graphs have been generated.
4. <b>Data Scraper</b> - Assuming you have created the database as described in /docs/data.txt , this feature allows you to populate the database with data from Reddit. Given that it has to send a large number of queries to the Reddit API, acquiring this data may take a long time.

<h2>Results of Experiment</h2>
A number of sample charts have been uploaded which demonstrate what can be done through the UI:

Here is the resulting graph for the single keyword "Ahri" during 2014:

![Single-Keyword](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analyticsNEW/ahri-singlekeyword_XYplot.png)

Here is the resulting graph comparing all League of Legends characters created prior to 2014, during 2014:

![Multi-Keyword](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analyticsNEW/RedditPosts-multikeyword_barchart.png)


As mentioned earlier, we also wanted to perform additional analysis on the existing data from Project 1. In the chart below, we normalize the values of Reddit Popularity and Patch Notes, and find the difference for each keyword. Essentially, these tells you which League of Legends characters are getting proportionally more or less attention when compared to the forum activity.

Since this chart requires Patch Note data, we did not make it accessible through the UI. However, the code to create this type of chart resides in "src/GraphCreator_MultiKeyword.java".
![Normalized Comparison](https://github.com/PolloDiablo/SENG-371-Project-1/blob/master/SENG371/analyticsNEW/multikeyword_normalized_difference.png)


<h2>Analysis and Conclusion</h2>
Overall, the tool seemed to be a success. The project was not over-scoped, so we had time to implement all intended functionality. A video demo of the tool is available [here](https://www.youtube.com/watch?v=OGSlmd3pc8U).

<h3>Threats to Validity</h3>
This project focused largely on creating the tool, rather than analyzing data. The validity of any graphs created by this tool will vary largely based on what data the user is studying and how they setup each graph. However, in all cases, users will be looking at Reddit data, so they must be aware of the limitations of natural language parsing. A keyword search will not pick-up on things such as slang, typos, abbreviations, or sarcasm. Additionally, any online forum is subject to trolling and any number of forum "games" which could affect the data being collected.

<h3>Future Work</h3>
There is still some manual setup required to get the tool running (specifically the database creation). It would be ideal to somehow automate this process. Perhaps by storing the data to a file, rather than into a proper database. This would reduce performance but would be much simpler and would work across more platforms (SQLEXPRESS not required).

The UI of the tool could also be improved if the tool were to be developed further. The current version is essentially a prototype with barebones functionality. UI elements such as date-pickers would make it much easier to fill-out the graph creation forms. Requiring epoch time is not user-friendly.

There is some overlap in functionality between the live subreddit monitor and the data scraper. Both of them query the Reddit API, but the live monitor does not store any data. These two features code be merged so that as the subreddit is monitored, the database is simultaneously updated. However, this merge could become complex since Reddit posts vary in score over time and the live updater just looks at posts in the last 24 hours. So it would also periodically need to check backwards in time to update the scores of older posts.

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
- JFreeChart for automated graph generation from Java: http://www.jfree.org/jfreechart/
- JFreeChart Tutorials: http://www.tutorialspoint.com/jfreechart/jfreechart_file_interface.htm
- Java Swing & AWT for the UI: http://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html
- Also, See Project 1 README for additional sources
