package stages.matches;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import models.matches.yr2018.Match_PowerUp;

public class MainDisplayBPane extends BorderPane {
	private ScrollPane tStandingsSPane;
	
	public MainDisplayBPane() {
		super();
		tStandingsSPane = new ScrollPane();
		this.setTop(tStandingsSPane);
		
	}
	
	public void setContent(Match_PowerUp m) {
		this.setCenter(new MainPreviewBox(m));
	}


}
