package stages.team_events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import general.constants.K;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TeamEventsStage extends Stage {
	private State state = State.NORMAL;

	private BorderPane root;
	private CenterPane cPane;
	private LeftScrollPane lPane;

	private HashMap<StringWithColor, ArrayList<Integer>> teams;

	public TeamEventsStage() {
		root = new BorderPane();
		setResizable(false);
		setTitle("Team Events (FRCSPN)");

		Scene scene = new Scene(root, K.TEAM_EVENTS.WIDTH, K.TEAM_EVENTS.HEIGHT);

		cPane = new CenterPane();
		cPane.setOnMouseClicked(new CClickHandler());
		root.setCenter(cPane);

		lPane = new LeftScrollPane();
		lPane.setOnMouseClicked(new LClickHandler());

		teams = new HashMap<StringWithColor, ArrayList<Integer>>();

		loadNormal();

		root.setLeft(lPane);

		setScene(scene);
	}

	public void setState(State s) {
		System.out.println(state + " -> " + s);
		if (!state.equals(s)) {
			state = s;
			if (state.equals(State.NORMAL)) {
				unloadTeamList();
				loadNormal();
			} else if (state.equals(State.TEAM_LIST)) {
				unloadNormal();
				loadTeamList();
			}
		}
	}

	private void unloadTeamList() {

	}

	public void loadNormal() {
		try (BufferedReader br = new BufferedReader(new FileReader("docs/team_list.txt"))) {
			teams = new HashMap<StringWithColor, ArrayList<Integer>>();
			String line;
			StringWithColor key = null;
			String keyStr = "";
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '*') {
					keyStr = line.substring(1, line.indexOf('*', 1));
					key = new StringWithColor(keyStr, line.substring(keyStr.length() + 3));
					teams.put(key, new ArrayList<Integer>());
				} else {
					ArrayList<Integer> teamNums = splitLineInt(line);
					teams.get(key).addAll(teamNums);
					for (Integer i : teamNums) {
						TeamInfo info = new TeamInfo(i.intValue(), keyStr);
						info.setColor(key.getColor());
						this.cPane.getTeams().add(info);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (StringWithColor k : teams.keySet()) {
			System.out.println(k);
			System.out.println(teams.get(k));
		}
		lPane.update(teams.keySet().toArray(new StringWithColor[] {}));
	}

	private void unloadNormal() {

	}

	private void loadTeamList() {

	}

	private ArrayList<Integer> splitLineInt(String s) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
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

	private class LClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			lPane.handleClick(e);
		}
	}

	private class CClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			cPane.handleClick(e);
		}
	}

	public enum State {
		NORMAL, TEAM_LIST
	}
}