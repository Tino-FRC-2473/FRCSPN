package stages.matches;

import general.constants.K;
import general.images.I;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
//import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchDetailsBox extends ScrollPane {

	public int height = 50;
	public double scaleScale = 4.25;
	public double switchScale = 2;
	public String force = "FIREBRICK";
	public String levitate = "LIMEGREEN";
	public String boost = "BLUE";
	public VBox content;

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
		HBox nameScoreTeamBox = new HBox(75); //adding labels for match name, score, and team numbers
		nameScoreTeamBox.setAlignment(Pos.CENTER);
		nameScoreTeamBox.setPadding(new Insets(15,0,0,0));
		VBox blueTeams = new VBox();
		blueTeams.setPrefWidth(175);
		blueTeams.setAlignment(Pos.CENTER);
		blueTeams.setStyle("-fx-background-color: #DDDEFF;");
		VBox redTeams = new VBox();
		redTeams.setAlignment(Pos.CENTER);
		redTeams.setPrefWidth(175);
		redTeams.setStyle("-fx-background-color: #FFB9B9;");
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
			int time = m.score_breakdown.blue.autoScaleOwnershipSec+m.score_breakdown.blue.teleopScaleOwnershipSec;
			Label blueScaleTime;
			if (time<10) blueScaleTime = new Label("\u00A0\u00A0\u00A0"+time+"\u00A0");
			else if (time<100) blueScaleTime = new Label("\u00A0\u00A0"+time+"\u00A0");
			else blueScaleTime = new Label(time+"\u00A0");
			blueScaleTime.setTextFill(Color.DARKBLUE);
			blueScaleTime.setStyle("-fx-font-size: 15");
			time = m.score_breakdown.red.autoScaleOwnershipSec+m.score_breakdown.red.teleopScaleOwnershipSec;
			Label redScaleTime;
			if (time<10) redScaleTime = new Label("\u00A0"+time+"\u00A0\u00A0\u00A0");
			else if (time<100) redScaleTime = new Label("\u00A0"+time+"\u00A0\u00A0");
			else redScaleTime = new Label("\u00A0"+time);
			redScaleTime.setTextFill(Color.DARKRED);
			redScaleTime.setStyle("-fx-font-size:15");
			scaleTime.getChildren().addAll(blueScaleTime,scaleBox,redScaleTime);
			scaleTime.setAlignment(Pos.CENTER);
			blueScaleTime.setTranslateY(17);
			redScaleTime.setTranslateY(17);
			
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
			time = m.score_breakdown.blue.autoSwitchOwnershipSec+m.score_breakdown.blue.teleopSwitchOwnershipSec;
			Label blueTime;
			if (time<10) blueTime = new Label("\u00A0\u00A0\u00A0"+time+"\u00A0");
			else if (time<100) blueTime = new Label("\u00A0\u00A0"+time+"\u00A0");
			else blueTime = new Label(time+"\u00A0");
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
			time = m.score_breakdown.red.autoSwitchOwnershipSec+m.score_breakdown.red.teleopSwitchOwnershipSec;
			Label redTime;
			if (time<10) redTime = new Label("\u00A0"+time+"\u00A0\u00A0\u00A0");
			else if (time<100) redTime = new Label("\u00A0"+time+"\u00A0\u00A0");
			else redTime = new Label("\u00A0"+time);
			redTime.setTextFill(Color.DARKRED);
			redTime.setStyle("-fx-font-size: 15");
			redTime.setAlignment(Pos.CENTER);
			HBox switchBox = new HBox(36);
			redSwitchTime.getChildren().addAll(red,redTime);
			redSwitchTime.setAlignment(Pos.CENTER_RIGHT);
			switchBox.getChildren().addAll(blueSwitchTime,redSwitchTime);
			switchBox.setAlignment(Pos.CENTER);
			blueTime.setTranslateY(17);
			redTime.setTranslateY(17);
			
			// creating graphics representing powerups used
			ImageView bb = new ImageView(I.getInstance().getImg(I.Type.BOOST));
			bb.setStyle("-fx-spacing: 20;");
			ImageView bl = new ImageView(I.getInstance().getImg(I.Type.LEVITATE));
			bl.setStyle("-fx-spacing: 20;");
			ImageView bf = new ImageView(I.getInstance().getImg(I.Type.FORCE));
			bf.setStyle("-fx-spacing: 20;");
			ImageView rb = new ImageView(I.getInstance().getImg(I.Type.BOOST));
			rb.setStyle("-fx-spacing: 20;");
			ImageView rl = new ImageView(I.getInstance().getImg(I.Type.LEVITATE));
			rl.setStyle("-fx-spacing: 20;");
			ImageView rf = new ImageView(I.getInstance().getImg(I.Type.FORCE));
			rf.setStyle("-fx-spacing: 20;");

			VBox blueForce = new VBox();
			blueForce.setAlignment(Pos.CENTER);
			BorderPane p = new BorderPane(bf);
			p.setPadding(new Insets(10, 0, 10, 0));
			blueForce.getChildren().addAll(new Label("F"), p, createPowerup(m.score_breakdown.blue.vaultForcePlayed,force));
			VBox blueLevitate = new VBox();
			blueLevitate.setAlignment(Pos.CENTER);
			p = new BorderPane(bl);
			p.setPadding(new Insets(10, 0, 10, 0));
			blueLevitate.getChildren().addAll(new Label("L"), p, createPowerup(m.score_breakdown.blue.vaultLevitatePlayed,levitate));
			VBox blueBoost = new VBox();
			blueBoost.setAlignment(Pos.CENTER);
			p = new BorderPane(bb);
			p.setPadding(new Insets(10, 0, 10, 0));
			blueBoost.getChildren().addAll(new Label("B"), p, createPowerup(m.score_breakdown.blue.vaultBoostPlayed,boost));
			
			VBox redForce = new VBox();
			redForce.setAlignment(Pos.CENTER);
			p = new BorderPane(rf);
			p.setPadding(new Insets(10, 0, 10, 0));
			redForce.getChildren().addAll(new Label("F"), p, createPowerup(m.score_breakdown.red.vaultForcePlayed,force));
			VBox redLevitate = new VBox();
			redLevitate.setAlignment(Pos.CENTER);
			p = new BorderPane(rl);
			p.setPadding(new Insets(10, 0, 10, 0));
			redLevitate.getChildren().addAll(new Label("L"), p, createPowerup(m.score_breakdown.red.vaultLevitatePlayed,levitate));
			VBox redBoost = new VBox();
			redBoost.setAlignment(Pos.CENTER);
			p = new BorderPane(rb);
			p.setPadding(new Insets(10, 0, 10, 0));
			redBoost.getChildren().addAll(new Label("B"), p, createPowerup(m.score_breakdown.red.vaultBoostPlayed,boost));
			
			HBox blueBoosts = new HBox(10);
			blueBoosts.getChildren().addAll(blueForce, blueLevitate, blueBoost);
			blueBoosts.setAlignment(Pos.TOP_CENTER);
			HBox redBoosts = new HBox(10);
			redBoosts.getChildren().addAll(redBoost,redLevitate,redForce);
			redBoosts.setAlignment(Pos.TOP_CENTER);
			for (int i = 0; i < blueBoosts.getChildren().size(); i++) {
				((VBox) blueBoosts.getChildren().get(i)).setAlignment(Pos.TOP_CENTER);
			}
			for (int i = 0; i < redBoosts.getChildren().size(); i++) {
				((VBox) redBoosts.getChildren().get(i)).setAlignment(Pos.TOP_CENTER);
			}
			BorderPane boosts = new BorderPane();
			boosts.setLeft(blueBoosts);
			boosts.setRight(redBoosts);
			BorderPane.setAlignment(blueBoosts, Pos.TOP_CENTER);
			BorderPane.setAlignment(redBoosts, Pos.TOP_CENTER);
			
			// creating info box for auto
			GridPane grid = new GridPane();
//			auto.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: gray");
			Label blueRun = new Label(autoState(m.score_breakdown.blue.autoRobot1) + " \u00B7 " + autoState(m.score_breakdown.blue.autoRobot2) +
					" \u00B7 " + autoState(m.score_breakdown.blue.autoRobot3));
			blueRun.setStyle("-fx-font-size:14");
			Label redRun = new Label(autoState(m.score_breakdown.red.autoRobot1) + " \u00B7 " + autoState(m.score_breakdown.red.autoRobot2) +
					" \u00B7 " + autoState(m.score_breakdown.red.autoRobot3));
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
			autoLabel.setStyle("-fx-font-size:5");
			autoLabel.setVisible(false);
//			grid.add(autoLabel, 1, gridRow++);
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
			Label blueEndgame = new Label(m.score_breakdown.blue.endgameRobot1 + " \u00B7 " + m.score_breakdown.blue.endgameRobot2 +
					" \u00B7 " + m.score_breakdown.blue.endgameRobot3);
			blueEndgame.setStyle("-fx-font-size: 14");
			Label redEndgame = new Label(m.score_breakdown.red.endgameRobot1 + " \u00B7 " + m.score_breakdown.red.endgameRobot2 +
					" \u00B7 " + m.score_breakdown.red.endgameRobot3);
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
			teleopLabel.setStyle("-fx-font-size:5;");
			teleopLabel.setVisible(false);
			
			grid.add(teleopLabel, 1, gridRow++);
			grid.add(blueTeleopOwn, 0, gridRow); grid.add(teleopOwnPoints, 1, gridRow); grid.add(redTeleopOwn, 2, gridRow++);
			grid.add(blueVault, 0, gridRow); grid.add(vaultTotalPoints, 1, gridRow); grid.add(redVault, 2, gridRow++);
			grid.add(blueEndgame, 0, gridRow); grid.add(endGame, 1, gridRow); grid.add(redEndgame, 2, gridRow++); 
			grid.add(blueEndPoints, 0, gridRow); grid.add(endgamePoints, 1, gridRow); grid.add(redEndPoints, 2, gridRow++);
			grid.add(blueTeleop, 0, gridRow); grid.add(totalTeleopPoints, 1, gridRow); grid.add(redTeleop, 2, gridRow++);
			
			teleopLabel.setAlignment(Pos.CENTER);
			
			Label fouls = new Label("Fouls/Techs Committed");
			fouls.setStyle("-fx-font-size:14");
			Label blueFouls = new Label(m.score_breakdown.blue.foulCount + " \u00B7 " + m.score_breakdown.blue.techFoulCount);
			blueFouls.setStyle("-fx-font-size:14");
			Label redFouls = new Label(m.score_breakdown.red.foulCount + " \u00B7 " + m.score_breakdown.red.techFoulCount);
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
			Label foulLabel = new Label("Fouls");
			foulLabel.setStyle("-fx-font-size:5");
			foulLabel.setVisible(false);
			grid.add(foulLabel, 1, gridRow++);
			grid.add(blueFouls, 0, gridRow); grid.add(fouls, 1, gridRow); grid.add(redFouls,2,gridRow++);
			grid.add(blueFoulPoints, 0, gridRow); grid.add(foulPoints, 1, gridRow); grid.add(redFoulPoints, 2, gridRow++);
			grid.add(blueAdjust, 0, gridRow); grid.add(adjust, 1, gridRow); grid.add(redAdjust, 2, gridRow++);
			
			for (int i = 0; i < grid.getChildren().size(); i++) {
				GridPane.setHalignment(grid.getChildren().get(i), HPos.CENTER);
			}
			grid.setAlignment(Pos.CENTER);
			grid.getColumnConstraints().addAll(new ColumnConstraints(170),new ColumnConstraints(170),new ColumnConstraints(170));
			for (int i = 0; i < gridRow; i++) {
				if (i == GridPane.getRowIndex(foulLabel)||i==GridPane.getRowIndex(teleopLabel)) grid.getRowConstraints().add(new RowConstraints(5));
				else grid.getRowConstraints().add(new RowConstraints(25));
			}
			grid.setStyle("-fx-grid-lines-visible: true; -fx-stroke: #CFCFCF");
			grid.setPadding(new Insets(15,0,0,0));
			boosts.setCenter(grid);
			boosts.setPadding(K.getInsets());
			// adding all nodes to pane
			content.getChildren().addAll(nameScoreTeamBox, scaleTime, switchBox, boosts);
			content.setSpacing(35);
			content.setAlignment(Pos.TOP_CENTER);
		
		}
	}
	
	public VBox createPowerup(int l, String c) {
		VBox box = new VBox();
		box.setStyle("-fx-border-width: 1; -fx-border-color: black;");
		for (int i = 0; i < 2; i++) {
			Label level = new Label();
			level.resize(50,14);
			Rectangle space = new Rectangle(50,4);
			space.setStrokeWidth(0);
			space.setFill(Color.LIGHTGRAY);
			box.getChildren().addAll(level,space);
		}
		Label level = new Label();
		level.resize(50,14);
		box.getChildren().add(level);

		((Label) box.getChildren().get(4)).setText(" Level 1 ");
		((Label) box.getChildren().get(2)).setText(" Level 2 ");
		((Label) box.getChildren().get(0)).setText(" Level 3 ");
		box.setAlignment(Pos.CENTER);
		
		if (l >= 1) {
			((Label) box.getChildren().get(4)).setStyle("-fx-background-color: " + c + ";");
			((Label) box.getChildren().get(4)).setTextFill(Color.WHITE);			
		}
		if (l >= 2) {
			((Label) box.getChildren().get(2)).setStyle("-fx-background-color: " + c + ";");
			((Label) box.getChildren().get(2)).setTextFill(Color.WHITE);
		}
		if (l >= 3) {
			((Label) box.getChildren().get(0)).setStyle("-fx-background-color: " + c + ";");
			((Label) box.getChildren().get(0)).setTextFill(Color.WHITE);
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
	
	public String autoState(String str) {
		if (str.equals("AutoRun")) return "\u2714";
		else return "\u2715";
	}
}
