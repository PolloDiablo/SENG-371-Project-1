package jeremy.seng371.part1;
import java.util.List;

import jeremy.seng371.part1.PatchNoteMain.PatchNoteMetaData;


public interface IPatchNoteParser {
	 
	/** Returns name of this game, will be associated with each database row entry*/
	public abstract String getGameName();
	
	/** Each game has a list of patch notes online.
	 * This list often on one page, but may also be spread across multiple pages.
	 * 
	 * This function returns the list of the URLs to each of these "root" pages.
	 * (Should be at least one)
	 * 
	 * */
	public abstract List<String> getRootPageURLs();
	
	/** Input: a "root" page containing a list of links 
	 * 		(and other meta information) for many patch notes
	 *  
	 *  Returns all of details for each patch note
	 *  	- gameName (required, will be the same for all patch notes for a given dame)
	 *  	- url to full patch body (required)
	 *  	- date (required)
	 *  	- version-number/name (optional)
	 */
	public abstract List<PatchNoteMetaData> getPatchDetailsFromRoot(String htmlPageText);
	
	/** Get the body text from the patch (the current page)*/
	public abstract String getBodyTextFromPatchURL(String htmlPageText);
}
