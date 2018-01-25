package team_events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import constants_and_images.I;
import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.ClickableButton;

public class TeamEventsStage extends Stage {
	private State state = State.NORMAL;
	
	private BorderPane root = new BorderPane();
	
	//temp
	private HBox topBox = new HBox();
	private ClickableButton toggleButton = new ClickableButton(I.imgs.TE_TEAM_LIST_BTN);
	
	private LeftScrollPane leftSPane = new LeftScrollPane();
	private CenterPane center = new CenterPane();
	
	private HashMap<String, ColorWithIntArrayList> teams = new HashMap<String, ColorWithIntArrayList>();

	public TeamEventsStage() {
		this.setResizable(false);
		this.setTitle("Team Events (FRCSPN)");
		
		topBox.getChildren().add(toggleButton);
		toggleButton.setX(K.TEAM_EVENTS.TEAM_LIST_SPACING);
		toggleButton.setY(K.TEAM_EVENTS.TEAM_LIST_SPACING);

		loadNormal();
	
		Scene scene = new Scene(root, K.TEAM_EVENTS.WIDTH, K.TEAM_EVENTS.HEIGHT);
		root.setTop(topBox);
		root.setLeft(leftSPane);
		root.setCenter(center);
		
		setScene(scene);
		root.setOnMousePressed(new TeamEventsStageMouseHandler());
	}
	
	private class TeamEventsStageMouseHandler implements EventHandler<MouseEvent> {
		@Override
	    public void handle(MouseEvent e) {
			if(toggleButton.contains(e.getX(), e.getY())) {
				toggleButton.onClick();
			}/*
			for(ClickableButton b : buttons) {
				if(b.contains(e.getX(), e.getY())) {
					b.onClick();
					break;
				}
			}*/
		}
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
		try (BufferedReader br = new BufferedReader(new FileReader("docs/team_list.txt"))) {
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
			    leftSPane.getVBox().getChildren().add(groupLabels.get(i));		    	
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
	
	/*
	private ClickableButton getCButton(I.imgs i) {
		for(ClickableButton b : buttons) {
			if(b.getType().equals(i)) {
				return b;
			}
		}
		System.out.println("Button of type " + i + " not found.");
		return null;
	}
	*/
	
	private ArrayList<Integer> splitLineInt(String s) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer(s);
		
		while (st.hasMoreTokens()) {
			arr.add(Integer.parseInt(st.nextToken()));
		}
		
		return arr;
	}
	/*
	private ArrayList<Double> splitLineDouble(String s) {
		ArrayList<Double> arr = new ArrayList<Double>();
		ArrayList<Integer> spaces = getSpaceIdxs(s);
		
		for(int i = -1; i < spaces.size(); i++) {
			if(i == -1)
				arr.add(Double.parseDouble(s.substring(0, spaces.get(0))));
			else if(i+1 == spaces.size())
				arr.add(Double.parseDouble(s.substring(spaces.get(spaces.size()-1)+1)));
			else
				arr.add(Double.parseDouble(s.substring(spaces.get(i)+1, spaces.get(i+1))));
		}
		
		return arr;
	}
	
	private ArrayList<Integer> getSpaceIdxs(String s) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == ' ') {
				arr.add(i);
			}
		}
		
		return arr;
	}
	*/
	
	
	public enum State {
		NORMAL, TEAM_LIST
	}
}