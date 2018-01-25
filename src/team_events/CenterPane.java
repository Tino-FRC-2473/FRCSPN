package team_events;

import constants_and_images.K;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.ScoutingApp;

public class CenterPane extends Pane {
	public CenterPane() {
		super();
		
		setPadding(K.getInsets());
		setPrefSize(K.TEAM_EVENTS.CENTER_WIDTH, K.TEAM_EVENTS.HEIGHT); //fix height eventually
		setStyle("-fx-background-color: #FFFFFF");
		
		Label test = new Label("254");
		test.setStyle("-fx-background-color: #FF0000;");
  		getChildren().add(test);
  		
		updateTeamInfo(Integer.parseInt(test.getText()));
	}
	
	private void updateTeamInfo(int teamNumber) {
		Label name = new Label(Integer.toString(teamNumber));
		name.setStyle("-fx-font-size: 36; -fx-font-color: #346233");
		String[] teamEvents = ScoutingApp.getRequester().getTeamEventsForYear(teamNumber,2018);
		if(teamEvents == null) {
			teamEvents = new String[]{"no events"};
		}
		
		String str = new String();
		for(int i = 0; i < teamEvents.length; i++) {
			if(i > 0)
				str += " ";
			str += teamEvents[i];
		}
		
		Label events = new Label(str);
		getChildren().addAll(name,events);
	}
}
