package stages.matches;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import general.constants.*;


public class MLoadingScene extends Scene {
	private Pane pane;
	private Circle[] circles;
	private final Color GREY = new Color(0.75, 0.75, 0.75, 1);
	private int i;
	
	@SuppressWarnings("deprecation")
	public MLoadingScene(Pane p) {
		super(p);
		pane = p;
		pane.setPadding(K.getInsets());
		pane.setPrefWidth(K.MATCHES.WIDTH);
		pane.setPrefHeight(K.MATCHES.HEIGHT);
		pane.setStyle("-fx-background-color: #FFFFFF");
		
		Label message = new Label("Loading...");
		pane.getChildren().add(message);
		message.setStyle("-fx-font-size: 42;");
		message.impl_processCSS(true);
		
		message.setLayoutX((K.MATCHES.WIDTH - message.prefWidth(-1))/2);
		message.setLayoutY((K.MATCHES.HEIGHT - message.prefHeight(-1))/2);
		
		circles = new Circle[12];
		for(int i = 1; i <= 12; i++) {
			circles[i-1] = new Circle(0.05 * K.MATCHES.HEIGHT);
			p.getChildren().add(circles[i-1]);
			circles[i-1].setCenterX(K.MATCHES.WIDTH/2 + K.MATCHES.HEIGHT*0.35*Math.sin(Math.toRadians(360.0/12*(12-i))));
			circles[i-1].setCenterY(K.MATCHES.HEIGHT/2 + K.MATCHES.HEIGHT*0.35*Math.cos(Math.toRadians(360.0/12*(12-i))));
		}
		
		i = 0;
		circles[i].setFill(GREY);
	}
	
	public void rotate() {
		circles[i++].setFill(Color.BLACK);
		if(i >= 12) i = 0;
		circles[i].setFill(GREY);
	}
}
