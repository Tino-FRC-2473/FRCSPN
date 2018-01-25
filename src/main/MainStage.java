package main;

import constants_and_images.I;
import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainStage extends Stage {
	private Pane pane = new Pane();
	private ClickableButton[][] buttons = new ClickableButton[K.MAIN.N_BTNS_X][K.MAIN.N_BTNS_Y];

	public MainStage() {
		this.setResizable(false);
		this.setTitle("FRCSPN (" + K.VERSION + ")");
		
		buttons[0][0] = new ClickableButton(I.imgs.TEAM_EVENTS_BTN);
		
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[i].length; j++) {
				if(buttons[i][j] == null)
					buttons[i][j] = new ClickableButton(I.imgs.COMING_SOON_BTN);
				pane.getChildren().add(buttons[i][j]);
				buttons[i][j].setX(i*K.MAIN.BTN_SPACE_LEN + K.MAIN.BTN_PAD);
				buttons[i][j].setY(j*K.MAIN.BTN_SPACE_LEN + K.MAIN.BTN_PAD);
			}
		}
		
		pane.setOnMousePressed(new MainStageMouseHandler());
		this.setScene(new Scene(pane, K.MAIN.WIDTH, K.MAIN.HEIGHT));
	}
	
	private class MainStageMouseHandler implements EventHandler<MouseEvent> {
    	@Override
        public void handle(MouseEvent e) {
    		for(ClickableButton[] bArr : buttons) {
    			for(ClickableButton b : bArr) {
    				if(b.contains(e.getX(), e.getY())) {
    					b.onClick();
    					break;
    				}
    			}
    		}
        }
    }
}