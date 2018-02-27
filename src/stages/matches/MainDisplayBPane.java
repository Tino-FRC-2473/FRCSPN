package stages.matches;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
}
