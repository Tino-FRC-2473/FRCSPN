package stages.matches;

import general.constants.K;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.matches.yr2018.Match_PowerUp;

public class MainPreviewBox extends VBox {

	public int height = 10;
	public int scaleOf = 1;
	public Color force = Color.FIREBRICK;
	public Color levitate = Color.LIMEGREEN;
	public Color boost = Color.BLUE;
	public Match_PowerUp match;

	public MainPreviewBox(Match_PowerUp m) {
		match = m;
		display();
	}

	public void display() {
		HBox nameScoreTeamBox = new HBox();
		VBox blueTeams = new VBox();
		VBox redTeams = new VBox();
		for (int i = 0; i < match.alliances.blue.team_keys.length; i++) {
			Label l = new Label(match.alliances.blue.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 24;");
			blueTeams.getChildren().add(l);
		}
		for (int i = 0; i < match.alliances.red.team_keys.length; i++) {
			Label l = new Label(match.alliances.red.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 24;");
			redTeams.getChildren().add(l);
		}
		VBox nameScore = new VBox();
		Label name = new Label(match.getName());
		name.setStyle("-fx-font-size: 40;");
		Label score = new Label(match.score_breakdown.blue.totalPoints + " - " + match.score_breakdown.red.totalPoints);
		score.setStyle("-fx-font-size: 48;");
		nameScore.getChildren().addAll(name,score);
		nameScoreTeamBox.getChildren().addAll(blueTeams,nameScore,redTeams);
		nameScoreTeamBox.setAlignment(Pos.CENTER);
	
		Rectangle scaleRect = new Rectangle((150-match.score_breakdown.blue.autoScaleOwnershipSec-match.score_breakdown.blue.teleopScaleOwnershipSec
				-match.score_breakdown.red.teleopScaleOwnershipSec-match.score_breakdown.blue.autoScaleOwnershipSec)*scaleOf,height);
		scaleRect.setStrokeWidth(3);
		scaleRect.setStroke(Color.BLACK);
		scaleRect.setFill(Color.WHITE);
		HBox scale = new HBox();
		if (match.score_breakdown.blue.autoScaleOwnershipSec != 0) {
			Rectangle blueAutoScale = new Rectangle(match.score_breakdown.blue.autoScaleOwnershipSec*scaleOf,height);
			scale.getChildren().add(blueAutoScale);
			blueAutoScale.setStroke(Color.BLACK);
			blueAutoScale.setStrokeWidth(3);
			blueAutoScale.setFill(Color.LIGHTBLUE);
		}
		if (match.score_breakdown.blue.teleopScaleOwnershipSec != 0) {
			Rectangle blueTeleopScale = new Rectangle(match.score_breakdown.blue.teleopScaleOwnershipSec*scaleOf,height);
			scale.getChildren().add(blueTeleopScale);
			blueTeleopScale.setFill(Color.BLUE);
			blueTeleopScale.setStroke(Color.BLACK);
			blueTeleopScale.setStrokeWidth(3);
		}
		scale.getChildren().add(scaleRect);
		if (match.score_breakdown.red.teleopScaleOwnershipSec != 0) {
			Rectangle redTeleopScale = new Rectangle(match.score_breakdown.red.teleopScaleOwnershipSec*scaleOf,height);
			scale.getChildren().add(redTeleopScale);
			redTeleopScale.setFill(Color.RED);
			redTeleopScale.setStroke(Color.BLACK);
			redTeleopScale.setStrokeWidth(3);
		}
		if (match.score_breakdown.red.autoScaleOwnershipSec != 0) {
			Rectangle redAutoScale = new Rectangle(match.score_breakdown.red.autoScaleOwnershipSec*scaleOf,height);
			scale.getChildren().add(redAutoScale);
			redAutoScale.setStroke(Color.BLACK);
			redAutoScale.setStrokeWidth(3);
			redAutoScale.setFill(Color.PINK);
		}
		
		
		StackPane switchPane = new StackPane();
		Rectangle switchRect = new Rectangle(150*scaleOf,height);
		HBox blueSwitch = new HBox();
		switchRect.setStrokeWidth(3);
		switchRect.setStroke(Color.BLACK);
		switchRect.setFill(Color.WHITE);
		if (match.score_breakdown.blue.autoSwitchOwnershipSec != 0) {
			Rectangle blueAutoSwitch = new Rectangle(match.score_breakdown.blue.autoSwitchOwnershipSec*scaleOf,height);
			blueSwitch.getChildren().add(blueAutoSwitch);
			blueAutoSwitch.setStroke(Color.BLUE);
			blueAutoSwitch.setStrokeWidth(3);
			blueAutoSwitch.setFill(Color.WHITE);
		}
		if (match.score_breakdown.blue.teleopSwitchOwnershipSec != 0) {
			Rectangle blueTeleopSwitch = new Rectangle(match.score_breakdown.blue.teleopSwitchOwnershipSec*scaleOf,height);
			blueSwitch.getChildren().add(blueTeleopSwitch);
			blueTeleopSwitch.setFill(Color.BLUE);
			blueTeleopSwitch.setStroke(Color.BLUE);
		}
		blueSwitch.autosize();
		blueSwitch.setAlignment(Pos.CENTER_LEFT);
		HBox redSwitch = new HBox();
		if (match.score_breakdown.red.teleopSwitchOwnershipSec != 0) {
			Rectangle redTeleopSwitch = new Rectangle(match.score_breakdown.red.teleopSwitchOwnershipSec*scaleOf,height);
			redSwitch.getChildren().add(redTeleopSwitch);
			redTeleopSwitch.setFill(Color.RED);
			redTeleopSwitch.setStroke(Color.RED);
		}
		if (match.score_breakdown.red.autoSwitchOwnershipSec != 0) {
			Rectangle redAutoSwitch = new Rectangle(match.score_breakdown.red.autoSwitchOwnershipSec*scaleOf,height);
			redSwitch.getChildren().add(redAutoSwitch);
			redAutoSwitch.setStroke(Color.RED);
			redAutoSwitch.setStrokeWidth(3);
			redAutoSwitch.setFill(Color.RED);
		}	
		redSwitch.autosize();
		redSwitch.setAlignment(Pos.CENTER_RIGHT);
		scale.setMaxSize(150*scaleOf, height);
		switchPane.getChildren().addAll(switchRect,blueSwitch,redSwitch);
		switchPane.autosize();
		
		VBox blueForce = new VBox();
		blueForce.setAlignment(Pos.CENTER);
		blueForce.setSpacing(5);
		blueForce.getChildren().addAll(new Label("F"), createPowerup(match.score_breakdown.blue.vaultForcePlayed,force));
		VBox blueLevitate = new VBox();
		blueLevitate.getChildren().addAll(new Label("L"), createPowerup(match.score_breakdown.blue.vaultLevitatePlayed,levitate));
		VBox blueBoost = new VBox();
		blueBoost.getChildren().addAll(new Label("B"), createPowerup(match.score_breakdown.blue.vaultBoostPlayed,boost));
		
		VBox redForce = new VBox();
		redForce.getChildren().addAll(new Label("F"),createPowerup(match.score_breakdown.red.vaultForcePlayed,force));
		VBox redLevitate = new VBox();
		redLevitate.getChildren().addAll(new Label("L"),createPowerup(match.score_breakdown.red.vaultLevitatePlayed,levitate));
		VBox redBoost = new VBox();
		redBoost.getChildren().addAll(new Label("B"),createPowerup(match.score_breakdown.red.vaultBoostPlayed,boost));
		
		HBox boosts = new HBox(50);
		Region r = new Region();
		boosts.getChildren().addAll(blueForce,blueLevitate,blueBoost,r,redBoost,redLevitate,redForce);
		
		this.getChildren().addAll(nameScoreTeamBox, boosts);
		this.setPadding(K.getInsets());
		this.setAlignment(Pos.CENTER);
		
		//WHAT IS THIS
//		HBox hBox = new HBox();
//		Rectangle rect = new Rectangle(100,50);
//		rect.setFill(Color.WHITE);
//		rect.setStrokeWidth(3);
//		rect.setStroke(Color.BLACK);
//		Rectangle secRect = new Rectangle(50,50);
//		secRect.setFill(Color.WHITE);
//		secRect.setStrokeWidth(3);
//		secRect.setStroke(Color.BLUE);
		
	}
	
	public VBox createPowerup(int l, Color c) {
		VBox box = new VBox();
		box.setStyle("-fx-border-width: 1; -fx-border-color: black;");
		for (int i = 0; i < 2; i++) {
			Rectangle level = new Rectangle(50,14);
			Rectangle space = new Rectangle(50,4);
			level.setStrokeWidth(0);
			space.setStrokeWidth(0);
			level.setFill(Color.WHITE);
			space.setFill(Color.LIGHTGRAY);
			box.getChildren().addAll(level,space);
		}
		Rectangle level = new Rectangle(50,14);
		level.setStrokeWidth(0);
		level.setFill(Color.WHITE);
		box.getChildren().add(level);
		
		switch(l) {			
		case 0: break;
		case 1: ((Rectangle) box.getChildren().get(4)).setFill(c); break;
		case 2: ((Rectangle) box.getChildren().get(2)).setFill(c); break;
		case 3: ((Rectangle) box.getChildren().get(0)).setFill(c); break;
		}
		return box;
	}
}
