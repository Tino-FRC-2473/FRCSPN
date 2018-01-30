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
	
	public static Requester getRequester() {
		return req;
	}
	
	
	private  void tbaStatus() {
		System.out.println(req.getTBAStatus()); //NEED THIS TO BE RUN IN A LOAD SCREEN
	}
	
	@Override
	public void start(Stage temp) throws Exception {
		stage = new MainStage();
		this.tbaStatus();
		stage.show();
	}
	
	public static void main(String[] args) {
		req = new Requester(true);
        launch(args);
    }
}