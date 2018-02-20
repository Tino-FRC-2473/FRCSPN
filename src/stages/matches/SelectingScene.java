package stages.matches;

import general.constants.K;
import general.images.I;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;

public class SelectingScene extends Scene {
	private BorderPane root;
	
	private SelectingSearchHBox tSearchBox;
	private SelectingSuggestHBox tSuggestionsHBox;
	private SelectingPreviewBox cPreviewBox;
	private SelectingEventsVBox lEventsBox;
	
	public SelectingScene(BorderPane p) {
		super(p, K.MATCHES.WIDTH, K.MATCHES.HEIGHT);
		root = p;
		root.setStyle("-fx-background-color: #F0F0F0");
		
		tSearchBox = new SelectingSearchHBox();
		tSuggestionsHBox = new SelectingSuggestHBox(15);

		VBox top = new VBox();
		top.getChildren().addAll(tSearchBox, tSuggestionsHBox, I.getInstance().getSeparatorWhite(K.MATCHES.WIDTH, 6));
		root.setTop(top);
		
		cPreviewBox = new SelectingPreviewBox();
		root.setCenter(cPreviewBox);
	}
	
	//called post requests go through
	public void initialize(Event[] arr) {
		lEventsBox = new SelectingEventsVBox(arr);
		lEventsBox.addAllEvents();
		HBox left = new HBox();
		left.getChildren().addAll(lEventsBox, I.getInstance().getSeparatorWhite(6, K.MATCHES.L_EVENTS_HEIGHT));
		root.setLeft(left);
		
		tSuggestionsHBox.generateSuggestions();
	}
	
	public void setContent(Event e) {
		cPreviewBox.setContent(e);
	}
	
	public void indicateSelected(EventsDisplay d) {
		lEventsBox.indicateSelected(d);
	}
	
	public void preview(EventsDisplay d) {
		cPreviewBox.setContent(d.getEvent());
		cPreviewBox.setColor(d.getColor());
	}
	
	public void filterEvents() {
		lEventsBox.filter(tSearchBox.getText());
	}
	
	public Event getSelectedEvent() {
		return lEventsBox.getSelectedEvent();
	}
}
