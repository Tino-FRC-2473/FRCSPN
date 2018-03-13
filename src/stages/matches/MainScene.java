package stages.matches;

import general.constants.K;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainScene extends Scene {
	private BorderPane root;
	private Match_PowerUp[] matches;

	private MainSideSPane lMatchesSPane;
	private MainSearchHBox tSearchBox;
	private MainSuggestionsHBox tSuggestBox;
	private MainDisplayPane cDisplayPane;

	public MainScene(BorderPane p) {
		super(p, K.MATCHES.WIDTH, K.MATCHES.HEIGHT);
		root = p;
		root.setStyle("-fx-background-color: #F0F0F0");
	}

	public void previewTeam(SingleAlliance s) {
		cDisplayPane.previewTeam(s);
	}

	public void filterMatches() {
		lMatchesSPane.filter(tSearchBox.getText());
	}

	public Match_PowerUp[] getMatches() {
		return matches;
	}

	public MainSideSPane getMainMatchesSPane() {
		return lMatchesSPane;
	}

	public void initialize(Match_PowerUp[] arr) {
		matches = arr.clone();

		lMatchesSPane = new MainSideSPane(matches);
		lMatchesSPane.addAllMatches();
		root.setLeft(lMatchesSPane);

		VBox t = new VBox();
		tSearchBox = new MainSearchHBox();
		tSuggestBox = new MainSuggestionsHBox();
		tSuggestBox.generateSuggestions();
		System.out.println("DOESEXIST: " + tSuggestBox.doesExist);
		t.getChildren().addAll(tSearchBox, tSuggestBox);
		root.setTop(t);

		cDisplayPane = new MainDisplayPane();
		root.setCenter(cDisplayPane);
	}

	public void setContent(Match_PowerUp m) {
		cDisplayPane.viewMatch(m);
	}

	public void preview(MatchesDisplay2018 d) {
		cDisplayPane.viewMatch(d.getMatch());
	}
}