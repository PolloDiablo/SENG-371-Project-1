<h2>Plan</h2>

<h3>Tasks</h3>
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
    <td>8</td>
    <td>required</td>
  </tr>
  <tr>
    <td>Auotmated Graph Ceation</td>
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
    <td>4</td> 
    <td>9</td>
    <td>optional</td>
  </tr>
  <tr>
    <td>Final Report/Video</td>
    <td>10</td> 
    <td>10</td>
    <td>required</td>
  </tr>
</table>

<h3>Friday March 13</h3>
  - <b>Brayden</b>: <b>Subreddit Monitoring Tool</b> which will detect spikes in activity and notify developers
  - <b>Jeremy</b>: <b>Auotmated Graph Ceation</b> (for keyword graphs) using JFree Chart library. Also some refactoring (renaming, organization, etc. of project files in Eclipse project).

<h3>Tuesday March 17</h3>
  - <b>Brayden</b>: TODO
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
    
<h3>Friday March 20</h3>
  - <b>Brayden</b>: TODO
  - <b>Jeremy</b>: <b>Implement Alternate Visualization</b> types (with a tool, ideally automated)
    - User could enter a list of terms (this is would be time-consuming, any better way to do this?)
    - Tool could automatically detect # of terms
      - If only 1 search term, do a graph of that term over time
      - If multiple search terms, autogenerate some of the alternative graphs
    - In absence of Patch Note data, what graphs can be generated from just Reddit data?
 
<h3>Tuesday March 24</h3>
  - <b>Brayden</b>: TODO
  - <b>Jeremy</b>: <b>Apply Methodology to Non-game Projects</b>
    - Investigation of non-gaming projects using current toolset

<h3>Friday March 27</h3>
  - <b>Brayden and Jeremy</b>: Draft of project <b>Final Report/Video</b>

<h3>Monday March 30</h3>
  - <b>Brayden and Jeremy</b>: Final version of <b>Final Report/Video</b>
  
<h3>If we have extra time...</h3>
- <b>Research Alternate Sources of Patch Data</b>
  - The problem with our current data source is that a script must be created for each project to parse webpages and pull down the patch data 
  - Want want to find sources with a consistent interface/API that could be queried
  - e.g. internal bug tracking tools, Jira, GitHub issues, etc.
- <b>Tool/UI to Parse Reddit Data</b>
  - user inputs subreddit name, game name (not really necessary, could just use subreddit name), start date, end date, and keywords to look for (defaults are "bug", "issue", "and crash")
  - does the user also specify the database they would like to connect to?

