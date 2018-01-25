package team_events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import constants_and_images.I;
import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.ClickableButton;

public class TeamEventsStage extends Stage {
	private State state = State.NORMAL;
	
	private BorderPane root = new BorderPane();
	
	private HBox topBox = new HBox();
	private ScrollPane leftSPane = new ScrollPane();
	private ClickableButton toggleButton = new ClickableButton(I.imgs.TE_TEAM_LIST_BTN);
	private HashMap<String, ColorWithIntArrayList> teams = new HashMap<String, ColorWithIntArrayList>();

	public TeamEventsStage() {
		this.setResizable(false);
		this.setTitle("Team Events (FRCSPN)");
		
		topBox.getChildren().add(toggleButton);
		toggleButton.setX(K.TEAM_EVENTS.TEAM_LIST_SPACING);
		toggleButton.setY(K.TEAM_EVENTS.TEAM_LIST_SPACING);
		
		leftSPane.setStyle("-fx-background-color: #CCCCCC; -fx-font-size: 20px");
		VBox v = new VBox();
		GridPane r = new GridPane();
		v.setPrefSize(K.TEAM_EVENTS.WIDTH/4.0, K.TEAM_EVENTS.HEIGHT);
		r.setPrefSize(K.TEAM_EVENTS.WIDTH*3/4.0, K.TEAM_EVENTS.HEIGHT);
		r.setStyle("-fx-background-color: #FFFFFF");
		Label label = new Label("254");
		label.setStyle("-fx-background-color: #FF0000;");
		v.getChildren().add(label);
		leftSPane.setContent(v);
		getTeamInfo(Integer.parseInt(label.getText()));
	
		loadNormal();
	
		Scene scene = new Scene(root, K.TEAM_EVENTS.WIDTH, K.TEAM_EVENTS.HEIGHT);
		root.setTop(topBox);
		root.setLeft(leftSPane);
		root.setCenter(r);
		
		this.setScene(scene);
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
		    while ((line = br.readLine()) != null) {
		        if(line.charAt(0) == '*') {
		        	key = line.substring(1, line.indexOf('*', 1));
					
		        	teams.put(key, new ColorWithIntArrayList(splitLineDouble(line.substring(key.length()+3))));
		        } else {
		        	teams.get(key).addAll(splitLineInt(line));
		        }
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
		ArrayList<Integer> spaces = getSpaceIdxs(s);
		
		for(int i = -1; i < spaces.size(); i++) {
			if(i == -1)
				arr.add(Integer.parseInt(s.substring(0, spaces.get(0))));
			else if(i+1 == spaces.size())
				arr.add(Integer.parseInt(s.substring(spaces.get(spaces.size()-1)+1)));
			else
				arr.add(Integer.parseInt(s.substring(spaces.get(i)+1, spaces.get(i+1))));
		}
		
		return arr;
	}
	
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
	
	private void getTeamInfo(int teamNumber) {
		Label name = new Label(Integer.toString(teamNumber));
		name.setStyle("-fx-font-size: 36; -fx-font-color: #346233");
		HBox
	}
	
	public enum State {
		NORMAL, TEAM_LIST
	}
}