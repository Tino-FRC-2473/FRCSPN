package models;

public class Award {
	public String name;
	public String event_key;
	public Recipient[] recipient_list;
	public int year;
}

class Recipient {
	public String team_key;
	public String awardee;
}