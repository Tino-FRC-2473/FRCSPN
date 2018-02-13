package stages.team_events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import general.ScoutingApp;
import general.constants.K;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TeamEventsStage extends Stage {
	private State state;

	private BorderPane root;
	private CenterPane cPane;
	private LeftScrollPane lPane;
	
	private Scene mainScene;
	private LoadingScene loadingScene;
	private LoadingThread loadingThread;

	private HashMap<StringWithColor, ArrayList<Integer>> teams;

	public TeamEventsStage() {
		root = new BorderPane();
		this.setResizable(false);
		this.setTitle("Team Events (FRCSPN)");

		mainScene = new Scene(root, K.TEAM_EVENTS.WIDTH, K.TEAM_EVENTS.HEIGHT);

		cPane = new CenterPane();
		root.setCenter(cPane);

		lPane = new LeftScrollPane();
		root.setLeft(lPane);

		
		loadingScene = new LoadingScene(new Pane());

		teams = new HashMap<StringWithColor, ArrayList<Integer>>();

		state = State.LOADING;
		
		loadLoading();
	}

	public void setState(State s) {
		System.out.println("changing state from " + state + " -> " + s);
		if(!state.equals(s)) {
			if(state.equals(State.VIEWING))
				unloadViewing();
			else if(state.equals(State.EDITING))
				unloadEditing();
			else if(state.equals(State.LOADING))
				unloadLoading();
			
			state = s;
			
			if(state.equals(State.VIEWING))
				loadViewing();
			else if(state.equals(State.EDITING))
				loadEditing();
			else if(state.equals(State.LOADING))
				loadLoading();
		}
	}
	
	public State getState() { return state; }

	public void loadViewing() {
		System.out.println("LOAD VIEWING");
//		for(Map.Entry<StringWithColor, ArrayList<Integer>> entry : teams.entrySet()) {
//			for(Integer i : entry.getValue()) {
//				TeamInfo info = new TeamInfo(i.intValue(), entry.getKey().getString());
//				info.setColor(entry.getKey().getColor());
//				this.cPane.getTeams().add(info);
//			}
//		}
		
//		for(StringWithColor k : teams.keySet()) {
//			System.out.println(k);
//			System.out.println(teams.get(k));
//		}
		lPane.initializeViewing(teams.keySet().toArray(new StringWithColor[]{}));
	}

	private void unloadViewing() {
		
	}

	private void loadEditing() {
		
	}
	
	private void unloadEditing() {
		cPane.saveChanges();
	}

	private void loadLoading() {
		try(BufferedReader br = new BufferedReader(new FileReader("docs/team_list.txt"))) {
			teams = new HashMap<StringWithColor, ArrayList<Integer>>();
			String line;
			StringWithColor key = null;
			String keyStr = "";
			while((line = br.readLine()) != null) {
				if(line.charAt(0) == '*') {
					keyStr = line.substring(1, line.indexOf('*', 1));
					key = new StringWithColor(keyStr, line.substring(keyStr.length() + 3));
					teams.put(key, new ArrayList<Integer>());
				} else {
					ArrayList<Integer> teamNums = splitLineInt(line);
					teams.get(key).addAll(teamNums);
					
					for(int i = 0; i < teamNums.size(); i++) {
						ScoutingApp.getRequesterThread().addRequestEventsForTeamInYear(teamNums.get(i), 2018);
					}
				}
			}
//			System.out.println("# incomplete: " + ScoutingApp.getDatabase().getNumberIncompleteRequests());
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.setScene(loadingScene);
		loadingThread = new LoadingThread(this);
		loadingThread.start();
	}

	private void unloadLoading() {
		this.setScene(mainScene);
	}

	private ArrayList<Integer> splitLineInt(String s) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(s);
		while(st.hasMoreTokens()) {
			arr.add(Integer.parseInt(st.nextToken()));
		}
		return arr;
	}

	public HashMap<StringWithColor, ArrayList<Integer>> getTeams() {
		return teams;
	}

	public ArrayList<Integer> getTeams(StringWithColor strWC) {
		return teams.get(strWC);
	}

	public enum State {
		VIEWING, EDITING, LOADING
	}
}