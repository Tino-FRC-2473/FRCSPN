package stages.matches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import general.ScoutingApp;
import general.images.I;
import general.neuralNetwork.NNDatabase.NNResponse;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Event;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MatchesStage extends Stage {
	private State state;
	
	private MLoadingScene loadScene1;
	private SelectingScene selectingScene;
	private MLoadingScene loadScene2;
	private MainScene mainScene;
	
	private Event[] allEvents;
	private Event event;
	private Match_PowerUp[] allMatches;
	
	public void previewTeam(SingleAlliance s) {
		mainScene.previewTeam(s);
	}
	
	public MatchesStage() {
		this.setResizable(false);
		this.setTitle("Matches (FRCSPN)");
		
		loadScene1 = new MLoadingScene(new Pane());
		selectingScene = new SelectingScene(new BorderPane());
		loadScene2 = new MLoadingScene(new Pane());
		mainScene = new MainScene(new BorderPane());
		
		this.getIcons().add(I.getInstance().getImg(I.Type.MAIN_ICON));
		
		state = State.LOADING1;
		setLoading1();
	}
	
	public void updateNNView(String[] p, NNResponse resp) { mainScene.updateNNView(p, resp); }
	public void viewBracket() { mainScene.viewBracket(); }
	public void unselectMatch() { mainScene.unselectMatch(); }
	public void matchesStageFilter() { mainScene.filter(); }
	public void viewStandings() { mainScene.viewStandings(); }
	public void viewAwards() { mainScene.viewAwards(); }
	
	public void setContent(Event event) { selectingScene.setContent(event); }
	public void indicateEventSelected(EventsDisplay d) { selectingScene.indicateSelected(d); }
	public void preview(EventsDisplay d) { selectingScene.preview(d); }
	public void preview(MatchesDisplay2018 d) { mainScene.preview(d); }
	public void filterEvents() { selectingScene.filterEvents(); }
	
	public void selectEvent() {
		if(selectingScene.getSelectedEvent() != null) {
			event = selectingScene.getSelectedEvent();
			this.setState(State.LOADING2);
		}
	}
	
	public MainSideSPane getMainMatchesSPane() {
		return mainScene.getMainMatchesSPane();
	}
	
	public void setState(State toSet) {
		System.out.println("changing state from " + state + " -> " + toSet);
		if(!state.equals(toSet)) {
			switch(state) {
			case LOADING1:		exitLoading1();		break;
			case SELECTING:		exitSelecting();	break;
			case LOADING2:		exitLoading2();		break;
			case MAIN:			exitMain();			break;
			default:			System.out.println("Unknown previous state: " + state); break;
			}
			
			state = toSet;
			
			switch(state) {
			case LOADING1:		setLoading1();		break;
			case SELECTING:		setSelecting();		break;
			case LOADING2:		setLoading2();		break;
			case MAIN:			setMain();			break;
			default:			System.out.println("Unknown new state: " + state); break;
			}
		}
	}
	
	public State getState() { return state; }
	
	public Event getEvent() { 
		return event;
	}
	
	private void setMain() {
		mainScene.initialize(allMatches);
		this.setScene(mainScene);
	}
	
	private void exitMain() {
		
	}
	
	private void setLoading2() {
		ScoutingApp.getRequesterThread().addRequestTeamsAtEvent(event.key);
		this.setScene(loadScene2);
		loadScene2.start();
	}
	
	private void exitLoading2() {
		ArrayList<Match_PowerUp> arr = new ArrayList<Match_PowerUp>(Arrays.asList(ScoutingApp.getDatabase().getMatches2018ForEvent(ScoutingApp.mStage.getEvent().key)));
		
		Collections.sort(arr, new Comparator<Match_PowerUp>() {
			@Override public int compare(Match_PowerUp m1, Match_PowerUp m2) {
				return m1.time - m2.time;
			}
		});
		
		allMatches = arr.toArray(new Match_PowerUp[arr.size()]);
	}
	
	private void setSelecting() {
		selectingScene.initialize(allEvents);
		this.setScene(selectingScene);
	}
	
	private void exitSelecting() {}
	
	private void setLoading1() {
		ScoutingApp.getRequesterThread().addRequestEventsInYear(2018);
		this.setScene(loadScene1);
		loadScene1.start();
	}
	
	private void exitLoading1() {
		ArrayList<Event> arr = new ArrayList<Event>(Arrays.asList(ScoutingApp.getDatabase().getEventsInYear(2018)));
		
		Collections.sort(arr, new Comparator<Event>() {
			@Override public int compare(Event e1, Event e2) {
				if(e1.start_date.compareTo(e2.start_date) == 0)
					return e1.end_date.compareTo(e2.end_date);
				return e1.start_date.compareTo(e2.start_date);
			}
		});
		
		allEvents = arr.toArray(new Event[arr.size()]);
	}
	
	public Event[] getAllEvents(){
		return allEvents;
	}
	
	public Match_PowerUp[] getMatches() {
		return allMatches;
	}
	
	public enum State {
		LOADING1, SELECTING, LOADING2, MAIN
	}
}
