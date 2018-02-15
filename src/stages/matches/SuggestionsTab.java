package stages.matches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import general.constants.K;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.Event;
import stages.team_events.TeamEventsStage;

public class SuggestionsTab extends HBox{
	private Event[] suggested; 
	private ArrayList<SuggestedLabel> labels;
	
	public SuggestionsTab(int n) {
		this.setPadding(K.getInsets());
		labels = new ArrayList<SuggestedLabel>();
		Event tempEvent = new Event();
		tempEvent.start_date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		tempEvent.end_date = tempEvent.start_date;
		getStage().getAllEvents().add(tempEvent);
		
		Collections.sort(getStage().getAllEvents(), new Comparator<Event>() {
			@Override public int compare(Event e1, Event e2) {
				if(e1.start_date.compareTo(e2.start_date) == 0)
					return e1.end_date.compareTo(e2.end_date);
				return e1.start_date.compareTo(e2.start_date);
			}
		});
		getStage().getAllEvents().subList(0, getStage().getAllEvents().indexOf(tempEvent)+1).clear();
		suggested = getStage().getAllEvents().subList(0, Math.min(getStage().getAllEvents().size(), n)).toArray(new Event[Math.min(getStage().getAllEvents().size(), n)]);
		for(Event e:suggested) {
			SuggestedLabel l = new SuggestedLabel(e.name);
			this.getChildren().add(l);
		}
	}
	private MatchesStage getStage() {
		return ((MatchesStage)((BorderPane) getParent()).getScene().getWindow());
	}
}
