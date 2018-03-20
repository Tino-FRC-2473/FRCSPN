package stages.matches;

import javafx.scene.layout.BorderPane;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainDisplayPane extends BorderPane {
	private MainMatchDetailsBox previewBox;
	private MainStandingsBox standingsBox;
	//private awe;oighawh;ieog tStandingsSPane;

	public MainDisplayPane() {
		super();
		previewBox = new MainMatchDetailsBox();
		standingsBox = new MainStandingsBox();
		//awe;goih;owaeg = new ScrollPane();
		
		this.viewStandings();
	}

	public void previewTeam(SingleAlliance s) {
		previewBox.previewTeam(s);
	}

	public void viewMatch(Match_PowerUp m) {
		this.setCenter(previewBox);
		previewBox.display(m);
	}
	
	public void viewStandings() {
		this.setCenter(standingsBox);
	}
}
