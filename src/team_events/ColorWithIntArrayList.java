package team_events;

import java.util.ArrayList;

import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public class ColorWithIntArrayList extends ArrayList<Integer> {
	private String color;
	
	public ColorWithIntArrayList(String s) {
		color = s;
	}
	
	public ColorWithIntArrayList(ArrayList<Double> arr) {
		
	}
	
	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return color;
	}
}
