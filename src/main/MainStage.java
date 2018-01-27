package main;

import constants_and_images.I;
import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainStage extends Stage {
	private Pane pane = new Pane();
	private ClickableButton[][] buttons = new ClickableButton[K.MAIN.N_BTNS_X][K.MAIN.N_BTNS_Y];

	public MainStage() {
		this.setResizable(false);
		this.setTitle("FRCSPN (" + K.VERSION + ")");

		buttons[0][0] = new ClickableButton(I.Type.TEAM_EVENTS_BTN, I.Type.TEAM_EVENTS_BTN_CLICKED, "TEEEEEAAAAAM");
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if (buttons[i][j] == null)
					buttons[i][j] = new ClickableButton(I.Type.COMING_SOON_BTN);
				pane.getChildren().add(buttons[i][j]);
				buttons[i][j].setX(i * K.MAIN.BTN_SPACE_LEN + K.MAIN.BTN_PAD);
				buttons[i][j].setY(j * K.MAIN.BTN_SPACE_LEN + K.MAIN.BTN_PAD);
			}
		}
		pane.setOnMousePressed(new MainStageOnPress());
		pane.setOnMouseReleased(new MainStageOnRelease());
		pane.setOnMouseMoved(new MainStageOnMoved());
		this.setScene(new Scene(pane, K.MAIN.WIDTH, K.MAIN.HEIGHT));
	}

	private class MainStageOnPress implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			for(int i = 0; i < buttons[0].length; i++) {
				for(ClickableButton b : buttons[i]) {
					if (b.contains(e.getX(), e.getY())) {
						b.onClick();
					}
				}
			}
		}
	}

	private class MainStageOnRelease implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			for(int i = 0; i < buttons[0].length; i++) {
				for(ClickableButton b : buttons[i]) {
					if (b.contains(e.getX(), e.getY())) {
						b.onRelease();
					}
				}
			}
		}
	}
	
	private class MainStageOnMoved implements EventHandler<MouseEvent> {
		Label description;
		boolean added = false;
		@Override
		public void handle(MouseEvent e) {
			for(int i = 0; i < buttons[0].length; i++) {
				for(ClickableButton b : buttons[i]) {
					if (b.contains(e.getX(), e.getY()) && !added) {
						System.out.println("description added");
						description = new Label(b.getDesc());
						pane.getChildren().add(description);
						description.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
						description.setLayoutX(e.getX());
						description.setLayoutY(e.getY());
						added = true;
					}else if(b.contains(e.getX(), e.getY()) && added){
						description.setLayoutX(e.getX());
						description.setLayoutY(e.getY());
					}else {
						pane.getChildren().remove(description);
						added = false;
					}
				}
			}
		}
	}
}