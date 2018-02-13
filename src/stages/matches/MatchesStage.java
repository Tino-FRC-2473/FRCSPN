package stages.matches;

import java.util.HashMap;

import general.constants.K;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stages.team_events.LoadingScene;

public class MatchesStage extends Stage {
	private BorderPane root;
	private State state;
	
	private HashMap<State, Scene> sceneMap;
	
	public MatchesStage() {
		root = new BorderPane();
		this.setResizable(false);
		this.setTitle("Matches (FRCSPN)");
		
		initScenesMap();
		
		
		state = State.SELECTING;
		loadSelecting();
	}
	
	public void setState(State toSet) {
		System.out.println("changing state from " + state + " -> " + toSet);
		if(!state.equals(toSet)) {
			switch(state) {
			case SELECTING:
				unloadSelecting();
				break;
			default:
				System.out.println("Unknown previous state: " + state);
				break;
			}
			
			state = toSet;
			
			switch(state) {
			case SELECTING:
				loadSelecting();
				break;
			default:
				System.out.println("Unknown previous state: " + state);
				break;
			}
		}
	}
	
	private void loadSelecting() {
		this.setScene(sceneMap.get(State.SELECTING));
	}
	
	private void unloadSelecting() {
		
	}
	
	private void initScenesMap() {
		sceneMap = new HashMap<State, Scene>();
		sceneMap.put(State.SELECTING, new Scene(root, K.MATCHES.WIDTH, K.MATCHES.HEIGHT));
		sceneMap.put(State.LOADING, new LoadingScene(new Pane()));
		sceneMap.put(State.MAIN, null/*new Scene(root, K.MATCHES.WIDTH, K.MATCHES.HEIGHT)*/);
	}
	
	public enum State {
		SELECTING, LOADING, MAIN
	}
}
