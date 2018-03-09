package stages.matches;

import java.util.ArrayList;

import general.ScoutingApp;
import general.constants.K;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchesSPane extends ScrollPane {
	private VBox content;
	private ArrayList<MatchesDisplay2018> matchList;
	private MatchesDisplay2018 selected;
	
	public MatchesDisplay2018 getSelected() {
		return selected;
	}

	public MainMatchesSPane(Match_PowerUp[] arr) {
		selected = null;
		this.setMinWidth(K.MATCHES.LEFT_WIDTH);
		this.setFitToWidth(true);
		content = new VBox();
		this.setContent(content);
		content.setMaxWidth(K.MATCHES.LEFT_WIDTH-15);
		content.setSpacing(3);
		content.setPadding(K.getInsets(3));
		matchList = new ArrayList<MatchesDisplay2018>();
		for(Match_PowerUp m : arr) {
			MatchesDisplay2018 display = new MatchesDisplay2018(m);
			matchList.add(display);
			display.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent e) {
					highlight(display);
				}
			});
		}
	}
	
	public void addAllMatches() {
		content.getChildren().clear();
		for(MatchesDisplay2018 d : matchList)
			content.getChildren().add(d);
	}
	
	public void filter(String s) {
		boolean prev = false;
		content.getChildren().clear();
		for(MatchesDisplay2018 m : matchList)
			if(m.contains(s))
				if(!prev) {
					prev = true;
					ScoutingApp.mStage
				}
				content.getChildren().add(m);
	}
	
	public Match_PowerUp highlight(MatchesDisplay2018 m) {
		if (selected != null) selected.highlight(false);
		for (MatchesDisplay2018 match : matchList) {
			if (m.equals(match)) {
				match.highlight(true);
				selected = match;
				ScoutingApp.mStage.preview(selected);
				return match.getMatch();
			}
		}
		return null;
	}
	
	public ArrayList<MatchesDisplay2018> getMatcheDisplays2018() {
		return matchList;
	}
	
	public Match_PowerUp getSelectedMatch() {
		return selected.getMatch();
	}
}

class MatchesDisplay2018 extends VBox {
	private Label matchName;
	private Label blueScore;
	private Label redScore;
	private Label[] blueAlliance;
	private Label[] redAlliance;
	private Label[] blueRankingPoints;
	private Label[] redRankingPoints;
	private String color;
	private Match_PowerUp match;
	
	public MatchesDisplay2018(Match_PowerUp m) {
		this.setStyle("-fx-background-color: #FFD32A;");
		color = "#FFD32A";
		this.setMaxWidth(K.MATCHES.LEFT_WIDTH-15);
		this.setAlignment(Pos.TOP_CENTER);
		match = m;
		matchName = new Label(parseFromKey(match.key));
		matchName.setStyle("-fx-font-size:22");

		if (m.score_breakdown != null) {
			blueScore = new Label(""+m.score_breakdown.blue.totalPoints);
			redScore = new Label(""+m.score_breakdown.red.totalPoints);
		} else {
			blueScore = new Label("???");
			redScore = new Label("???");
		}
		
		blueScore.setStyle("-fx-font-size: 30;");
		redScore.setStyle("-fx-font-size: 30;");
		
		if(m.score_breakdown != null)
			if(m.score_breakdown.blue.totalPoints > m.score_breakdown.red.totalPoints)
				blueScore.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
			else if(m.score_breakdown.blue.totalPoints < m.score_breakdown.red.totalPoints)
				redScore.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
			
		blueAlliance = new Label[3];
		redAlliance = new Label[3];
		for(int i = 0; i < blueAlliance.length; i++) {
			blueAlliance[i] = new Label(m.alliances.blue.team_keys[i].substring(3));
			blueAlliance[i].setStyle("-fx-font-size: 14;"); }
		for(int i = 0; i < redAlliance.length; i++) {
			redAlliance[i] = new Label(m.alliances.red.team_keys[i].substring(3));
			redAlliance[i].setStyle("-fx-font-size:14;"); }
		boolean quals = matchName.getText().substring(0,5).equals("Quals");
		blueRankingPoints = new Label[2];
		redRankingPoints = new Label[2];
		blueRankingPoints[0] = new Label("A");
		blueRankingPoints[1] = new Label("C");
		redRankingPoints[0] = new Label("A");
		redRankingPoints[1] = new Label("C");
		if(m.score_breakdown != null && quals && m.score_breakdown.blue.autoRunPoints == 15)
			blueRankingPoints[0].setVisible(true);
		else
			blueRankingPoints[0].setVisible(false);
		
		if(m.score_breakdown != null && quals && (m.score_breakdown.blue.endgameRobot1.equals("Levitate")||m.score_breakdown.blue.endgameRobot1.equals("Climbing"))
				&& (m.score_breakdown.blue.endgameRobot2.equals("Levitate")||m.score_breakdown.blue.endgameRobot2.equals("Climbing"))
				&& (m.score_breakdown.blue.endgameRobot3.equals("Levitate")||m.score_breakdown.blue.endgameRobot3.equals("Climbing")))
		{ 
			blueRankingPoints[1].setVisible(true);
		} else
			blueRankingPoints[1].setVisible(false);
		
		if (m.score_breakdown != null && quals && (m.score_breakdown.red.endgameRobot1.equals("Levitate")||m.score_breakdown.red.endgameRobot1.equals("Climbing"))
				&& (m.score_breakdown.red.endgameRobot2.equals("Levitate")||m.score_breakdown.red.endgameRobot2.equals("Climbing"))
				&& (m.score_breakdown.red.endgameRobot3.equals("Levitate")||m.score_breakdown.red.endgameRobot3.equals("Climbing"))) { 
			redRankingPoints[1].setVisible(true); }
		else
			redRankingPoints[1].setVisible(false);
		if(m.score_breakdown != null && quals && m.score_breakdown.red.autoRunPoints == 15)
			redRankingPoints[0].setVisible(true);
		else
			redRankingPoints[0].setVisible(false);
		
		display();
	}
	
	public void display() {
		this.getChildren().add(matchName);
		HBox alliances = new HBox();
		alliances.setMaxWidth(K.MATCHES.LEFT_WIDTH-15);
		alliances.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(alliances);
		VBox blueBox = new VBox();
		VBox redBox = new VBox();
		blueBox.setAlignment(Pos.CENTER);
		redBox.setAlignment(Pos.CENTER);
		blueBox.setPrefWidth(K.MATCHES.LEFT_WIDTH/2-10);
		redBox.setPrefWidth(K.MATCHES.LEFT_WIDTH/2-10);
		blueBox.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-background-color: #DDDEFF;");
		redBox.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-background-color: #FFB9B9;");
		alliances.getChildren().addAll(blueBox, redBox);
		
		BorderPane blueScorePoint = new BorderPane();
		blueBox.getChildren().add(blueScorePoint);
		boolean quals = matchName.getText().substring(0,5).equals("Quals");
		VBox bluePointBox = new VBox();
		if (quals) {
			bluePointBox.setStyle("-fx-border-style: solid; -fx-border-width: 1;");
			blueScorePoint.setRight(bluePointBox);
		}
		blueScorePoint.setCenter(blueScore);
		for(int i = 0; i < blueAlliance.length; i++) {
			blueBox.getChildren().add(blueAlliance[i]); 
			blueAlliance[i].setTranslateX(-5);}
		if (quals) {
			for(int i = 0; i < blueRankingPoints.length; i++) 
				bluePointBox.getChildren().add(blueRankingPoints[i]);
		}		
		BorderPane redScorePoint = new BorderPane();
		VBox redTeams = new VBox();
		redTeams.setAlignment(Pos.CENTER);
		redBox.getChildren().add(redScorePoint);
		for(int i = 0; i < redAlliance.length; i++) {
			redTeams.getChildren().add(redAlliance[i]);
			redAlliance[i].setTranslateX(5); }
		redScorePoint.setBottom(redTeams);
		VBox redPointBox = new VBox();
		if (quals) {
			redPointBox.setStyle("-fx-border-style: solid; -fx-border-width:1;");
			redScorePoint.setLeft(redPointBox);
		}
		redScorePoint.setCenter(redScore);
		if (quals) {
			for(int i = 0; i < redRankingPoints.length; i++) 
				redPointBox.getChildren().add(redRankingPoints[i]);
		}
	}
	
	public String nameFromKey(String key) {
		String name = "";
		int firstNumberIdx = -1;
		
		for(int i = 0; i < key.length(); i++) {
			if(firstNumberIdx == -1 && Character.isDigit(key.charAt(i))) {
				firstNumberIdx = i;
				break;
			}
		}
		return name;
	}
	
	public boolean contains(String s) {
		if (getName().toLowerCase().indexOf(s.toLowerCase()) != -1) 
			return true;
		else {
			for (int i = 0; i < getBlueAlliance().length; i++) {
				if (getBlueAlliance()[i].indexOf(s)!= -1)
					return true;
			}
			for (int i = 0; i < getRedAlliance().length; i++) {
				if (getRedAlliance()[i].indexOf(s)!= -1)
					return true;
			}
			return false;
		}
	}
	
	public String getName() {
		return matchName.getText();
	}
	
	public String[] getBlueAlliance() {
		String[] arr = new String[3];
		for (int i = 0; i < match.alliances.blue.team_keys.length; i++) {
			arr[i] = match.alliances.blue.team_keys[i].substring(3);
		}
		return arr;
	}
	
	public String[] getRedAlliance() {
		String[] arr = new String[3];
		for (int i = 0; i < match.alliances.red.team_keys.length; i++) {
			arr[i] = match.alliances.red.team_keys[i].substring(3);
		}
		return arr;
	}
	
	public String parseFromKey(String key) {
		System.out.println("Parsing " + key);
		int i = key.indexOf("_")+1;
		if(key.indexOf("qm", i) != -1) 
			return "Quals " + key.substring(i+2);
		else if (key.indexOf("qf", i) != -1)
			return "Quarters " + key.charAt(i+2) + " Match " + key.charAt(i+4);
		else if (key.indexOf("sf", i) != -1)
			return "Semis " + key.charAt(i+2) + " Match " + key.charAt(i+4);
		else if (key.indexOf("f", i) != -1)
			return "Finals " + key.charAt(i+1) + " Match " + key.charAt(i+3);
		return null;
	}
	
	public void highlight(boolean h) {
		if(h)
			this.setStyle("-fx-border-color: red; -fx-border-width: 7; -fx-background-color: " + color);
		else
			this.setStyle("-fx-border-radius: 0; -fx-background-color: " + color);
	}
	
	public Match_PowerUp getMatch() {
		return match;
	}
	
	public boolean equals(MatchesDisplay2018 other) {
		return this.getMatch().key.equals(other.getMatch().key);
	}
	
	public String toString() {
		return getName();
	}
	
}
