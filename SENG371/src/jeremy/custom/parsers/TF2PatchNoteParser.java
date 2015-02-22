package jeremy.custom.parsers;

import java.util.List;

import jeremy.seng371.part1.IPatchNoteParser;
import jeremy.seng371.part1.PatchNoteMain.PatchNoteMetaData;

public class TF2PatchNoteParser implements IPatchNoteParser {

	@Override
	public String getGameName() {
		return("Team Fortress 2");
	}
	
	@Override
	public List<String> getRootPageURLs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatchNoteMetaData> getPatchDetailsFromRoot(String htmlPageText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBodyTextFromPatchURL(String htmlPageText) {
		// TODO Auto-generated method stub
		return null;
	}

}
