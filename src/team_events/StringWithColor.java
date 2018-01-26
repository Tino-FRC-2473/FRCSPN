package team_events;

@SuppressWarnings("serial")
public class StringWithColor {
	private String str;
	private String color;
	
	public StringWithColor(String s, String c) {
		str = s;
		color = c;
	}
	
	public String getValue() {
		return str;
	}
	
	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return str + " (" + color + ")";
	}
}
