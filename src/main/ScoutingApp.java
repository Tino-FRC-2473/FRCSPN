package main;

import javafx.application.Application;
import javafx.stage.Stage;
import team_events.TeamEventsStage;

public class ScoutingApp extends Application {
	public static MainStage stage;
	public static TeamEventsStage teStage;
	private static Requester req;
	
	public static void launchTeamEvents() {
		teStage = new TeamEventsStage();
		teStage.show();
	}
	
	@Override
	public void start(Stage temp) throws Exception {
		stage = new MainStage();
		stage.show();
	}
	
	public Requester getRequester() {
		return req;
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}