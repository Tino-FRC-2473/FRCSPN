package main;

import javafx.geometry.Insets;

public class BoxPaddingInsets extends Insets {
	private final static int OFFSET = 10;
	
	public BoxPaddingInsets() {
		super(OFFSET, OFFSET, OFFSET, OFFSET);
	}
}
