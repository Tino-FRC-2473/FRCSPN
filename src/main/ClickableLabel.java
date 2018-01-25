package main;

import javafx.scene.control.Label;

public abstract class ClickableLabel extends Label {
	public ClickableLabel(String s) {
		super(s);
	}

	public abstract void onClick();
}