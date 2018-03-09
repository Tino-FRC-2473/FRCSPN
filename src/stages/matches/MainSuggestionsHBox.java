package stages.matches;

import java.util.ArrayList;

import general.ScoutingApp;
import general.constants.K;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.matches.yr2018.Match_PowerUp;

public class MainSuggestionsHBox extends HBox{
	public boolean doesExist;
	private ArrayList<Label> labels;
	
	public MainSuggestionsHBox() {
		this.setPadding(K.getInsets());
		this.setSpacing(25);
		this.setAlignment(Pos.CENTER_LEFT);
		doesExist = true;
		labels = new ArrayList<Label>();
	}
	
	public void generateSuggestions() {
		int index = 0;
		for(Match_PowerUp m:getMain().getMatches())
			if(m.score_breakdown != null)
				index++;
		
		if(index>=getMain().getMatches().length||index==0) {
			labels = null;
			doesExist = false;
		} else {
			doesExist = true;
			Label title = new Label("Suggestions: ");
			title.setStyle("-fx-background-color: transparent;-fx-font-size: 24; -fx-font-weight: bold");
			labels.add(title);
			for(int i = Math.max(index-6, 0); i < index; i++) {
				Label l = new Label("Quals " + getMain().getMatches()[i].key.substring(getMain().getMatches()[i].key.length()-2));
				l.setStyle("-fx-background-color: #FFD32A;-fx-font-size: 24; -fx-font-weight: bold; -fx-border-color: black");
				l.setPadding(K.getInsets());
				l.setOnMouseClicked(new onLabelClicked(getMain().getMatches()[i]));
				labels.add(l);
			}
			this.getChildren().addAll(labels);
		}
	}

	private MatchesStage getMain() {
		return ScoutingApp.mStage;
	}
}

class onLabelClicked implements EventHandler<MouseEvent> {
	private Match_PowerUp m;

	public onLabelClicked(Match_PowerUp m) {
		this.m = m;
	}
	
	@Override
	public void handle(MouseEvent event) {
		for(MatchesDisplay2018 md : ScoutingApp.mStage.getMainMatchesSPane().getMatcheDisplays2018()) {
			if(md.getMatch().key.equals(this.m.key)) {
				ScoutingApp.mStage.getMainMatchesSPane().highlight(md);
			}
		}
	}
}