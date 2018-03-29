package stages.matches;

import general.ScoutingApp;
import general.constants.K;
import gui.BoxPaddingInsets;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.matches.yr2018.Match_PowerUp;

public class MainTTabBox extends HBox {
	private Label standings;
	private Label bracket;
	private Label awards;
	
	public MainTTabBox(Match_PowerUp[] matches) {
		this.setStyle("-fx-background-color: #DDDDDD");
		this.setPadding(K.getInsets(BoxPaddingInsets.OFFSET));
		this.setSpacing(BoxPaddingInsets.OFFSET);
		
		standings = new Label("Standings");
		standings.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 18; -fx-border-color: black");
		standings.setPadding(K.getInsets());
		standings.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				ScoutingApp.mStage.unselectMatch();
				ScoutingApp.mStage.viewStandings();
			}
		});
		this.getChildren().add(standings);
		
		for(Match_PowerUp m : matches) {
			if(!m.comp_level.equals("qm")) {
				bracket = new Label("Playoff Bracket");
				bracket.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 18; -fx-border-color: black");
				bracket.setPadding(K.getInsets());
				bracket.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						ScoutingApp.mStage.unselectMatch();
						ScoutingApp.mStage.viewBracket();
					}
				});
//				this.getChildren().add(bracket);
				break;
			}
		}
		
		awards = null;
		if(ScoutingApp.getDatabase().getAwardsAtEvent(ScoutingApp.mStage.getEvent().key) != null && ScoutingApp.getDatabase().getAwardsAtEvent(ScoutingApp.mStage.getEvent().key).length > 0) {
			awards = new Label("Awards");
			awards.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 18; -fx-border-color: black");
			awards.setPadding(K.getInsets());
			awards.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent e) {
					ScoutingApp.mStage.unselectMatch();
					ScoutingApp.mStage.viewAwards();
				}
			});
			this.getChildren().add(awards);
		}
	}
}
