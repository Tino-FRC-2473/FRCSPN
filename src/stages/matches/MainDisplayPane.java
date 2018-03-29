package stages.matches;

import general.neuralNetwork.NNDatabase.NNResponse;
import javafx.scene.layout.BorderPane;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainDisplayPane extends BorderPane {
	private MainTTabBox tabBox;
	
	private MainMatchDetailsBox matchDetailsBox;
	private MainMatchPreviewBox matchPreviewBox;
	private MainStandingsBox standingsBox;
	private MainBracketBox bracketBox;
	private MainAwardsPane awardsPane;

	public MainDisplayPane(Match_PowerUp[] matches) {
		super();
		
		tabBox = new MainTTabBox(matches);
		matchDetailsBox = new MainMatchDetailsBox();
		matchPreviewBox = new MainMatchPreviewBox();
		standingsBox = new MainStandingsBox();
		bracketBox = new MainBracketBox(matches);
		awardsPane = new MainAwardsPane();
		
		this.setTop(tabBox);
		
		this.viewStandings();
	}

	public void previewTeam(SingleAlliance s) { matchDetailsBox.previewTeam(s); }

	public void viewMatch(Match_PowerUp m) {
		if(m.score_breakdown != null) {
			this.setCenter(matchDetailsBox);
			matchDetailsBox.display(m);
		} else {
			this.setCenter(matchPreviewBox);
			matchPreviewBox.display(m);
		}
	}
	
	public void updateNNView(String[] p, NNResponse resp) { matchPreviewBox.updateNNView(p, resp); }
	public void viewStandings() { this.setCenter(standingsBox); }
	public void viewBracket() { this.setCenter(bracketBox); }
	public void viewAwards() { this.setCenter(awardsPane); }
}
