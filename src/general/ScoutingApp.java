package general;

import general.requests.Database;
import general.requests.R;
import general.requests.RequesterThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import stages.main.MainStage;
import stages.matches.MatchesStage;
import stages.team_events.TeamEventsStage;

/**
 * The main javafx application.
 */
public class ScoutingApp extends Application {
	public static MainStage stage;
	public static TeamEventsStage teStage;
	public static MatchesStage mStage;
	private static RequesterThread reqThread;
	private static Database database;
	
	public static void launchTeamEvents() {
		teStage = new TeamEventsStage();
		teStage.show();
	}
	
	public static void launchMatches() {
		mStage = new MatchesStage();
		mStage.show();
	}
	
	public static Database getDatabase() {
		return database;
	}
	
	public static RequesterThread getRequesterThread() {
		return reqThread;
	}
	
	@Override
	public void start(Stage temp) throws Exception {
		stage = new MainStage();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	System.out.println("end");
		    	reqThread.end();
		        Platform.exit();
		        System.exit(0);
		    }
		});
		stage.show();
	}
	
	public static void main(String[] args) {
		database = new Database();
		reqThread = new RequesterThread();
		reqThread.start();
        launch(args);
    }
}