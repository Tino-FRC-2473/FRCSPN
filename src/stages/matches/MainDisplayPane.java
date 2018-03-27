package stages.matches;

import javafx.scene.layout.BorderPane;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainDisplayPane extends BorderPane {
	private MainTTabBox tabBox;
	
	private MainMatchDetailsBox previewBox;
	private MainStandingsBox standingsBox;
	private MainBracketBox bracketBox;
	private MainAwardsPane awardsPane;

	public MainDisplayPane(Match_PowerUp[] matches) {
		super();
		
		tabBox = new MainTTabBox(matches);
		previewBox = new MainMatchDetailsBox();
		standingsBox = new MainStandingsBox();
		bracketBox = new MainBracketBox(matches);
		awardsPane = new MainAwardsPane();
		
		this.setTop(tabBox);
		
		this.viewStandings();
	}

	public void previewTeam(SingleAlliance s) { previewBox.previewTeam(s); }

	public void viewMatch(Match_PowerUp m) {
		this.setCenter(previewBox);
		if(m.score_breakdown != null) {
			previewBox.display(m);
		} else {
			System.out.println("predict");
		}
	}
	
	public void viewStandings() { this.setCenter(standingsBox); }
	public void viewBracket() { this.setCenter(bracketBox); }
	public void viewAwards() { this.setCenter(awardsPane); }
}
