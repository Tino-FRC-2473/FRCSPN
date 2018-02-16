package stages.matches;

import general.constants.K;
import general.images.I.Type;
import gui.BoxPaddingInsets;
import gui.ClickableButton;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SearchHBox extends HBox {
	private TextField searchBar;
	private ClickableButton searchButton;
	private ClickableButton selectButton;
	
	public SearchHBox() {
		this.setPadding(K.getInsets());
		this.setSpacing(BoxPaddingInsets.OFFSET);
		this.setStyle("-fx-background-color: #EEEEEE");
		this.setAlignment(Pos.CENTER_LEFT);
		
		searchBar = new TextField();
		searchBar.setPrefWidth(K.MATCHES.SEARCH_WIDTH);
		searchBar.setPrefHeight(K.MATCHES.SEARCH_HEIGHT - 2*BoxPaddingInsets.OFFSET);
		this.getChildren().add(searchBar);
		
		searchButton = new ClickableButton(Type.M_SEARCH_BTN);
		this.getChildren().add(searchButton);
		
		selectButton = new ClickableButton(Type.M_SELECT_BTN);
		this.getChildren().add(selectButton);
	}
	
	public String getText() {
		return searchBar.getText();
	}
	
	private MatchesStage getStage() {
		return ((MatchesStage)((BorderPane) getParent()).getScene().getWindow());
	}
}
