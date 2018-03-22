package stages.matches;

import java.util.HashMap;
import java.util.Iterator;

import general.ScoutingApp;
import general.constants.K;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.Team;
import models.event_status.EventStatus;
import models.matches.yr2018.Match_PowerUp;


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
		
		public MLoadingThread() {
			alive = true;
		}

		public void run() {
			Team[] teams = null;
			
			HashMap<Team, HashMap<String, EventStatus>> teamEventStatuses = new HashMap<Team, HashMap<String, EventStatus>>();
			boolean teamEventStatusesCompleted = false;
			
			Match_PowerUp[] matches = null;
			
//			boolean matchesCompleted = false;
			
			while(alive) {
				rotate();
				
				try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
				
				if(ScoutingApp.mStage.getState().equals(MatchesStage.State.LOADING1) && ScoutingApp.getDatabase().getNumberIncompleteRequests() == 0) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ScoutingApp.mStage.setState(MatchesStage.State.SELECTING);
							end();
						}
					});
				} else if(ScoutingApp.mStage.getState().equals(MatchesStage.State.LOADING2)) {
					
					if(teams == null) {
						teams = ScoutingApp.getDatabase().getTeamsAtEvent(ScoutingApp.mStage.getEvent().key);
						if(teams != null) {
							System.out.println("***REQUESTING TEAM STATUSES***");
							for(int i = 0; i < teams.length; i++) {
								ScoutingApp.getRequesterThread().addRequestStatusesForTeamInYear(teams[i].getNumber(), 2018);
								teamEventStatuses.put(teams[i], null);
							}
						}
					} else if(!teamEventStatusesCompleted) {
						for(final Iterator<Team> itr = teamEventStatuses.keySet().iterator(); itr.hasNext();) {
							Team t = itr.next();
							if(teamEventStatuses.get(t) == null)
								teamEventStatuses.put(t, ScoutingApp.getDatabase().getStatusesForTeamInYear(t.getNumber(), 2018));
						}
						
						boolean foundNull = false;
						for(final Iterator<HashMap<String, EventStatus>> itr = teamEventStatuses.values().iterator(); itr.hasNext();) {
							HashMap<String, EventStatus> statuses = itr.next();
							if(statuses == null) {
								foundNull = true;
								break;
							}
						}
						
						if(!foundNull) {
							System.out.println("***NEXT: MATCHES***");
							teamEventStatusesCompleted = true;
							ScoutingApp.getRequesterThread().addRequestMatchesAtEvent(ScoutingApp.mStage.getEvent().key);
						}
					} else if(matches == null) {
						matches = ScoutingApp.getDatabase().getMatches2018ForEvent(ScoutingApp.mStage.getEvent().key);
						if(matches != null) {
							System.out.println("***NEXT: AWARDS***");
							ScoutingApp.getRequesterThread().addRequestAwardsAtEvent(ScoutingApp.mStage.getEvent().key);
						}
					} else if(ScoutingApp.getDatabase().getAwardsAtEvent(ScoutingApp.mStage.getEvent().key) != null) {
						System.out.println("LUL");
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								ScoutingApp.mStage.setState(MatchesStage.State.MAIN);
								end();
							}
						});
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
