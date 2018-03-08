package stages.matches;

import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.yr2018.Match_PowerUp;

public class MainDisplayBPane extends BorderPane {
	private ScrollPane tStandingsSPane;
	
	public MainDisplayBPane() {
		super();
		tStandingsSPane = new ScrollPane();
		this.setTop(tStandingsSPane);
		HBox h = new HBox();
		VBox v = new VBox();
		
		this.setLeft(h);
		this.setRight(v);
	}
	
	public void setContent(Match_PowerUp m) {
		this.setCenter(new MainPreviewBox(m));
	}


}
