package jeremy.seng371.part1;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import jeremy.seng371.part1.PatchNoteMain.PatchNoteMetaData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class LOLPatchNoteParser implements IPatchNoteParser {

	@Override
	public String getGameName() {
		return("League of Legends");
	}
	
	@Override
	public List<String> getRootPageURLs() {
		// LOL just has the one page
		List<String> listOfURLs = new ArrayList<String>();
		listOfURLs.add("http://leagueoflegends.wikia.com/wiki/Patch");
		return listOfURLs;
	}

	@Override
	public List<PatchNoteMetaData> getPatchDetailsFromRoot(String htmlPageText) {
		
		List<PatchNoteMetaData> patchNoteData = new ArrayList<PatchNoteMetaData>();
		
		Document doc = Jsoup.parse(htmlPageText);
		//Elements links = doc.select("a");
		//Element head = doc.select("#head").first();
		
		Elements tableRows = doc.select("tr");
		for(Element row : tableRows){
			// Get each cell within the row
			Elements cells = row.select("td");
			if( cells.size() == 5){

				//String url = cells.get(4).select("a").first().attributes().get("href");
				String url = "http://leagueoflegends.wikia.com"+cells.get(0).select("a").first().attributes().get("href");
				
				// TODO do this with regex
				// Returns the following format: "YYYY-MM-DD", or "MM-DD-YYYY", or "<Month> YYYY"
				String dateText = cells.get(1).text();
				int year,month,day;
				try{
					// Regular case
					year = Integer.parseInt(dateText.substring(0, 4));
					month = Integer.parseInt(dateText.substring(5, 7));
					day = Integer.parseInt(dateText.substring(8, 10));
				} catch(Exception e){
					// Other case
					try{
						month = Integer.parseInt(dateText.substring(0, 2));
						day = Integer.parseInt(dateText.substring(3, 5));
						year = Integer.parseInt(dateText.substring(6, 10));
					} catch(Exception e1){
						// Ugly case, give up
						continue;
					}
				} 
				Calendar cal = new GregorianCalendar();
				cal.set(year, month, day);
				Date dateTime = new Date(cal.getTimeInMillis());

				String patchName = cells.get(0).text();
				
				patchNoteData.add(new PatchNoteMetaData(url, dateTime, patchName));
			}
		}
		
		
		return patchNoteData;
	}

	@Override
	public String getBodyTextFromPatchURL(String htmlPageText) {
		Document doc = Jsoup.parse(htmlPageText);
		Element article = doc.getElementById("WikiaArticle");
		// Remove the box on the bottom which links to other pages
		article.getElementsByAttributeValueContaining("class", "navbox hlist").remove();
		return article.text();
	}

}
