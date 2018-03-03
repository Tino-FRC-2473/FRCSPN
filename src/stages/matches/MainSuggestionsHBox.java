package stages.matches;

import java.util.ArrayList;

import general.ScoutingApp;
import general.constants.K;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.Match;
import models.matches.yr2018.Match_PowerUp;
import stages.main.MainStage;

public class MainSuggestionsHBox extends HBox{
	public boolean doesExist;
	private ArrayList<Label> labels;
	
	public MainSuggestionsHBox() {
		this.setPadding(K.getInsets());
		doesExist = true;
		labels = new ArrayList<Label>();
	}
	
	public void generateSuggestions() {
		int index = 0;
		for(Match_PowerUp m:getMain().getMatches()) {
			if(m.score_breakdown != null) {
				index++;
			}
		}
		if(index>=getMain().getMatches().length) {
			labels = null;
			doesExist = false;
		}else {
			doesExist = true;
			for(int i = Math.max(index-4, 0); i < Math.min(index, getMain().getMatches().length-1); i++) {
				Label l = new Label(getMain().getMatches()[i].key);
				l.setStyle("-fx-background-color: #FFD32A;-fx-font-size: 24; -fx-font-weight: bold");
				l.setOnMouseClicked(new onLabelClicked(getMain().getMatches()[i], getMain()));
				labels.add(l);
			}
		}
	}

	private MatchesStage getMain() {
		return ScoutingApp.mStage;
	}
}
class onLabelClicked implements EventHandler{
	Match_PowerUp m;
	MatchesStage main;
	public onLabelClicked(Match_PowerUp m, MatchesStage main) {
		this.m = m;
		this.main = main;
	}
	@Override
	public void handle(Event event) {
		for(MatchesDisplay2018 m: main.getMainMatchesSPane().getMatcheDisplays2018()) {
			if(m.getMatch().equals(this.m)) {
				main.getMainMatchesSPane().highlight(m);
			}
		}
	}
}