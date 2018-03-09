package stages.matches;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainDisplayBPane extends BorderPane {
	private ScrollPane tStandingsSPane;
	private MainPreviewBox mainPreviewBox;

	public MainDisplayBPane() {
		super();
		tStandingsSPane = new ScrollPane();
		this.setTop(tStandingsSPane);
		HBox h = new HBox();
		VBox v = new VBox();

		this.setLeft(h);
		this.setRight(v);
	}

	public void previewTeam(SingleAlliance s) {
		mainPreviewBox.previewTeam(s);
	}

	public void swapDisplayMatchPreview() {
		
	}

	public void setContent(Match_PowerUp m) {
		MainPreviewBox pb = new MainPreviewBox(m);
		mainPreviewBox = pb;
		this.setCenter(pb);
	}
}
