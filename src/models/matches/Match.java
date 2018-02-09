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
}
