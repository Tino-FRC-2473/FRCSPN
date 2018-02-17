package stages.matches;

import gui.BoxPaddingInsets;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class SuggestedLabel extends Label {
	
	public SuggestedLabel(String name) {
		super(name);
		this.setStyle("-fx-background-color: #ADD8E6; -fx-font-size: 14; -fx-stroke: black; -fx-font-weight: bold");
		this.setPadding(new Insets(BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET, BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET));
	}
}
