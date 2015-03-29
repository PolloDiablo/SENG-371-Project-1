package awesome.seng371.part2;

public class GraphTester {

	
	//All the league champs as of March 2015
	/*private static final String keywords = "Aatrox,Ahri,Akali,Alistar,Amumu,Anivia,Annie,Ashe,Azir,Bard,Blitzcrank," +
			"Brand,Braum,Caitlyn,Cassiopeia,Cho'Gath,Corki,Darius,Diana,Draven,Elise,Evelynn,Ezreal,Fiddlesticks,Fiora," +
			"Fizz,Galio,Gangplank,Garen,Gnar,Gragas,Graves,Hecarim,Heimerdinger,Irelia,Janna,Jarvan,Jax,Jayce,Jinx,Kalista," +
			"Karma,Karthus,Kassadin,Katarina,Kayle,Kennen,Kha'Zix,Kog'Maw,LeBlanc,Lee,Leona,Lissandra,Lucian,Lulu,Lux,Malphite,Malzahar," +
			"Maokai,Miss Fortune,Mordekaiser,Morgana,Mundo,Nami,Nasus,Nautilus,Nidalee,Nocturne,Nunu,Olaf,Orianna,Pantheon,Poppy,Quinn," +
			"Rammus,Rek'Sai,Renekton,Rengar,Riven,Rumble,Ryze,Sejuani,Shaco,Shen,Shyvana,Singed,Sion,Sivir,Skarner,Sona,Soraka,Swain," +
			"Syndra,Talon,Taric,Teemo,Thresh,Tristana,Trundle,Tryndamere,Twisted Fate,Twitch,Udyr,Urgot,Varus,Vayne,Veigar,Vel'Koz,Vi,Viktor," +
			"Vladimir,Volibear,Warwick,Wukong,Xerath,Xin Zhao,Yasuo,Yi,Yorick,Zac,Zed,Ziggs,Zilean,Zyra";*/
	
	// Champs released before 2014
	private static final String keywords = "Aatrox,Ahri,Akali,Alistar,Amumu,Anivia,Annie,Ashe,Blitzcrank,Brand,Caitlyn," +
			"Cassiopeia,Cho'Gath,Corki,Darius,Diana,Draven,Elise,Evelynn,Ezreal,Fiddlesticks,Fiora,Fizz,Galio,Gangplank," +
			"Garen,Gragas,Graves,Hecarim,Heimerdinger,Irelia,Janna,Jarvan,Jax,Jayce,Jinx,Karma,Karthus,Kassadin,Katarina,Kayle," +
			"Kennen,Kha'Zix,Kog'Maw,LeBlanc,Lee,Leona,Lissandra,Lucian,Lulu,Lux,Malphite,Malzahar,Maokai,Miss Fortune,Mordekaiser," +
			"Morgana,Mundo,Nami,Nasus,Nautilus,Nidalee,Nocturne,Nunu,Olaf,Orianna,Pantheon,Poppy,Quinn,Rammus,Renekton,Rengar,Riven," +
			"Rumble,Ryze,Sejuani,Shaco,Shen,Shyvana,Singed,Sion,Sivir,Skarner,Sona,Soraka,Swain,Syndra,Talon,Taric,Teemo,Thresh,Tristana," +
			"Trundle,Tryndamere,Twisted Fate,Twitch,Udyr,Urgot,Varus,Vayne,Veigar,Vi,Viktor,Vladimir,Volibear,Warwick,Wukong,Xerath," +
			"Xin Zhao,Yasuo,Yi,Yorick,Zac,Zed,Ziggs,Zilean,Zyra";
	
	private static final String databaseTableName = "PatchNotes";
	private static final String gameName = "League of Legends";
	private static final String filePrefix = "LOL";
	
	
	/* Start of 2013 = 1357027200
	 * Start of 2014 = 1388563200
	 * Start of 2015 = 1420099200
	 */
	private static final long queryStartDate = 1388563200;
	private static final long queryEndDate = 1420099200;
	
	private static final String databaseURL = "jdbc:sqlserver://localhost:1433;databaseName=db371project;user=sa;password=sosecure";
	
	
	public static void main( String[] args ){	
		GraphCreator_MultiKeyword.createCharts(keywords, filePrefix, databaseURL, databaseTableName, gameName, queryStartDate , queryEndDate);
	}
	
}
