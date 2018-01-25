package models;

public class Event {
	public String key;
	public String name;
	public String event_code;
	public District district;
	public String city;
	public String state_prov;
	public String country;
	public String start_date;
	public String end_date;
	public int year;
	
	@Override
	public String toString() {
		return name + " " + key + " " + event_code + " " + city + " " + state_prov + " " + country + " " + start_date + " " + end_date + " " + year;
	}
}
