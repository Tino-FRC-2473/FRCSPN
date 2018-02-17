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
	private State state;
	
	private HashMap<State, Scene> sceneMap;
	private BorderPane selectingRoot;
	
	private MLoadingThread loadingThread;
	
	private SearchHBox searchBox;
	private SuggestionsSPane suggestionsSPane;
	private EventsVBox lEventsBox;

	
	public MatchesStage() {
		this.setResizable(false);
		this.setTitle("Matches (FRCSPN)");
		
		selectingRoot = new BorderPane();
		
		searchBox = new SearchHBox();
		suggestionsSPane = new SuggestionsSPane(15);
		VBox top = new VBox();
		top.getChildren().addAll(searchBox, suggestionsSPane);
		selectingRoot.setTop(top);
		
		initScenesMap();
		
		this.getIcons().add(I.getInstance().getImg(I.Type.MAIN_ICON));
		
		state = State.LOADING;
		setLoading();
	}
	
	public void filterEvents() {
		lEventsBox.filter(searchBox.getText());
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
		suggestionsSPane.generateSuggestions();
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
		
		lEventsBox = new EventsVBox(allEvents);
		lEventsBox.addAllEvents();
		selectingRoot.setLeft(lEventsBox);
	}
	
	private void initScenesMap() {
		sceneMap = new HashMap<State, Scene>();
		sceneMap.put(State.SELECTING, new Scene(selectingRoot, K.MATCHES.WIDTH, K.MATCHES.HEIGHT));
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
