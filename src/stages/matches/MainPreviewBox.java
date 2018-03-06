package stages.matches;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import models.matches.yr2018.Match_PowerUp;

public class MainPreviewBox extends VBox {

	public Match_PowerUp match;

	public MainPreviewBox(Match_PowerUp m) {
		match = m;
	}

	public void display() {
		HBox nameScoreTeamBox = new HBox();
		VBox blueTeams = new VBox();
		VBox redTeams = new VBox();
		for (int i = 0; i < match.alliances.blue.team_keys.length; i++) {
			Label l = new Label(match.alliances.blue.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 14;");
			blueTeams.getChildren().add(l);
		}
		for (int i = 0; i < match.alliances.red.team_keys.length; i++) {
			Label l = new Label(match.alliances.red.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 14;");
			redTeams.getChildren().add(l);
		}
		VBox nameScore = new VBox();
		Label name = new Label(parseFromKey(match.key));
		name.setStyle("-fx-font-size: 24;");
		Label score = new Label(match.score_breakdown.blue.totalPoints + " - " + match.score_breakdown.red.totalPoints);
		score.setStyle("-fx-font-size: 24;");
		nameScore.getChildren().addAll(name,score);
		nameScoreTeamBox.getChildren().addAll(blueTeams,nameScore,redTeams);
	
		StackPane scale = new StackPane();
		Rectangle scaleRect = new Rectangle(300,50);
		HBox blueScale = new HBox();
		Rectangle blueTeleopScale = new Rectangle(match.score_breakdown.blue.teleopScaleOwnershipSec,50);
		Rectangle blueAutoScale = new Rectangle(match.score_breakdown.blue.autoScaleOwnershipSec,50);
		blueScale.getChildren().addAll(blueAutoScale, blueTeleopScale);
		HBox redScale = new HBox();
		Rectangle redTeleopScale = new Rectangle(match.score_breakdown.red.teleopScaleOwnershipSec,50);
		Rectangle redAutoScale = new Rectangle(match.score_breakdown.red.autoScaleOwnershipSec,50);
		redScale.getChildren().addAll(redTeleopScale, redAutoScale);
		scale.getChildren().addAll(scaleRect,blueScale,redScale);
		
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
