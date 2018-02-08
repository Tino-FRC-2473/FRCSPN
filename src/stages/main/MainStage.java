package stages.main;

import general.constants.K;
import general.images.I;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import other.ClickableButton;

public class MainStage extends Stage {
	private Pane pane = new Pane();
	private ClickableButton[][] buttons = new ClickableButton[K.MAIN.N_BTNS_X][K.MAIN.N_BTNS_Y];

	public MainStage() {
		this.setResizable(false);
		this.setTitle("FRCSPN (" + K.VERSION + ")");

		buttons[0][0] = new ClickableButton(I.Type.TEAM_EVENTS_BTN, I.Type.TEAM_EVENTS_BTN_CLICKED, "TEEEEEAAAAAM");
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[i].length; j++) {
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
		@Override public void handle(MouseEvent e) {
			for(ClickableButton[] bArr : buttons) {
				for(ClickableButton b : bArr) {
					if(b.contains(e.getX(), e.getY()))
						b.onPress();
				}
			}
		}
	}

	private class MainStageOnRelease implements EventHandler<MouseEvent> {
		@Override public void handle(MouseEvent e) {
			for(ClickableButton[] bArr : buttons) {
				for(ClickableButton b : bArr) {
					if(b.contains(e.getX(), e.getY()))
						b.onRelease();
				}
			}
		}
	}
	
	private class MainStageOnMoved implements EventHandler<MouseEvent> {
		private Label label;
		private boolean added;
		
		private MainStageOnMoved() {
			added = false;
		}
		
		@Override
		public void handle(MouseEvent e) {
			boolean breakFromOuterForLoop = false;
			for(int y = 0; y < buttons.length; y++) {
				if(breakFromOuterForLoop)
					break;
				for(ClickableButton b : buttons[y]) {
					if(b.contains(e.getX(), e.getY()) && b.getDesc() == null) {
						break;
					} else if(b.contains(e.getX(), e.getY()) && !added) {
//						System.out.println("description added");
						breakFromOuterForLoop = true;
						label = new Label(b.getDesc());
						pane.getChildren().add(label);
						label.setStyle("-fx-background-color: #eeeeee; -fx-stroke: white; ");
						label.setOpacity(0.99);
						label.setLayoutX(e.getX());
						label.setLayoutY(e.getY());
						added = true;
						break;
					} else if(b.contains(e.getX(), e.getY()) && added) {
//						System.out.println("description moved");
						breakFromOuterForLoop = true;
						label.setLayoutX(e.getX());
						label.setLayoutY(e.getY()-label.getHeight());
						break;
					} else if(added) {
//						System.out.println("description removed");
						breakFromOuterForLoop = true;
						pane.getChildren().remove(label);
						added = false;
						break;
					}
				}
			}
//			System.out.println("END");
		}
	}
}