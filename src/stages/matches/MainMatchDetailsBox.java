package stages.matches;

import general.constants.K;
//import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchDetailsBox extends VBox {

	public int height = 100;
	public int scaleOf = 2;
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
		HBox nameScoreTeamBox = new HBox();
		VBox blueTeams = new VBox();
		VBox redTeams = new VBox();
		for (int i = 0; i < m.alliances.blue.team_keys.length; i++) {
			Label l = new Label(m.alliances.blue.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 14;");
			blueTeams.getChildren().add(l);
		}
		for (int i = 0; i < m.alliances.red.team_keys.length; i++) {
			Label l = new Label(m.alliances.red.team_keys[i].substring(3));
			l.setStyle("-fx-font-size: 14;");
			redTeams.getChildren().add(l);
		}
		
		if (m.score_breakdown != null) {
			VBox nameScore = new VBox();
			Label name = new Label(parseFromKey(m.key));
			name.setStyle("-fx-font-size: 24;");
			Label score = new Label(m.score_breakdown.blue.totalPoints + " - " + m.score_breakdown.red.totalPoints);
			score.setStyle("-fx-font-size: 24;");
			nameScore.getChildren().addAll(name,score);
			nameScoreTeamBox.getChildren().addAll(blueTeams,nameScore,redTeams);
			
			StackPane scale = new StackPane();
			Rectangle scaleRect = new Rectangle(150*scaleOf,height);
			scaleRect.setStrokeWidth(3);
			scaleRect.setStroke(Color.BLACK);
			scaleRect.setFill(Color.WHITE);
			HBox blueScale = new HBox();
			if (m.score_breakdown.blue.autoScaleOwnershipSec != 0) {
				Rectangle blueAutoScale = new Rectangle(m.score_breakdown.blue.autoScaleOwnershipSec*scaleOf,height-2);
				blueScale.getChildren().add(blueAutoScale);
				blueAutoScale.setStrokeWidth(2);
				blueAutoScale.setStroke(Color.BLACK);
				blueAutoScale.setFill(Color.BLUE);
			}
			if (m.score_breakdown.blue.teleopScaleOwnershipSec != 0) {
				Rectangle blueTeleopScale = new Rectangle(m.score_breakdown.blue.teleopScaleOwnershipSec*scaleOf,height-1);
				blueScale.getChildren().add(blueTeleopScale);
				blueTeleopScale.setFill(Color.BLUE);
				blueTeleopScale.setStroke(Color.BLUE);
			}
			HBox redScale = new HBox();
			if (m.score_breakdown.red.teleopScaleOwnershipSec != 0) {
				Rectangle redTeleopScale = new Rectangle(m.score_breakdown.red.teleopScaleOwnershipSec*scaleOf,height-1);
				redScale.getChildren().add(redTeleopScale);
				redTeleopScale.setFill(Color.RED);
				redTeleopScale.setStroke(Color.RED);
			}		
			if (m.score_breakdown.red.autoScaleOwnershipSec != 0) {
				Rectangle redAutoScale = new Rectangle(m.score_breakdown.red.autoScaleOwnershipSec*scaleOf,height-2);
				redScale.getChildren().add(redAutoScale);
				redAutoScale.setStrokeWidth(2);
				redAutoScale.setStroke(Color.BLACK);
				redAutoScale.setFill(Color.RED);
			}
			blueScale.setAlignment(Pos.CENTER_LEFT);
			redScale.setAlignment(Pos.CENTER_RIGHT);
			scale.getChildren().addAll(scaleRect, blueScale,redScale);
			
			VBox switchPane = new VBox();

			Rectangle bswitchRect = new Rectangle(150*scaleOf,height);
			bswitchRect.setStrokeWidth(3);
			bswitchRect.setStroke(Color.BLACK);
			bswitchRect.setFill(Color.WHITE);
			
			StackPane blue = new StackPane();
			HBox blueSwitch = new HBox();
			if (m.score_breakdown.blue.autoSwitchOwnershipSec != 0) {
				Rectangle blueAutoSwitch = new Rectangle(m.score_breakdown.blue.autoSwitchOwnershipSec*scaleOf,height);
				blueSwitch.getChildren().add(blueAutoSwitch);
				blueAutoSwitch.setStroke(Color.BLUE);
				blueAutoSwitch.setStrokeWidth(3);
				blueAutoSwitch.setFill(Color.WHITE);
			}
			if (m.score_breakdown.blue.teleopSwitchOwnershipSec != 0) {
				Rectangle blueTeleopSwitch = new Rectangle(m.score_breakdown.blue.teleopSwitchOwnershipSec*scaleOf,height);
				blueSwitch.getChildren().add(blueTeleopSwitch);
				blueTeleopSwitch.setFill(Color.BLUE);
				blueTeleopSwitch.setStroke(Color.BLUE);
			}
			blue.getChildren().add(bswitchRect);
			blue.getChildren().add(blueSwitch);

			Rectangle rswitchRect = new Rectangle(150*scaleOf,height);
			rswitchRect.setStrokeWidth(3);
			rswitchRect.setStroke(Color.BLACK);
			rswitchRect.setFill(Color.WHITE);
			
			HBox redSwitch = new HBox();
			StackPane red = new StackPane();
			if (m.score_breakdown.red.teleopSwitchOwnershipSec != 0) {
				Rectangle redTeleopSwitch = new Rectangle(m.score_breakdown.red.teleopSwitchOwnershipSec*scaleOf,height);
				redSwitch.getChildren().add(redTeleopSwitch);
				redTeleopSwitch.setFill(Color.RED);
				redTeleopSwitch.setStroke(Color.RED);
			}
			if (m.score_breakdown.red.autoSwitchOwnershipSec != 0) {
				Rectangle redAutoSwitch = new Rectangle(m.score_breakdown.red.autoSwitchOwnershipSec*scaleOf,height);
				redSwitch.getChildren().add(redAutoSwitch);
				redAutoSwitch.setStroke(Color.RED);
				redAutoSwitch.setStrokeWidth(3);
				redAutoSwitch.setFill(Color.WHITE);
			}	
			red.getChildren().add(rswitchRect);
			red.getChildren().add(redSwitch);
			
			switchPane.getChildren().addAll(blue,red);

			scale.setMaxSize(150*scaleOf, height);
			switchPane.setMaxSize(150*scaleOf, height);
			
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
			
			HBox boosts = new HBox(50);
			Region r = new Region();
			boosts.getChildren().addAll(blueForce,blueLevitate,blueBoost,r,redBoost,redLevitate,redForce);
			
			getChildren().addAll(nameScoreTeamBox, scale, switchPane, boosts);
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
