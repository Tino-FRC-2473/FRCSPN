package stages.matches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import general.ScoutingApp;
import general.constants.K;
import general.images.I;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Event;

public class MatchesStage extends Stage {
	private ArrayList<Event> allEvents;
	private BorderPane root;
	private VBox top;
	private State state;
	
	private HashMap<State, Scene> sceneMap;
	private MLoadingThread loadingThread;
	EventsPane lP;

	
	public MatchesStage() {
		this.setResizable(false);
		this.setTitle("Matches (FRCSPN)");
		
		root = new BorderPane();
		
		top = new VBox();
		top.getChildren().add(new SearchHBox());
		root.setTop(top);
		
		initScenesMap();
		
		this.getIcons().add(I.getInstance().getImg(I.Type.MAIN_ICON));
		
		state = State.LOADING;
		setLoading();
		
		
	}
	
	public void setState(State toSet) {
		System.out.println("changing state from " + state + " -> " + toSet);
		if(!state.equals(toSet)) {
			switch(state) {
			case SELECTING:
				exitSelecting();
				break;
			case LOADING:
				exitLoading();
				break;
			default:
				System.out.println("Unknown previous state: " + state);
				break;
			}
			
			state = toSet;
			
			switch(state) {
			case SELECTING:
				setSelecting();
				break;
			case LOADING:
				setLoading();
				break;
			default:
				System.out.println("Unknown previous state: " + state);
				break;
			}
		}
	}
	
	public State getState() {
		return state;
	}
	
	private void setSelecting() {
		this.setScene(sceneMap.get(State.SELECTING));
		
//		Event tempEvent = new Event();
//		tempEvent.start_date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
//		tempEvent.end_date = tempEvent.start_date;
//		allEvents.add(tempEvent);
//		
//		Collections.sort(allEvents, new Comparator<Event>() {
//			@Override public int compare(Event e1, Event e2) {
//				if(e1.start_date.compareTo(e2.start_date) == 0)
//					return e1.end_date.compareTo(e2.end_date);
//				return e1.start_date.compareTo(e2.start_date);
//			}
//		});
//		allEvents.subList(0, allEvents.indexOf(tempEvent)+1).clear();
//		Event[] events = allEvents.subList(0, Math.min(allEvents.size(), 15)).toArray(new Event[Math.min(allEvents.size(), 15)]);
//		//some amount of suggested events should appear in a top VBox
//		//center pane should have a event selector
//		HBox top = new HBox();
//		for(Event e : events) {
//			top.getChildren().add(new Label(e.key));
//		}
//		root.setTop(top);

//		SuggestionsTab tab = new SuggestionsTab(15);
	}
	
	private void exitSelecting() {
		
	}
	
	private void setLoading() {
		ScoutingApp.getRequesterThread().addRequestEventsInYear(2018);
		this.setScene(sceneMap.get(State.LOADING));
		loadingThread = new MLoadingThread(this, (MLoadingScene)sceneMap.get(State.LOADING));
		loadingThread.start();
	}
	
	private void exitLoading() {
		allEvents = new ArrayList<Event>(Arrays.asList(ScoutingApp.getDatabase().getEventsInYear(2018)));
		
		Collections.sort(allEvents, new Comparator<Event>() {
			@Override public int compare(Event e1, Event e2) {
				if(e1.start_date.compareTo(e2.start_date) == 0)
					return e1.end_date.compareTo(e2.end_date);
				return e1.start_date.compareTo(e2.start_date);
			}
		});
		lP = new EventsPane(allEvents);
		lP.addAllEvents();
		root.setLeft(lP);
	}
	
	private void initScenesMap() {
		sceneMap = new HashMap<State, Scene>();
		sceneMap.put(State.SELECTING, new Scene(root, K.MATCHES.WIDTH, K.MATCHES.HEIGHT));
		sceneMap.put(State.LOADING, new MLoadingScene(new Pane()));
		sceneMap.put(State.MAIN, null/*new Scene(root, K.MATCHES.WIDTH, K.MATCHES.HEIGHT)*/);
	}
	
	public ArrayList<Event> getAllEvents(){
		return allEvents;
	}
	
	public enum State {
		SELECTING, LOADING, MAIN
	}
}
