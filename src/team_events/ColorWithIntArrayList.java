package team_events;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ColorWithIntArrayList extends ArrayList<Integer> {
	private String color;
	
	public ColorWithIntArrayList(String s) {
		color = s;
	}
	
	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return color + ": " + super.toString();
	}
}
