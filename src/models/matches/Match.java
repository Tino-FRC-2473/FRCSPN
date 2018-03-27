package models.matches;

//extend this class and have a score_breakdown per year
public abstract class Match {
	public String key;
	public String comp_level;
	public int set_number;
	public int match_number;
	public Alliances alliances;
	public String winning_alliance;
	public String event_key;
	public int time;
	public int actual_time;
	public int predicted_time;
	public int post_result_time;
	public Video[] videos;
	
	public String getName() {
		int i = key.indexOf("_")+1;
		if(key.indexOf("qm", i) != -1) 
			return "Quals " + key.substring(i+2);
		else if (key.indexOf("qf", i) != -1)
			return "Quarters " + key.charAt(i+2) + " Match " + key.charAt(i+4);
		else if (key.indexOf("sf", i) != -1)
			return "Semis " + key.charAt(i+2) + " Match " + key.charAt(i+4);
		else if (key.indexOf("f", i) != -1)
			return "Finals " + key.charAt(i+1) + " Match " + key.charAt(i+3);
		return null;
	}
}
