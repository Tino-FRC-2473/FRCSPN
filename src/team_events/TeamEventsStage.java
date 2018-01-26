package team_events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TeamEventsStage extends Stage {
	private State state = State.NORMAL;
	
	private BorderPane root = new BorderPane();
	
	private LeftScrollPane lPane;
	private CenterPane cPane;
	
	private HashMap<String, ColorWithIntArrayList> teams;

	public TeamEventsStage() {
		setResizable(false);
		setTitle("Team Events (FRCSPN)");
		
		lPane = new LeftScrollPane();
		lPane.setOnMouseClicked(new LClickHandler());
		
		cPane = new CenterPane();
		cPane.setOnMouseClicked(new CClickHandler());
		
		teams = new HashMap<String, ColorWithIntArrayList>();

		loadNormal();
	
		Scene scene = new Scene(root, K.TEAM_EVENTS.WIDTH, K.TEAM_EVENTS.HEIGHT);
		root.setLeft(lPane);
		root.setCenter(cPane);
		
		setScene(scene);
	}
	
	public void setState(State s) {
		System.out.println(state + " -> " + s);
		if(!state.equals(s)) {
			state = s;
			if(state.equals(State.NORMAL)) {
				unloadTeamList();
				loadNormal();
			} else if(state.equals(State.TEAM_LIST)) {
				unloadNormal();
				loadTeamList();
			}
		}
	}
	
	private void unloadTeamList() {
		
	}
	
	private void loadNormal() {
		try(BufferedReader br = new BufferedReader(new FileReader("docs/team_list.txt"))) {
			teams = new HashMap<String, ColorWithIntArrayList>();
			String line;
			String key = null;
			ArrayList<Label> groupLabels = new ArrayList<Label>();
		    while ((line = br.readLine()) != null) {
		    	Label l = new Label();
		    	groupLabels.add(l);
		        if(line.charAt(0) == '*') {
		        	key = line.substring(1, line.indexOf('*', 1));
		        	teams.put(key, new ColorWithIntArrayList(line.substring(key.length()+3)));
		        	l.setText("\n" + key);
		        } else {
		        	teams.get(key).addAll(splitLineInt(line));
		        	l.setText(l.getText() + teams.get(key).get(0));
		        	for (int i = 1; i < teams.get(key).size(); i++) {
		        		l.setText(l.getText() + ", " + teams.get(key).get(i));
		        	}
		        	l.setText(l.getText() + "\n");
		        }
				l.setWrapText(true);
				l.setStyle("-fx-background-color: #FF0000;");
				l.setStyle("-fx-font-size: 20");		        
		    }
		    for (int i = 0; i < groupLabels.size(); i++) {
			    lPane.getVBox().getChildren().add(groupLabels.get(i));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String k : teams.keySet()) {
			System.out.println(teams.get(k));
		}
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
	
	private class LClickHandler implements EventHandler<MouseEvent> {
		@Override public void handle(MouseEvent e) { lPane.handleClick(e); }}
	private class CClickHandler implements EventHandler<MouseEvent> {
		@Override public void handle(MouseEvent e) { cPane.handleClick(e); }}
	
	public enum State {
		NORMAL, TEAM_LIST
	}
}