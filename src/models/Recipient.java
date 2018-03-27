package models;

public class Recipient {
	public String team_key;
	public String awardee;
	
	@Override public String toString() {
		return ((team_key == null)?"":team_key) + ((awardee == null)?"":((team_key == null)?awardee:", " + awardee));
	}
}
