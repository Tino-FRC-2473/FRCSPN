package stages.team_events;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import general.constants.*;

public class LoadingPane extends Pane{
	Label message;
	public LoadingPane() {
		super();
		setPadding(K.getInsets());
		setPrefWidth(K.TEAM_EVENTS.CENTER_WIDTH);
		setPrefHeight(K.TEAM_EVENTS.HEIGHT);
		setStyle("fx-background-color: #FFFFFF");
		message = new Label("Loading...");
		message.setStyle("fx-font-size: 54px");
		message.setLayoutY(K.TEAM_EVENTS.HEIGHT*0.75);
		message.setLayoutX(K.TEAM_EVENTS.LEFT_WIDTH+K.TEAM_EVENTS.CENTER_WIDTH/2);
		
		
	}
}
