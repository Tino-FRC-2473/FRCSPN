package general;

import general.images.I;
import general.requests.Database;
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
	
	@SuppressWarnings("unused")
	private static void testAddingRequests() {
		reqThread.addRequestStatus();
		reqThread.addRequestEventKeysInYear(2017);
		reqThread.addRequestEventGeneralInfo("2017tur");
		reqThread.addRequestEventsForTeamInYear(2473, 2017);
		reqThread.addRequestMatchesForTeamAtEvent(1676, "2017nhfoc");
//		reqThread.addRequest(new R(R.Type.MATCHES_AT_EVENT, "2017tur"));
		reqThread.addRequestTeamsAtEvent("2017tur");
		reqThread.addRequestStatusForTeamAtEvent(254, "2017dal");
	}
	
	@SuppressWarnings("unused")
	private static void startTestGetRequestsThread() {
		new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						database.printLengths();
						database.printIncomplete();
						System.out.println("\t\tstatus: " + database.getStatus());
						System.out.println("\t\tyear event keys: " + database.getEventKeysInYear(2017));
						System.out.println("\t\tevent: " + database.getEventInfo("2017tur"));
						System.out.println("\t\tteam events: " + database.getEventsForTeamInYear(2473, 2017));
//						System.out.println("\t\tmatches: " + database.generalGet(new R(R.MATCHES_AT_EVENT, "2017tur"), Match_2017Steamworks.class));
						System.out.println("\t\tteam matches: " + database.getMatchesForTeamAtEvent(1676, "2017nhfoc"));
						System.out.println("\t\tteams: " + database.getTeamsAtEvent("2017tur"));
						System.out.println("\t\tevent status: " + database.getStatusForTeamAtEvent(254, "2017dal"));
						System.out.println("");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
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
//		startTestGetRequestsThread();
		stage.show();
	}
	
	public static void main(String[] args) {
		database = new Database();
		reqThread = new RequesterThread();
		reqThread.start();
//		testAddingRequests();
        launch(args);
    }
}