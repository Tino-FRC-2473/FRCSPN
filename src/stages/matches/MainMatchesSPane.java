package stages.matches;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchesSPane extends ScrollPane {
	private VBox matches;
	private ArrayList<MatchesDisplay2018> matchList;
	public MainMatchesSPane(Match_PowerUp[] m) {
		matches = new VBox();
		this.setContent(matches);
		matchList = new ArrayList<MatchesDisplay2018>();
		for(int i = 0; i < m.length; i++) {
			matchList.add(new MatchesDisplay2018(m[i]));
		}
	}
	
	public void addAllMatches() {
		matches.getChildren().clear();
		for (MatchesDisplay2018 i : matchList) {
			getChildren().add(i);
		}
	}
	
	public void filter(String s) {
		matches.getChildren().clear();
		for (int i = 0; i < matchList.size();i++) {
			if (matchList.get(i).contains(s)) {
				matches.getChildren().add(matchList.get(i));
			}
		}
	}
}

class MatchesDisplay2018 extends VBox{
	private Label matchName;
	private Label blueScore;
	private Label redScore;
	private Label[] blueAlliance;
	private Label[] redAlliance;
	private Label[] blueRankingPoints;
	private Label[] redRankingPoints;
	private Match_PowerUp match;
	
	public MatchesDisplay2018(Match_PowerUp m) {
		match = m;
		matchName = new Label("Match");
		blueScore = new Label(Integer.toString(m.score_breakdown.blue.totalPoints));
		redScore = new Label(Integer.toString(m.score_breakdown.red.totalPoints));
		
		blueAlliance = new Label[3];
		redAlliance = new Label[3];
		for (int i = 0; i < blueAlliance.length; i++) { blueAlliance[i] = new Label(m.alliances.blue.team_keys[i]); }
		for (int i = 0; i < redAlliance.length; i++) { redAlliance[i] = new Label(m.alliances.red.team_keys[i]);}
		
		blueRankingPoints = new Label[2];
		redRankingPoints = new Label[2];
		if(m.score_breakdown.blue.autoSwitchAtZero)
			blueRankingPoints[0] = new Label("A");
		else
			blueRankingPoints[0] = new Label();
		if((m.score_breakdown.blue.endgameRobot1.equals("Levitate")||m.score_breakdown.blue.endgameRobot1.equals("Climbing"))
				&& (m.score_breakdown.blue.endgameRobot2.equals("Levitate")||m.score_breakdown.blue.endgameRobot2.equals("Climbing"))
				&& (m.score_breakdown.blue.endgameRobot3.equals("Levitate")||m.score_breakdown.blue.endgameRobot3.equals("Climbing")))
		{ 
			blueRankingPoints[1] = new Label();
		} else
			blueRankingPoints[1] = new Label("C");
		
		if ((m.score_breakdown.red.endgameRobot1.equals("Levitate")||m.score_breakdown.red.endgameRobot1.equals("Climbing"))
				&& (m.score_breakdown.red.endgameRobot2.equals("Levitate")||m.score_breakdown.red.endgameRobot2.equals("Climbing"))
				&& (m.score_breakdown.red.endgameRobot3.equals("Levitate")||m.score_breakdown.red.endgameRobot3.equals("Climbing"))) { 
			redRankingPoints[1] = new Label(); }
		else
			redRankingPoints[0] = new Label();
		if(m.score_breakdown.red.endgameRobot1.equals(""))
			redRankingPoints[1] = new Label();
		else
			redRankingPoints[1] = new Label("C");
		
		display();
	}
	
	public void display() {
		this.getChildren().add(matchName);
		HBox alliances = new HBox();
		getChildren().add(alliances);
		VBox blueBox = new VBox();
		VBox redBox = new VBox();
		blueBox.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
		redBox.setStyle("fx-border-style: solid); -fx-border-width: 2;");
		alliances.getChildren().addAll(blueBox,redBox);
		
		HBox blueScorePoint = new HBox();
		blueBox.getChildren().add(blueScorePoint);
		for(int i = 0; i < blueAlliance.length; i++)
			blueBox.getChildren().add(blueAlliance[i]);
		VBox bluePointBox = new VBox();
		bluePointBox.setStyle("-fx-border-style: solid; -fx-border-width:1;");
		blueScorePoint.getChildren().addAll(blueScore, bluePointBox);
		for(int i = 0; i < blueRankingPoints.length; i++) 
			bluePointBox.getChildren().add(blueRankingPoints[i]);
		
		HBox redScorePoint = new HBox();
		redBox.getChildren().add(redScorePoint);
		for(int i = 0; i < redAlliance.length; i++)
			redBox.getChildren().add(redAlliance[i]);
		VBox redPointBox = new VBox();
		redPointBox.setStyle("-fx-border-style: solid; -fx-border-width:1;");
		redScorePoint.getChildren().addAll(redScore, redPointBox);
		for(int i = 0; i < redRankingPoints.length; i++)
			redPointBox.getChildren().add(redRankingPoints[i]);
	}
	
	public boolean contains(String s) {
		return false;
	}
	
	public String getName() {
		return matchName.getText();
	}
	
	public String[] getBlueAlliance() {
		return match.alliances.blue.team_keys;
	}
	
	public String[] getRedAlliance() {
		return match.alliances.red.team_keys;
	}
}
