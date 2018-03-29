package stages.matches;

import java.io.File;

import general.constants.K;
import general.neuralNetwork.NNDatabase.NNResponse;
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
		
		File f = new File("filecss.css");
		this.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
	}

	public void previewTeam(SingleAlliance s) {
		cDisplayPane.previewTeam(s);
	}
	
	public void updateNNView(String[] p, NNResponse resp) { cDisplayPane.updateNNView(p, resp); }
	public void unselectMatch() { lMatchesSPane.unselect(); }
	public void viewStandings() { cDisplayPane.viewStandings(); }
	public void viewBracket() { cDisplayPane.viewBracket(); }
	public void viewAwards() { cDisplayPane.viewAwards(); }

	public void filter() {
		String text = tSearchBox.getText();
		if(text.length() == 0)
			cDisplayPane.viewStandings();
		
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
		t.getChildren().add(tSearchBox);
		if(tSuggestBox.doesExist)
			t.getChildren().add(tSuggestBox);
		root.setTop(t);
		
		cDisplayPane = new MainDisplayPane(matches);
		root.setCenter(cDisplayPane);
	}

	public void preview(MatchesDisplay2018 d) {
		cDisplayPane.viewMatch(d.getMatch());
	}
}