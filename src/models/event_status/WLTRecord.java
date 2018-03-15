package models.event_status;

public class WLTRecord {
	public int losses;
	public int ties;
	public int wins;
	
	@Override
	public String toString() {
		return wins + "-" + losses + "-" + ties;
	}
}
