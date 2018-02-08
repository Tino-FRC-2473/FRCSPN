package gui;

import javafx.geometry.Insets;

public class BoxPaddingInsets extends Insets {
	public final static int OFFSET = 10;
	
	public BoxPaddingInsets() {
		super(OFFSET, OFFSET, OFFSET, OFFSET);
	}
	
	public BoxPaddingInsets(int i) {
		super(i, i, i, i);
	}
}
