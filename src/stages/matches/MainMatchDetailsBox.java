package stages.matches;

import general.constants.K;
//import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchDetailsBox extends VBox {

	public int height = 50;
	public int scaleOf = 3;
	public Color force = Color.FIREBRICK;
	public Color levitate = Color.LIMEGREEN;
	public Color boost = Color.BLUE;
	
	private Scene teamP;
	private Scene matchP;
	

	public MainMatchDetailsBox() {
		
	}
	
	private void clearDisplay() {
		while(!this.getChildren().isEmpty()) {
			this.getChildren().remove(0);
		}
	}
	
	public void previewTeam(SingleAlliance s) {
		clearDisplay();
		
	}

	public void display(Match_PowerUp m) {
		clearDisplay();
		HBox nameScoreTeamBox = new HBox(50);
		nameScoreTeamBox.setAlignment(Pos.CENTER);
		VBox blueTeams = new VBox();
		blueTeams.setAlignment(Pos.CENTER_LEFT);
		VBox redTeams = new VBox();
		redTeams.setAlignment(Pos.CENTER_RIGHT);
		for (int i = 0; i < m.alliances.blue.team_keys.length; i++) {
			Label l = new Label(m.alliances.blue.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 24;");
			blueTeams.getChildren().add(l);
		}
		for (int i = 0; i < m.alliances.red.team_keys.length; i++) {
			Label l = new Label(m.alliances.red.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 24;");
			redTeams.getChildren().add(l);
		}
		
		if (m.score_breakdown != null) {
			VBox nameScore = new VBox(15);
			nameScore.setAlignment(Pos.CENTER);
			Label name = new Label(parseFromKey(m.key));
			name.setStyle("-fx-font-size: 30;");
			Label score = new Label(m.score_breakdown.blue.totalPoints + " - " + m.score_breakdown.red.totalPoints);
			score.setStyle("-fx-font-size: 36;");
			nameScore.getChildren().addAll(name,score);
			nameScoreTeamBox.getChildren().addAll(blueTeams,nameScore,redTeams);
			
			VBox scaleBox = new VBox();
			Label scaleLabel = new Label("Scale");
			scaleLabel.setStyle("-fx-font-size: 24;");
			Rectangle scaleRect = new Rectangle((150-m.score_breakdown.blue.autoScaleOwnershipSec-m.score_breakdown.blue.teleopScaleOwnershipSec-
					m.score_breakdown.red.autoScaleOwnershipSec-m.score_breakdown.red.teleopScaleOwnershipSec)*scaleOf,height);
			scaleRect.setStroke(Color.WHITE);
			scaleRect.setFill(Color.WHITE);
			HBox scale = new HBox();
			if (m.score_breakdown.blue.autoScaleOwnershipSec != 0) {
				Rectangle blueAutoScale = new Rectangle(m.score_breakdown.blue.autoScaleOwnershipSec*scaleOf,height-2);
				scale.getChildren().add(blueAutoScale);
				blueAutoScale.setStroke(Color.LIGHTBLUE);
				blueAutoScale.setFill(Color.LIGHTBLUE);
			}
			if (m.score_breakdown.blue.teleopScaleOwnershipSec != 0) {
				Rectangle blueTeleopScale = new Rectangle(m.score_breakdown.blue.teleopScaleOwnershipSec*scaleOf,height-1);
				scale.getChildren().add(blueTeleopScale);
				blueTeleopScale.setFill(Color.BLUE);
				blueTeleopScale.setStroke(Color.BLUE);
			}
			scale.getChildren().add(scaleRect);
			if (m.score_breakdown.red.teleopScaleOwnershipSec != 0) {
				Rectangle redTeleopScale = new Rectangle(m.score_breakdown.red.teleopScaleOwnershipSec*scaleOf,height-1);
				scale.getChildren().add(redTeleopScale);
				redTeleopScale.setFill(Color.RED);
				redTeleopScale.setStroke(Color.RED);
			}		
			if (m.score_breakdown.red.autoScaleOwnershipSec != 0) {
				Rectangle redAutoScale = new Rectangle(m.score_breakdown.red.autoScaleOwnershipSec*scaleOf,height-2);
				scale.getChildren().add(redAutoScale);
				redAutoScale.setStrokeWidth(2);
				redAutoScale.setStroke(Color.PINK);
				redAutoScale.setFill(Color.PINK);
			}
			scale.setAlignment(Pos.CENTER);
			scale.setMaxSize(150*scaleOf, height);
			scale.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
			scaleBox.setAlignment(Pos.CENTER);
			scaleBox.getChildren().addAll(scaleLabel,scale);
			

			VBox blue = new VBox();
			Label bluesLabel = new Label("Blue Switch");
			bluesLabel.setStyle("-fx-font-size: 24;");
			Rectangle bswitchRect = new Rectangle((150-m.score_breakdown.blue.autoSwitchOwnershipSec-m.score_breakdown.blue.teleopSwitchOwnershipSec)*scaleOf,height);
			bswitchRect.setStroke(Color.WHITE);
			bswitchRect.setFill(Color.WHITE);
			
			HBox blueSwitch = new HBox();
			if (m.score_breakdown.blue.autoSwitchOwnershipSec != 0) {
				Rectangle blueAutoSwitch = new Rectangle(m.score_breakdown.blue.autoSwitchOwnershipSec*scaleOf,height);
				blueSwitch.getChildren().add(blueAutoSwitch);
				blueAutoSwitch.setStroke(Color.LIGHTBLUE);
				blueAutoSwitch.setFill(Color.LIGHTBLUE);
			}
			if (m.score_breakdown.blue.teleopSwitchOwnershipSec != 0) {
				Rectangle blueTeleopSwitch = new Rectangle(m.score_breakdown.blue.teleopSwitchOwnershipSec*scaleOf,height);
				blueSwitch.getChildren().add(blueTeleopSwitch);
				blueTeleopSwitch.setFill(Color.BLUE);
				blueTeleopSwitch.setStroke(Color.BLUE);
			}
			blueSwitch.setAlignment(Pos.CENTER);
			blueSwitch.getChildren().add(bswitchRect);
			blueSwitch.setMaxSize(150*scaleOf, height);
			blue.getChildren().addAll(bluesLabel,blueSwitch);
			blue.setAlignment(Pos.CENTER);
			blueSwitch.setStyle("-fx-border-style: solid; -fx-border-width: 2;");

			Rectangle rswitchRect = new Rectangle((150-m.score_breakdown.red.teleopSwitchOwnershipSec-m.score_breakdown.red.autoSwitchOwnershipSec)*scaleOf,height);
			rswitchRect.setStroke(Color.WHITE);
			rswitchRect.setFill(Color.WHITE);
			VBox red = new VBox();
			Label redsLabel = new Label("Red Switch");
			redsLabel.setStyle("-fx-font-size: 24;");
			HBox redSwitch = new HBox();
			if (m.score_breakdown.red.autoSwitchOwnershipSec != 0) {
				Rectangle redAutoSwitch = new Rectangle(m.score_breakdown.red.autoSwitchOwnershipSec*scaleOf,height);
				redSwitch.getChildren().add(redAutoSwitch);
				redAutoSwitch.setStroke(Color.PINK);
				redAutoSwitch.setFill(Color.PINK);
			}
			if (m.score_breakdown.red.teleopSwitchOwnershipSec != 0) {
				Rectangle redTeleopSwitch = new Rectangle(m.score_breakdown.red.teleopSwitchOwnershipSec*scaleOf,height);
				redSwitch.getChildren().add(redTeleopSwitch);
				redTeleopSwitch.setFill(Color.RED);
				redTeleopSwitch.setStroke(Color.RED);
			}
			redSwitch.setAlignment(Pos.CENTER);
			redSwitch.getChildren().add(rswitchRect);
			redSwitch.setMaxSize(150*scaleOf, height);
			redSwitch.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
			red.getChildren().addAll(redsLabel,redSwitch);
			red.setAlignment(Pos.CENTER);
						
			VBox blueForce = new VBox();
			blueForce.getChildren().addAll(new Label("F"), createPowerup(m.score_breakdown.blue.vaultForcePlayed,force));
			VBox blueLevitate = new VBox();
			blueLevitate.getChildren().addAll(new Label("L"), createPowerup(m.score_breakdown.blue.vaultLevitatePlayed,levitate));
			VBox blueBoost = new VBox();
			blueBoost.getChildren().addAll(new Label("B"), createPowerup(m.score_breakdown.blue.vaultBoostPlayed,boost));
			
			VBox redForce = new VBox();
			redForce.getChildren().addAll(new Label("F"),createPowerup(m.score_breakdown.red.vaultForcePlayed,force));
			VBox redLevitate = new VBox();
			redLevitate.getChildren().addAll(new Label("L"),createPowerup(m.score_breakdown.red.vaultLevitatePlayed,levitate));
			VBox redBoost = new VBox();
			redBoost.getChildren().addAll(new Label("B"),createPowerup(m.score_breakdown.red.vaultBoostPlayed,boost));
			
			HBox blueBoosts = new HBox(50);
			blueBoosts.getChildren().addAll(blueForce, blueLevitate, blueBoost);
			HBox redBoosts = new HBox(50);
			redBoosts.getChildren().addAll(redBoost,redLevitate,redForce);
			BorderPane boosts = new BorderPane();
			boosts.setLeft(blueBoosts);
			boosts.setRight(redBoosts);
			
			getChildren().addAll(nameScoreTeamBox, scaleBox, blue, red, boosts);
			setPadding(K.getInsets());
			setAlignment(Pos.TOP_CENTER);
		}
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
	
	public String parseFromKey(String key) {
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
}
