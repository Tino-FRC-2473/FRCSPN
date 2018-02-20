package stages.matches;

import general.constants.K;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import models.matches.yr2018.Match_PowerUp;

public class MainScene extends Scene {
	private BorderPane root;
	private Match_PowerUp[] matches;
	
	private MainMatchesSPane lMatchesSPane;
	
	public MainScene(BorderPane p) {
		super(p, K.MATCHES.WIDTH, K.MATCHES.HEIGHT);
		root = p;
		root.setStyle("-fx-background-color: #F0F0F0");
	}
	
	public void initialize(Match_PowerUp[] arr) {
		matches = arr.clone();
		lMatchesSPane = new MainMatchesSPane(matches);
		lMatchesSPane.addAllMatches();
		
		root.setLeft(lMatchesSPane);
	}
}
