package models;

public class Team {
	public String key;
	public String nickname;
	public String city;
	public String state_prov;
	public String country;
	
	public int getNumber() {
		return Integer.parseInt(key.substring(3));
	}
}
