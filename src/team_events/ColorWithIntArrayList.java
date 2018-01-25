package team_events;

import java.util.ArrayList;

import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public class ColorWithIntArrayList extends ArrayList<Integer> {
	private Color color;
	
	public ColorWithIntArrayList(double r, double g, double b, double o) {
		color = new Color(r, g, b, o);
	}
	
	public ColorWithIntArrayList(ArrayList<Double> arr) {
		this(arr.get(0), arr.get(1), arr.get(2), (arr.size()>3)?arr.get(3):1);
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "): " + super.toString();
	}
}
