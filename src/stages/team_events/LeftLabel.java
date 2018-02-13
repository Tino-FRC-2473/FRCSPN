package stages.team_events;

import general.constants.K;
import gui.BoxPaddingInsets;
import javafx.scene.control.Label;

public class LeftLabel extends Label {
	public LeftLabel(StringWithColor sc) {
		super(sc.getString());
		this.setStyle("-fx-background-color: #" + sc.getColor() + "; "
				+ "-fx-font-size: 20; -fx-stroke: black; -fx-font-weight: bold");
		K.getInsets();
		this.setPrefSize(K.TEAM_EVENTS.LEFT_WIDTH - 2*BoxPaddingInsets.OFFSET, K.TEAM_EVENTS.LEFT_BUTTON_HEIGHT);
		this.setPadding(K.getInsets());
	}
}
