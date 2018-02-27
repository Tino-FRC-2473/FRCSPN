package stages.matches;

import general.ScoutingApp;
import general.constants.K;
import general.images.I.Type;
import gui.BoxPaddingInsets;
import gui.ClickableButton;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class SelectingSearchHBox extends HBox {
	private TextField searchBar;
	private ClickableButton searchButton;
	private ClickableButton selectButton;
	
	public SelectingSearchHBox() {
		this.setPadding(K.getInsets());
		this.setSpacing(BoxPaddingInsets.OFFSET);
		this.setStyle("-fx-background-color: #F0F0F0");
		this.setAlignment(Pos.CENTER_LEFT);
		
		searchBar = new TextField();
		searchBar.setPrefWidth(K.MATCHES.SEARCH_WIDTH_SELECTING);
		searchBar.setPrefHeight(K.MATCHES.SEARCH_HEIGHT - 2*BoxPaddingInsets.OFFSET);
		searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent e) {
		        if(e.getCode() == KeyCode.ENTER) {
		        	ScoutingApp.mStage.filterEvents();
		        }
		    }
		});
		this.getChildren().add(searchBar);
		
		searchButton = new ClickableButton(Type.M_SEARCH_BTN);
		this.getChildren().add(searchButton);
		
		selectButton = new ClickableButton(Type.M_SELECT_BTN);
		this.getChildren().add(selectButton);
	}
	
	public String getText() {
		return searchBar.getText();
	}
}
