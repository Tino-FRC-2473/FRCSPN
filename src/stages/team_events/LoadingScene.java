package stages.team_events;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import general.constants.*;

public class LoadingScene extends Scene{
	Label message;
	Pane pane;
	TeamEventsStage s;
	public LoadingScene(Pane p, TeamEventsStage s) {
		super(p);
		this.s = s;
		pane = p;
		pane.setPadding(K.getInsets());
		pane.setPrefWidth(K.TEAM_EVENTS.CENTER_WIDTH);
		pane.setPrefHeight(K.TEAM_EVENTS.HEIGHT);
		pane.setStyle("fx-background-color: #FFFFFF");
		message = new Label("Loading...");
		message.setStyle("fx-font-size: 36px");
		message.setLayoutY(K.TEAM_EVENTS.HEIGHT/2);
		message.setLayoutX((K.TEAM_EVENTS.LEFT_WIDTH+K.TEAM_EVENTS.CENTER_WIDTH)/2);
		pane.getChildren().add(message);
	}
}
