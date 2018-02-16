package stages.matches;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import general.constants.K;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.Event;

public class EventsPane extends VBox{
	
	private Label allTeams;
	private ScrollPane scrollPane;
	private VBox events;
	private ArrayList<EventsDisplay> displayList;
	private ArrayList<Event> eventList;
	
	public EventsPane(ArrayList<Event> e) {
		eventList = e;
		allTeams = new Label("All Teams");
		allTeams.setStyle("-fx-font-size: 18");
		scrollPane = new ScrollPane();
		events = new VBox();
		events.setPadding(K.getInsets());
		scrollPane.setContent(events);
		displayList = new ArrayList<>();
		for (Event i: eventList) {
			EventsDisplay display = new EventsDisplay(i);
			displayList.add(display);
		}
		getChildren().addAll(allTeams, scrollPane);
	}
	
	public void addAllEvents() {
		for (EventsDisplay i: displayList) {
			events.getChildren().add(i);
		}
	}
	
	public void filter(String s) {
		events.getChildren().clear();
		for (int i = 0; i < displayList.size(); i++) {
			if (displayList.get(i).getName().indexOf(s) != -1) {
				events.getChildren().add(displayList.get(i));
			}
			else if (displayList.get(i).getDate().indexOf(s) != -1) {
				events.getChildren().add(displayList.get(i));
			}
			else if (displayList.get(i).getLocation().indexOf(s) != -1) {
				events.getChildren().add(displayList.get(i));
			}
		}
	}
}

class EventsDisplay extends VBox {
	private Label name;
	private Label location;
	private Label date;
	private Event event;
	
	public EventsDisplay(Event e) {
		DateFormat d = DateFormat.getDateInstance(DateFormat.SHORT);
		event = e;
		name = new Label(e.name);
		name.setStyle("-fx-font-size:14");
		location = new Label(e.city + ", " + e.state_prov);
		date = new Label(dateConvert(e.start_date) + " - " + dateConvert(e.end_date));
		getChildren().addAll(name, location, date);
		setPadding(K.getInsets(3));
	}
	
	public Event getEvent() {
		return event;
	}
	
	public String getName() {
		return name.getText();
	}
	
	public String getDate() {
		return date.getText();
	}
	
	public String getLocation() {
		return location.getText();
	}
	
	public String dateConvert(String d) {
		String year = d.substring(0, d.indexOf('-'));
		d = d.substring(d.indexOf('-') + 1);
		String month = d.substring(0, d.indexOf('-'));
		String day = d.substring(d.indexOf('-') + 1);
		switch(month) {
		case "01":
			month = "January";
			break;
		case "02":
			month = "February";
			break;
		case "03":
			month = "March";
			break;
		case "04":
			month = "April";
			break;
		case "05":
			month = "May";
			break;
		case "06":
			month = "June";
			break;
		case "07":
			month = "July";
			break;
		case "08":
			month = "August";
			break;
		case "09":
			month = "September";
			break;
		case "10":
			month = "October";
			break;
		case "11":
			month = "November";
			break;
		case "12":
			month = "January";
			break;
		}
		return month + " " + day + ", " + year;
	}
}
