package stages.matches;

import java.util.ArrayList;

import general.constants.K;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchesSPane extends ScrollPane {
	private VBox content;
	private ArrayList<MatchesDisplay2018> matchList;
	
	public MainMatchesSPane(Match_PowerUp[] arr) {
		this.setFitToWidth(true);
		content = new VBox();
		this.setContent(content);
		content.setMaxWidth(K.MATCHES.LEFT_WIDTH-10);
		content.setSpacing(3);
		content.setPadding(K.getInsets(3));
		matchList = new ArrayList<MatchesDisplay2018>();
		for(Match_PowerUp m : arr)
			matchList.add(new MatchesDisplay2018(m));
	}
	
	public void addAllMatches() {
		content.getChildren().clear();
		for(MatchesDisplay2018 d : matchList)
			content.getChildren().add(d);
	}
	
	public void filter(String s) {
		content.getChildren().clear();
		for(MatchesDisplay2018 m : matchList)
			if(m.contains(s))
				content.getChildren().add(m);
	}
	
	public void highlight(MatchesDisplay2018 m) {
		
	}
	
	public ArrayList<MatchesDisplay2018> getMatcheDisplays2018() {
		return matchList;
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
	
	private Match_PowerUp match;
	
	public MatchesDisplay2018(Match_PowerUp m) {
		this.setStyle("-fx-background-color: #FFD32A;");
		this.setMaxWidth(K.MATCHES.LEFT_WIDTH-10);
		this.setAlignment(Pos.TOP_CENTER);
		match = m;
		matchName = new Label(parseFromKey(match.key));
		matchName.setStyle("-fx-font-size:22");
		blueScore = new Label(""+m.score_breakdown.blue.totalPoints);
		blueScore.setStyle("-fx-font-size: 24; -fx-font-weight: bold");
		redScore = new Label(""+m.score_breakdown.red.totalPoints);
		redScore.setStyle("-fx-font-size: 24; -fx-font-weight: bold");
		
		blueAlliance = new Label[3];
		redAlliance = new Label[3];
		for(int i = 0; i < blueAlliance.length; i++) {
			blueAlliance[i] = new Label(m.alliances.blue.team_keys[i].substring(3));
			blueAlliance[i].setStyle("-fx-font-size: 14;"); }
		for(int i = 0; i < redAlliance.length; i++) {
			redAlliance[i] = new Label(m.alliances.red.team_keys[i].substring(3));
			redAlliance[i].setStyle("-fx-font-size:14;"); }
		
		blueRankingPoints = new Label[2];
		redRankingPoints = new Label[2];
		blueRankingPoints[0] = new Label("A");
		blueRankingPoints[1] = new Label("C");
		redRankingPoints[0] = new Label("A");
		redRankingPoints[1] = new Label("C");
		if(m.score_breakdown.blue.autoRunPoints == 15)
			blueRankingPoints[0].setVisible(true);
		else
			blueRankingPoints[0].setVisible(false);
		
		if((m.score_breakdown.blue.endgameRobot1.equals("Levitate")||m.score_breakdown.blue.endgameRobot1.equals("Climbing"))
				&& (m.score_breakdown.blue.endgameRobot2.equals("Levitate")||m.score_breakdown.blue.endgameRobot2.equals("Climbing"))
				&& (m.score_breakdown.blue.endgameRobot3.equals("Levitate")||m.score_breakdown.blue.endgameRobot3.equals("Climbing")))
		{ 
			blueRankingPoints[1].setVisible(true);
		} else
			blueRankingPoints[1].setVisible(false);
		
		if ((m.score_breakdown.red.endgameRobot1.equals("Levitate")||m.score_breakdown.red.endgameRobot1.equals("Climbing"))
				&& (m.score_breakdown.red.endgameRobot2.equals("Levitate")||m.score_breakdown.red.endgameRobot2.equals("Climbing"))
				&& (m.score_breakdown.red.endgameRobot3.equals("Levitate")||m.score_breakdown.red.endgameRobot3.equals("Climbing"))) { 
			redRankingPoints[1].setVisible(true); }
		else
			redRankingPoints[1].setVisible(false);
		if(m.score_breakdown.red.autoRunPoints == 15)
			redRankingPoints[0].setVisible(true);
		else
			redRankingPoints[0].setVisible(false);
		
		display();
	}
	
	public void display() {
		this.getChildren().add(matchName);
		HBox alliances = new HBox();
		alliances.setMaxWidth(K.MATCHES.LEFT_WIDTH-10);
		alliances.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(alliances);
		VBox blueBox = new VBox();
		VBox redBox = new VBox();
		blueBox.setAlignment(Pos.CENTER);
		redBox.setAlignment(Pos.CENTER);
		blueBox.setMinWidth(K.MATCHES.LEFT_WIDTH/2-25);
		redBox.setMinWidth(K.MATCHES.LEFT_WIDTH/2-25);
		blueBox.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
		redBox.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
		alliances.getChildren().addAll(blueBox, redBox);
		
		BorderPane blueScorePoint = new BorderPane();
		VBox blueTeams = new VBox();
		blueTeams.setAlignment(Pos.CENTER);
		blueBox.getChildren().add(blueScorePoint);
		for(int i = 0; i < blueAlliance.length; i++) 
			blueTeams.getChildren().add(blueAlliance[i]);
		blueScorePoint.setBottom(blueTeams);
		VBox bluePointBox = new VBox();
		bluePointBox.setStyle("-fx-border-style: solid; -fx-border-width: 1;");
		blueScorePoint.setCenter(blueScore);
		blueScorePoint.setRight(bluePointBox);
		for(int i = 0; i < blueRankingPoints.length; i++) 
			bluePointBox.getChildren().add(blueRankingPoints[i]);
		
		BorderPane redScorePoint = new BorderPane();
		VBox redTeams = new VBox();
		redTeams.setAlignment(Pos.CENTER);
		redBox.getChildren().add(redScorePoint);
		for(int i = 0; i < redAlliance.length; i++) 
			redTeams.getChildren().add(redAlliance[i]);
		redScorePoint.setBottom(redTeams);
		VBox redPointBox = new VBox();
		redPointBox.setStyle("-fx-border-style: solid; -fx-border-width:1;");
		redScorePoint.setCenter(redScore);
		redScorePoint.setLeft(redPointBox);
		for(int i = 0; i < redRankingPoints.length; i++) 
			redPointBox.getChildren().add(redRankingPoints[i]);
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
		return true;
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
		String s = "";
		if (key.indexOf("qm") != -1) 
			s = "Quals " + key.substring(key.indexOf("qm")+2);
		else if (key.indexOf("qf") != -1) 
			s = "Quarters " + key.substring(key.indexOf("qf")+2, key.indexOf("m")) + " Match " + key.substring(key.indexOf("m") + 1);
		else if (key.indexOf("sf") != -1)
			s = "Semis " + key.substring(key.indexOf("sf")+2, key.indexOf("m")) + " Match " + key.substring(key.indexOf("m") + 1);
		else if (key.indexOf("f") != -1)
			s = "Finals " + key.substring(key.indexOf("f")+1, key.indexOf("m")) + " Match " + key.substring(key.indexOf("m") + 1);
		return s;
	}
	
	
}
