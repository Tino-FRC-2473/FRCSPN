package stages.matches;

import gui.BoxPaddingInsets;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import models.Event;

public class SuggestedLabel extends Label {
	private Event event;
	
	public SuggestedLabel(Event event) {
		super(event.name);
		this.event = event;
		this.setStyle("-fx-background-color: #ADD8E6; -fx-font-size: 14; -fx-stroke: black; -fx-font-weight: bold");
		this.setPadding(new Insets(BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET, BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET));
	}
	
	public Event getEvent() {
		return this.event;
	}
}
