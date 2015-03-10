<h2>Plan</h2>

TODO Other things we could work on:
- Research other methods of obtaining patch data (e.g. internal bug tracking tools, Jira, GitHub issues, etc.)
- Create a tool/UI for parsing Reddit data 
  - user inputs subreddit name, game name (not really necessary, could just use subreddit name), start date, end date, and keywords to look for (defaults are "bug", "issue", "and crash")
  - does the user also specify the database they would like to connect to?

<h3>Friday March 13</h3>
  - <b>Brayden</b>: subreddit monitoring tool which will detect spikes in activity and notify developers
  - <b>Jeremy</b>: automated keyword graph creation using JFree Chart library

<h3>Tuesday March 17</h3>
  - <b>Brayden</b>: TODO
  - <b>Jeremy</b>: investigate alternative visualization types which can be obtained from existing data. Create a few sample charts to demonstrate that the data analysis method is valid.
    - Compare bug mention activity between keywords of the same category (e.g. characters, maps, or items). 
    This list could be complete (e.g. all the maps), or a subset of the keywords in the category (e.g. a few of the maps).
      - Looking at patch notes and forum posts separately => <b>wordcloud</b>, <b>piechart</b>, or <b>barchart</b>
      - Comparing patch notes and forum posts => <b>stacked column chart</b>
    - Could normalize the above data, to get the proportion of activity for each keyword
      - Difference between patch notes and forum posts => <b>column chart</b> (which items get the most/least relative attention)
      - e.g. http://www2.le.ac.uk/offices/ld/images/study-guide-images/numeracy/bar2.gif
    - Note: Must consider what time-span to look at. Shouldn't look at keywords before they exist in the game.
    (Their proportion would be relatively low).
    
<h3>Friday March 20</h3>
  - <b>Brayden</b>: TODO
  - <b>Jeremy</b>: automate creation of alternative visualization types (with a tool)
    - User could enter a list of terms (this is would be time-consuming, any better way to do this?)
    - Tool could automatically detect # of terms
      - If only 1 search term, do a graph of that term over time
      - If multiple search terms, autogenerate some of the alternative graphs
    - In absence of Patch Note data, what graphs can be generated from just Reddit data?
 
<h3>Tuesday March 24</h3>
  - <b>Brayden</b>: TODO
  - <b>Jeremy</b>: Investigation of non-gaming projects using current toolset


<h3>Friday March 27</h3>
  - <b>Brayden and Jeremy</b>: Draft of project report/presentation?

<h3>Monday March 30</h3>
  - <b>Brayden and Jeremy</b>: Final version of project report/presentation?
