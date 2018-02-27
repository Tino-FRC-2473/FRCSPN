package stages.matches;

import java.util.ArrayList;

import general.constants.K;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.matches.yr2018.Match_PowerUp;

public class MatchesSuggestionsHBox extends HBox{
	public boolean doesExist;
	private ArrayList<Label> labels;
	
	public MatchesSuggestionsHBox() {
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
			for(int i = Math.max(index-4, 0); i < index; i++) {
				Label l = new Label(getMain().getMatches()[i].key);
				l.setStyle("-fx-background-color: #FFD32A;-fx-font-size: 24; -fx-font-weight: bold");
				l.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						for(MatchesDisplay2018 m:getMain().getMainMatchesSPane().getMatcheDisplays2018()) {
							
						}
					}
				});
				labels.add(l);
			}
		}
	}

	private MainScene getMain() {
		return new MainScene(new BorderPane());
	}
}
