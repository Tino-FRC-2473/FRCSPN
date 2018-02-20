package stages.matches;

import general.ScoutingApp;
import general.constants.K;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.Event;
import models.Team;


public class MLoadingScene extends Scene {
	private Pane pane;
	private Circle[] circles;
	private final Color GREY = new Color(0.78, 0.78, 0.78, 1);
	private int i;
	
	private MLoadingThread thread;
	
	@SuppressWarnings("deprecation")
	public MLoadingScene(Pane p) {
		super(p);
		pane = p;
		pane.setPadding(K.getInsets());
		pane.setPrefWidth(K.MATCHES.WIDTH);
		pane.setPrefHeight(K.MATCHES.HEIGHT);
		pane.setStyle("-fx-background-color: #FFFFFF");
		
		Label message = new Label("Loading...");
		pane.getChildren().add(message);
		message.setStyle("-fx-font-size: 42;");
		message.impl_processCSS(true);
		
		message.setLayoutX((K.MATCHES.WIDTH - message.prefWidth(-1))/2);
		message.setLayoutY((K.MATCHES.HEIGHT - message.prefHeight(-1))/2);
		
		circles = new Circle[12];
		for(int i = 1; i <= 12; i++) {
			circles[i-1] = new Circle(0.05 * K.MATCHES.HEIGHT);
			p.getChildren().add(circles[i-1]);
			circles[i-1].setCenterX(K.MATCHES.WIDTH/2 + K.MATCHES.HEIGHT*0.35*Math.sin(Math.toRadians(360.0/12*(12-i))));
			circles[i-1].setCenterY(K.MATCHES.HEIGHT/2 + K.MATCHES.HEIGHT*0.35*Math.cos(Math.toRadians(360.0/12*(12-i))));
		}
		
		i = 0;
		circles[i].setFill(GREY);
	}
	
	public void rotate() {
		circles[i++].setFill(Color.BLACK);
		if(i >= 12) i = 0;
		circles[i].setFill(GREY);
	}
	
	public void start() {
		thread = new MLoadingThread();
		thread.start();
	}
	
	private class MLoadingThread extends Thread {
		private boolean alive;
		private int completions;
		
		public MLoadingThread() {
			alive = true;
			completions = 0;
		}

		public void run() {
			Team[] teams = null;
			
			while(alive) {
				rotate();
				
				try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
				
				if(ScoutingApp.getDatabase().getNumberIncompleteRequests() == 0) {
					if(ScoutingApp.mStage.getState().equals(MatchesStage.State.LOADING1)) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								ScoutingApp.mStage.setState(MatchesStage.State.SELECTING);
								end();
							}
						});
					} else if(ScoutingApp.mStage.getState().equals(MatchesStage.State.LOADING2)) {
						if(completions == 0) {
							System.out.println("***NEXT: EVENTS/TEAM***");
							//done: request teams at event
							//requesting: events for each team
							teams = ScoutingApp.getDatabase().getTeamsAtEvent(ScoutingApp.mStage.getEvent().key);
							for(Team t : teams)
								ScoutingApp.getRequesterThread().addRequestEventsForTeamInYear(t.getNumber(), 2018);
						} else if(completions == 1) {
							System.out.println("***NEXT: STATUS/TEAM/EVENT***");
							//done: events for each team
							//requesting: status for each team at each of their events
							for(Team t : teams) {
								Event[] events = ScoutingApp.getDatabase().getEventsForTeamInYear(t.getNumber(), 2018);
								for(Event e : events)
									ScoutingApp.getRequesterThread().addRequestStatusForTeamAtEvent(t.getNumber(), e.key);
							}
						} else if(completions == 2) {
							System.out.println("***NEXT: MATCH KEYS***");
							//done: status for each team for each of their events
							//requesting: match keys for the event
							ScoutingApp.getRequesterThread().addRequestMatchKeysForEvent(ScoutingApp.mStage.getEvent().key);
						} else if(completions == 3) {
							System.out.println("***NEXT: MATCHES***");
							//done: match keys for this event
							//requesting: all matches for the event
							String[] matchKeys = ScoutingApp.getDatabase().getMatchKeysForEvent(ScoutingApp.mStage.getEvent().key);
							for(String mKey : matchKeys)
								ScoutingApp.getRequesterThread().addRequestMatch(mKey);
						} else if(completions == 4) {
							System.out.println("***NEXT: AWARDS***");
							//done: all matches for the event
							//requesting: awards for the event
							ScoutingApp.getRequesterThread().addRequestAwardsAtEvent(ScoutingApp.mStage.getEvent().key);
						} else {
							System.out.println("***DONE***");
							//done: awards for the event
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									ScoutingApp.mStage.setState(MatchesStage.State.MAIN);
									end();
								}
							});
						}
						completions++;
					}
				}
			}
//			System.out.println("loading thread ended");
		}
		
		public void end() {
//			System.out.println("loading thread told to end");
			alive = false;
		}
	}
}
