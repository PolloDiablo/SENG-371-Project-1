package jeremy.custom.parsers;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jeremy.seng371.part1.IPatchNoteParser;
import jeremy.seng371.part1.PatchNoteMain.PatchNoteMetaData;

public class WOWPatchNoteParser implements IPatchNoteParser {
	
	@Override
	public String getGameName() {
		return("World of Warcraft");
	}
	
	
	@Override
	public List<String> getRootPageURLs() {
		// LOL just has the one page
		List<String> listOfURLs = new ArrayList<String>();
		listOfURLs.add("http://www.wowwiki.com/Patches/0.x");
		listOfURLs.add("http://www.wowwiki.com/Patches/1.x");
		listOfURLs.add("http://www.wowwiki.com/Patches/2.x");
		listOfURLs.add("http://www.wowwiki.com/Patches/3.x");
		listOfURLs.add("http://www.wowwiki.com/Patches"); //Contains 4.x and 5.x
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

				String url = null;
				try {
					url = "http://www.wowwiki.com"+cells.get(0).select("a").first().attributes().get("href");
				}catch(Exception e){
					// Some very minor patches have no data (no url), skip these
					continue;
				}
				// Get patch date
				String dateText = cells.get(1).text();
				String formattedDate = null;
				try {
					DateFormat inputFormat = new SimpleDateFormat("d MMMMM yyyy");
					DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = outputFormat.format(inputFormat.parse(dateText));
				} catch (ParseException e) {
					// Some patches just say "Beta", these aren't really patches, so skip these rows
					continue;
				}
				int year = Integer.parseInt(formattedDate.substring(0, 4));
				int month = Integer.parseInt(formattedDate.substring(5, 7));
				int day = Integer.parseInt(formattedDate.substring(8, 10));
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
		try{
			Document doc = Jsoup.parse(htmlPageText);
			Element article = doc.getElementById("WikiaArticle");
			// Remove external links are references from the bottom of the article
			article.getElementsByAttributeValueContaining("class", "elinks-item").remove();
			article.getElementsByAttributeValueContaining("class", "navbox").remove();
			return article.text();
		}catch(Exception e){
			System.out.println("Warning, page load failed in getBodyTextFromPatchURL(), keeping text empty.");
			return "";
		}
	}
}
