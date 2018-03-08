package stages.matches;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import general.ScoutingApp;
import general.constants.K;
import general.requests.R;
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
		
		public MLoadingThread() {
			alive = true;
		}

		public void run() {
			Team[] teams = null;
			
			HashMap<Team, Event[]> teamEvents = new HashMap<Team, Event[]>();
			boolean teamEventsCompleted = false;
			
			boolean teamEventStatusesCompleted = false;
			
			String[] matchKeys = null;
			
			boolean matchesCompleted = false;
			
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
							System.out.println("***REQUESTING EVENTS/TEAM***");
							for(int i = 0; i < teams.length; i++) {
								ScoutingApp.getRequesterThread().addRequestEventsForTeamInYear(teams[i].getNumber(), 2018);
								teamEvents.put(teams[i], null);
							}
						}
					} else if(!teamEventsCompleted) {
						for(final Iterator<Team> itr = teamEvents.keySet().iterator(); itr.hasNext();) {
							Team t = itr.next();
							if(teamEvents.get(t) == null)
								teamEvents.put(t, ScoutingApp.getDatabase().getEventsForTeamInYear(t.getNumber(), 2018));
						}
						
						boolean foundNull = false;
						for(final Iterator<Event[]> itr = teamEvents.values().iterator(); itr.hasNext();) {
							Event[] events = itr.next();
							if(events == null) {
								foundNull = true;
								break;
							}
						}
						
						if(!foundNull) {
							System.out.println("***NEXT: STATUS/TEAM/EVENT***");
							teamEventsCompleted = true;
							for(final Iterator<Entry<Team, Event[]>> itr = teamEvents.entrySet().iterator(); itr.hasNext();) {
								Entry<Team, Event[]> entry = itr.next();
								for(int i = 0; i < entry.getValue().length; i++)
									ScoutingApp.getRequesterThread().addRequestStatusForTeamAtEvent(entry.getKey().getNumber(), entry.getValue()[i].key);
							}
							
						}
					} else if(!teamEventStatusesCompleted) {
						boolean foundIncomplete = false;
						for(final Iterator<Entry<Team, Event[]>> itr = teamEvents.entrySet().iterator(); itr.hasNext();) {
							Entry<Team, Event[]> entry = itr.next();
							if(foundIncomplete)
								break;
							for(int i = 0; i < entry.getValue().length; i++) {
								if(ScoutingApp.getDatabase().isIncomplete(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, entry.getKey().getNumber(), entry.getValue()[i].key))) {
									foundIncomplete = true;
									break;
								}
							}
						}
						
						if(!foundIncomplete) {
							System.out.println("***NEXT: MATCH KEYS***");
							teamEventStatusesCompleted = true;
							ScoutingApp.getRequesterThread().addRequestMatchKeysForEvent(ScoutingApp.mStage.getEvent().key);
						}
					} else if(matchKeys == null) {
						matchKeys = ScoutingApp.getDatabase().getMatchKeysForEvent(ScoutingApp.mStage.getEvent().key);
						if(matchKeys != null) {
							System.out.println("***NEXT: MATCHES***");
							for(int i = 0; i < matchKeys.length; i++)
								ScoutingApp.getRequesterThread().addRequestMatch(matchKeys[i]);
						}
					} else if(!matchesCompleted) {
						boolean foundNull = false;
						for(int i = 0; i < matchKeys.length; i++) {
							if(ScoutingApp.getDatabase().get2018Match(matchKeys[i]) == null) {
								System.out.println(matchKeys[i] + " not found");
								foundNull = true;
								break;
							}
						}
						
						if(!foundNull) {
							System.out.println("***NEXT: AWARDS***");
							matchesCompleted = true;
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
