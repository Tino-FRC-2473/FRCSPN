package stages.matches;

import general.constants.K;
import javafx.geometry.HPos;
//import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchDetailsBox extends ScrollPane {

	public int height = 50;
	public int scaleScale = 4;
	public int switchScale = 2;
	public Color force = Color.FIREBRICK;
	public Color levitate = Color.LIMEGREEN;
	public Color boost = Color.BLUE;
	public VBox content;
	private Scene teamP;
	private Scene matchP;
	

	public MainMatchDetailsBox() {
		this.setMinWidth(K.MATCHES.WIDTH-K.MATCHES.LEFT_WIDTH);
		content = new VBox();
		content.setPrefWidth(K.MATCHES.WIDTH-K.MATCHES.LEFT_WIDTH-20);
		this.setContent(content);
	}
	
	private void clearDisplay() { // clear display
		while(!content.getChildren().isEmpty()) {
			content.getChildren().remove(0);
		}
	}
	
	public void previewTeam(SingleAlliance s) { //run by stage
		clearDisplay();
		
	}

	public void display(Match_PowerUp m) {
		clearDisplay();
		HBox nameScoreTeamBox = new HBox(50); //adding labels for match name, score, and team numbers
		nameScoreTeamBox.setAlignment(Pos.CENTER);
		VBox blueTeams = new VBox();
		blueTeams.setAlignment(Pos.CENTER);
		VBox redTeams = new VBox();
		redTeams.setAlignment(Pos.CENTER);
		for (int i = 0; i < m.alliances.blue.team_keys.length; i++) { //adding team numbers
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
			VBox nameScore = new VBox(15); // adding name and score
			nameScore.setAlignment(Pos.CENTER);
			Label name = new Label(parseFromKey(m.key));
			name.setStyle("-fx-font-size: 30;");
			Label score = new Label(m.score_breakdown.blue.totalPoints + " - " + m.score_breakdown.red.totalPoints);
			score.setStyle("-fx-font-size: 36;");
			nameScore.getChildren().addAll(name,score);
			nameScoreTeamBox.getChildren().addAll(blueTeams,nameScore,redTeams);
			
			VBox scaleBox = new VBox(); // adding graphic representing time of scale ownership
			Label scaleLabel = new Label("Scale");
			scaleLabel.setStyle("-fx-font-size: 24;");
			Rectangle scaleRect = new Rectangle((150-m.score_breakdown.blue.autoScaleOwnershipSec-m.score_breakdown.blue.teleopScaleOwnershipSec-
					m.score_breakdown.red.autoScaleOwnershipSec-m.score_breakdown.red.teleopScaleOwnershipSec)*scaleScale,height);
			scaleRect.setStroke(Color.WHITE);
			scaleRect.setFill(Color.WHITE);
			HBox scale = new HBox();
			if (m.score_breakdown.blue.autoScaleOwnershipSec != 0) {
				Rectangle blueAutoScale = new Rectangle(m.score_breakdown.blue.autoScaleOwnershipSec*scaleScale,height-2);
				scale.getChildren().add(blueAutoScale);
				blueAutoScale.setStroke(Color.LIGHTBLUE);
				blueAutoScale.setFill(Color.LIGHTBLUE);
			}
			if (m.score_breakdown.blue.teleopScaleOwnershipSec != 0) {
				Rectangle blueTeleopScale = new Rectangle(m.score_breakdown.blue.teleopScaleOwnershipSec*scaleScale,height-1);
				scale.getChildren().add(blueTeleopScale);
				blueTeleopScale.setFill(Color.BLUE);
				blueTeleopScale.setStroke(Color.BLUE);
			}
			scale.getChildren().add(scaleRect);
			if (m.score_breakdown.red.teleopScaleOwnershipSec != 0) {
				Rectangle redTeleopScale = new Rectangle(m.score_breakdown.red.teleopScaleOwnershipSec*scaleScale,height-1);
				scale.getChildren().add(redTeleopScale);
				redTeleopScale.setFill(Color.RED);
				redTeleopScale.setStroke(Color.RED);
			}		
			if (m.score_breakdown.red.autoScaleOwnershipSec != 0) {
				Rectangle redAutoScale = new Rectangle(m.score_breakdown.red.autoScaleOwnershipSec*scaleScale,height-2);
				scale.getChildren().add(redAutoScale);
				redAutoScale.setStrokeWidth(2);
				redAutoScale.setStroke(Color.PINK);
				redAutoScale.setFill(Color.PINK);
			}
			scale.setAlignment(Pos.CENTER);
			scale.setMaxSize(150*scaleScale, height);
			scale.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
			scaleBox.setAlignment(Pos.CENTER);
			scaleBox.getChildren().addAll(scaleLabel,scale);
			HBox scaleTime = new HBox();
			Label blueScaleTime = new Label((m.score_breakdown.blue.autoScaleOwnershipSec+m.score_breakdown.blue.teleopScaleOwnershipSec)+"");
			blueScaleTime.setTextFill(Color.DARKBLUE);
			blueScaleTime.setStyle("-fx-font-size: 15");
			Label redScaleTime = new Label((m.score_breakdown.red.autoScaleOwnershipSec+m.score_breakdown.red.teleopScaleOwnershipSec)+"");
			redScaleTime.setTextFill(Color.DARKRED);
			redScaleTime.setStyle("-fx-font-size:15");
			scaleTime.getChildren().addAll(blueScaleTime,scaleBox,redScaleTime);
			scaleTime.setAlignment(Pos.CENTER);
			
			VBox blue = new VBox(); // adding graphic representing blue switch ownership
			Label bluesLabel = new Label("Blue Switch");
			bluesLabel.setStyle("-fx-font-size: 24;");
			Rectangle bswitchRect = new Rectangle((150-m.score_breakdown.blue.autoSwitchOwnershipSec-m.score_breakdown.blue.teleopSwitchOwnershipSec)*switchScale,height);
			bswitchRect.setStroke(Color.WHITE);
			bswitchRect.setFill(Color.WHITE);
			
			HBox blueSwitch = new HBox();
			if (m.score_breakdown.blue.autoSwitchOwnershipSec != 0) {
				Rectangle blueAutoSwitch = new Rectangle(m.score_breakdown.blue.autoSwitchOwnershipSec*switchScale,height);
				blueSwitch.getChildren().add(blueAutoSwitch);
				blueAutoSwitch.setStroke(Color.LIGHTBLUE);
				blueAutoSwitch.setFill(Color.LIGHTBLUE);
			}
			if (m.score_breakdown.blue.teleopSwitchOwnershipSec != 0) {
				Rectangle blueTeleopSwitch = new Rectangle(m.score_breakdown.blue.teleopSwitchOwnershipSec*switchScale,height);
				blueSwitch.getChildren().add(blueTeleopSwitch);
				blueTeleopSwitch.setFill(Color.BLUE);
				blueTeleopSwitch.setStroke(Color.BLUE);
			}
			blueSwitch.setAlignment(Pos.CENTER);
			blueSwitch.getChildren().add(bswitchRect);
			blueSwitch.setMaxSize(150*switchScale, height);
			blue.getChildren().addAll(bluesLabel,blueSwitch);
			blue.setAlignment(Pos.CENTER);
			blueSwitch.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
			HBox blueSwitchTime = new HBox();
			Label blueTime = new Label(m.score_breakdown.blue.autoSwitchOwnershipSec+m.score_breakdown.blue.teleopSwitchOwnershipSec+"");
			blueTime.setTextFill(Color.DARKBLUE);
			blueTime.setStyle("-fx-font-size: 15");
			blueTime.setAlignment(Pos.CENTER);
			blueSwitchTime.getChildren().addAll(blueTime,blue);
			blueSwitchTime.setAlignment(Pos.CENTER_LEFT);

			// adding graphic representing red switch ownership
			Rectangle rswitchRect = new Rectangle((150-m.score_breakdown.red.teleopSwitchOwnershipSec-m.score_breakdown.red.autoSwitchOwnershipSec)*switchScale,height);
			rswitchRect.setStroke(Color.WHITE);
			rswitchRect.setFill(Color.WHITE);
			VBox red = new VBox();
			Label redsLabel = new Label("Red Switch");
			redsLabel.setStyle("-fx-font-size: 24;");
			HBox redSwitch = new HBox();
			redSwitch.getChildren().add(rswitchRect);
			if (m.score_breakdown.red.teleopSwitchOwnershipSec != 0) {
				Rectangle redTeleopSwitch = new Rectangle(m.score_breakdown.red.teleopSwitchOwnershipSec*switchScale,height);
				redSwitch.getChildren().add(redTeleopSwitch);
				redTeleopSwitch.setFill(Color.RED);
				redTeleopSwitch.setStroke(Color.RED);
			}
			if (m.score_breakdown.red.autoSwitchOwnershipSec != 0) {
				Rectangle redAutoSwitch = new Rectangle(m.score_breakdown.red.autoSwitchOwnershipSec*switchScale,height);
				redSwitch.getChildren().add(redAutoSwitch);
				redAutoSwitch.setStroke(Color.PINK);
				redAutoSwitch.setFill(Color.PINK);
			}
			redSwitch.setAlignment(Pos.CENTER);
			redSwitch.setMaxSize(150*switchScale, height);
			redSwitch.setStyle("-fx-border-style: solid; -fx-border-width: 2;");
			red.getChildren().addAll(redsLabel,redSwitch);
			red.setAlignment(Pos.CENTER);
			HBox redSwitchTime = new HBox();
			Label redTime = new Label(m.score_breakdown.red.autoSwitchOwnershipSec+m.score_breakdown.red.teleopSwitchOwnershipSec+"");
			redTime.setTextFill(Color.DARKRED);
			redTime.setStyle("-fx-font-size: 15");
			redTime.setAlignment(Pos.CENTER);
			HBox switchBox = new HBox(25);
			redSwitchTime.getChildren().addAll(red,redTime);
			redSwitchTime.setAlignment(Pos.CENTER_RIGHT);
			switchBox.getChildren().addAll(blueSwitchTime,redSwitchTime);
			switchBox.setAlignment(Pos.CENTER);
			
			// creating graphics representing powerups used
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
			
			// creating info box for auto
			GridPane grid = new GridPane();
//			auto.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: gray");
			Label blueRun = new Label(m.score_breakdown.blue.autoRobot1 + "  " + m.score_breakdown.blue.autoRobot2 +
					"  " + m.score_breakdown.blue.autoRobot3);
			blueRun.setStyle("-fx-font-size:14");
			Label redRun = new Label(m.score_breakdown.red.autoRobot1 + "  " + m.score_breakdown.red.autoRobot2 +
					"  " + m.score_breakdown.red.autoRobot3);
				redRun.setStyle("-fx-font-size:14");
			Label autoRun = new Label("Auto Run");
			autoRun.setStyle("-fx-font-size:14");
			
			Label blueOwnPoints = new Label(m.score_breakdown.blue.autoOwnershipPoints+"");
			blueOwnPoints.setStyle("-fx-font-size:14");
			Label redOwnPoints = new Label(m.score_breakdown.red.autoOwnershipPoints+"");
			redOwnPoints.setStyle("-fx-font-size:14");
			Label autoOwnPoints = new Label("Ownership Points");
			autoOwnPoints.setStyle("-fx-font-size:14");
			
			int gridRow = 0;
			Label blueAutoTotal = new Label(m.score_breakdown.blue.autoPoints+"");
			blueAutoTotal.setStyle("-fx-font-size:14; -fx-font-weight: bold");
			Label redAutoTotal = new Label(m.score_breakdown.red.autoPoints+"");
			redAutoTotal.setStyle("-fx-font-size:14; -fx-font-weight:bold");
			Label autoTotal = new Label("Total Auto Points");
			autoTotal.setStyle("-fx-font-size:14; -fx-font-weight: bold");
			
			Label autoLabel = new Label("Auto");
			autoLabel.setStyle("-fx-font-size:20");
			grid.add(autoLabel, 1, gridRow++);
			grid.add(blueRun, 0, gridRow); grid.add(autoRun, 1, gridRow); grid.add(redRun, 2, gridRow++); 
			grid.add(blueOwnPoints, 0, gridRow); grid.add(autoOwnPoints, 1, gridRow); grid.add(redOwnPoints, 2, gridRow++);
			grid.add(blueAutoTotal, 0, gridRow); grid.add(autoTotal, 1, gridRow); grid.add(redAutoTotal, 2, gridRow++);
			
			Label teleopOwnPoints = new Label("Ownership Points");
			teleopOwnPoints.setStyle("-fx-font-size: 14");
			Label blueTeleopOwn = new Label(m.score_breakdown.blue.teleopOwnershipPoints+"");
			blueTeleopOwn.setStyle("-fx-font-size:14");
			Label redTeleopOwn = new Label(m.score_breakdown.red.teleopOwnershipPoints+"");
			redTeleopOwn.setStyle("-fx-font-size:14");
			Label vaultTotalPoints = new Label("Vault Total Points");
			vaultTotalPoints.setStyle("-fx-font-size:14");
			Label blueVault = new Label(m.score_breakdown.blue.vaultPoints+"");
			blueVault.setStyle("-fx-font-size:14");
			Label redVault = new Label(m.score_breakdown.red.vaultPoints+"");
			redVault.setStyle("-fx-font-size:14");
			Label endGame = new Label("Endgame");
			endGame.setStyle("-fx-font-size:14");
			Label blueEndgame = new Label(m.score_breakdown.blue.endgameRobot1 + "  " + m.score_breakdown.blue.endgameRobot2 +
					"  " + m.score_breakdown.blue.endgameRobot3);
			blueEndgame.setStyle("-fx-font-size: 14");
			Label redEndgame = new Label(m.score_breakdown.red.endgameRobot1 + "  " + m.score_breakdown.red.endgameRobot2 +
					"  " + m.score_breakdown.red.endgameRobot3);
			redEndgame.setStyle("-fx-font-size: 14");
			Label endgamePoints = new Label("Endgame Points");
			endgamePoints.setStyle("-fx-font-size:14");
			Label blueEndPoints = new Label(m.score_breakdown.blue.endgamePoints+"");
			blueEndPoints.setStyle("-fx-font-size:14");
			Label redEndPoints = new Label(m.score_breakdown.red.endgamePoints+"");
			redEndPoints.setStyle("-fx-font-size:14");
			Label totalTeleopPoints = new Label("Total Teleop Points");
			totalTeleopPoints.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
			Label blueTeleop = new Label(m.score_breakdown.blue.teleopPoints+"");
			blueTeleop.setStyle("-fx-font-size:14; -fx-font-weight:bold");
			Label redTeleop = new Label(m.score_breakdown.red.teleopPoints+"");
			redTeleop.setStyle("-fx-font-size:14; -fx-font-weight: bold");
			Label teleopLabel = new Label("Teleop");
			teleopLabel.setStyle("-fx-font-size:20;");
			
			grid.add(teleopLabel, 1, gridRow++);
			grid.add(blueTeleopOwn, 0, gridRow); grid.add(teleopOwnPoints, 1, gridRow); grid.add(redTeleopOwn, 2, gridRow++);
			grid.add(blueVault, 0, gridRow); grid.add(vaultTotalPoints, 1, gridRow); grid.add(redVault, 2, gridRow++);
			grid.add(blueEndgame, 0, gridRow); grid.add(endGame, 1, gridRow); grid.add(redEndgame, 2, gridRow++); 
			grid.add(blueEndPoints, 0, gridRow); grid.add(endgamePoints, 1, gridRow); grid.add(redEndPoints, 2, gridRow++);
			grid.add(blueTeleop, 0, gridRow); grid.add(totalTeleopPoints, 1, gridRow); grid.add(redTeleop, 2, gridRow++);
			
			teleopLabel.setAlignment(Pos.CENTER);
			
			Label fouls = new Label("Fouls/Techs Committed");
			fouls.setStyle("-fx-font-size:14");
			Label blueFouls = new Label(m.score_breakdown.blue.foulCount + "  " + m.score_breakdown.blue.techFoulCount);
			blueFouls.setStyle("-fx-font-size:14");
			Label redFouls = new Label(m.score_breakdown.red.foulCount + "  " + m.score_breakdown.red.techFoulCount);
			redFouls.setStyle("-fx-font-size: 14");
			Label foulPoints = new Label("Foul Points");
			foulPoints.setStyle("-fx-font-size:14");
			Label blueFoulPoints = new Label("+" + m.score_breakdown.blue.foulPoints);
			blueFoulPoints.setStyle("-fx-font-size:14");
			Label redFoulPoints = new Label("+" + m.score_breakdown.red.foulPoints);
			redFoulPoints.setStyle("-fx-font-size:14");
			Label adjust = new Label("Adjustments");
			adjust.setStyle("-fx-font-size:14");
			Label blueAdjust = new Label(m.score_breakdown.blue.adjustPoints+"");
			blueAdjust.setStyle("-fx-font-size:14");
			Label redAdjust = new Label(m.score_breakdown.red.adjustPoints+"");
			redAdjust.setStyle("-fx-font-size:14");
			grid.add(blueFouls, 0, gridRow); grid.add(fouls, 1, gridRow); grid.add(redFouls,2,gridRow++);
			grid.add(blueFoulPoints, 0, gridRow); grid.add(foulPoints, 1, gridRow); grid.add(redFoulPoints, 2, gridRow++);
			grid.add(blueAdjust, 0, gridRow); grid.add(adjust, 1, gridRow); grid.add(redAdjust, 2, gridRow++);
			
			for (int i = 0; i < grid.getChildren().size(); i++) {
				GridPane.setHalignment(grid.getChildren().get(i), HPos.CENTER);
			}
			grid.setHgap(20);
			grid.setAlignment(Pos.CENTER);
			
			// adding all nodes to pane
			content.getChildren().addAll(nameScoreTeamBox, scaleTime, switchBox, boosts, grid);
			content.setPadding(K.getInsets());
			content.setSpacing(20);
			content.setAlignment(Pos.TOP_CENTER);
		
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
