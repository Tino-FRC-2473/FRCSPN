package stages.matches;

import java.util.ArrayList;

import general.constants.K;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.Event;

public class SuggestionsBox extends HBox {
	private Event[] suggested; 
	private ArrayList<SuggestedLabel> labels;
	
	public SuggestionsBox(int n) {
		this.setPadding(K.getInsets());
		labels = new ArrayList<SuggestedLabel>();
		
		//get all events then filter using binary search WITHOUT MODIFYING allevents
		Event tempEvent = new Event();
		tempEvent.start_date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		tempEvent.end_date = tempEvent.start_date;
		this.getStage().getAllEvents().add(tempEvent);
		
		
		this.getStage().getAllEvents().subList(0, getStage().getAllEvents().indexOf(tempEvent)+1).clear();
		suggested = getStage().getAllEvents().subList(0, Math.min(getStage().getAllEvents().size(), n)).toArray(new Event[Math.min(getStage().getAllEvents().size(), n)]);
		for(Event e : suggested) {
			SuggestedLabel l = new SuggestedLabel(e.name);
			this.getChildren().add(l);
		}
	}
	
	private MatchesStage getStage() {
		return ((MatchesStage)((BorderPane) getParent()).getScene().getWindow());
	}
}
