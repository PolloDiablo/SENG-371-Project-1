package jeremy.seng371.part1;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import jeremy.seng371.part1.PatchNoteMain.PatchNoteMetaData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TF2PatchNoteParser implements IPatchNoteParser {

	@Override
	public String getGameName() {
		return("Team Fortress 2");
	}
	
	@Override
	public List<String> getRootPageURLs() {
		// LOL just has the one page
		List<String> listOfURLs = new ArrayList<String>();
		listOfURLs.add("https://wiki.teamfortress.com/wiki/Patches");
		return listOfURLs;
	}

	@Override
	public List<PatchNoteMetaData> getPatchDetailsFromRoot(String htmlPageText) {
		
		List<PatchNoteMetaData> patchNoteData = new ArrayList<PatchNoteMetaData>();
		
		Document doc = Jsoup.parse(htmlPageText);
		//Elements links = doc.select("a");
		//Element head = doc.select("#head").first();
		
		// Get all of the elements of the list of patches
		Elements listItems = doc.getElementsByAttributeValueContaining("style",
				"-moz-column-count:4; -webkit-column-count:4; column-count:4;").select("li");
		for(Element item : listItems){
			// Get patch url
			String url = "https://wiki.teamfortress.com"+item.select("a").first().attributes().get("href");		
			
			// Get patch date
			String dateText = item.text();
			String formattedDate = null;
			try {
				DateFormat inputFormat = new SimpleDateFormat("MMMMM d, yyyy");
				DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate = outputFormat.format(inputFormat.parse(dateText));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int year = Integer.parseInt(formattedDate.substring(0, 4));
			int month = Integer.parseInt(formattedDate.substring(5, 7));
			int day = Integer.parseInt(formattedDate.substring(8, 10));
			Calendar cal = new GregorianCalendar();
			cal.set(year, month, day);
			Date dateTime = new Date(cal.getTimeInMillis());
			
			// Now perform a backwards look-up of the date, to see if the patch has a name
			String patchName = dateText; //backup, since some of the patches do not have titles
			Elements cells = doc.select("td");
			for(Element cell : cells){
				if(cell.text().contains(dateText)){
					Element titleElement = cell.select("a").get(1);
					patchName = titleElement.text();
				}
				
			}
			patchNoteData.add(new PatchNoteMetaData(url, dateTime, patchName));
		}
		return patchNoteData;
	}

	@Override
	public String getBodyTextFromPatchURL(String htmlPageText) {
		Document doc = Jsoup.parse(htmlPageText);
		Element article = doc.getElementById("bodyContent");
		return article.text();
	}

}
