package stages.matches;

import general.constants.K;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainScene extends Scene {
	private BorderPane root;
	
	public MainScene(BorderPane p) {
		super(p, K.MATCHES.WIDTH, K.MATCHES.HEIGHT);
		root = p;
		root.setStyle("-fx-background-color: #F0F0F0");
		
		root.setCenter(new Pane());
	}

}
