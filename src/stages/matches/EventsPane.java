package stages.matches;

import java.util.ArrayList;
import java.util.Random;

import general.constants.K;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;

public class EventsPane extends VBox {
	
	private Label allTeams;
	private ScrollPane scrollPane;
	private VBox events;
	private ArrayList<EventsDisplay> displayList;
	
	public EventsPane(ArrayList<Event> arr) {
		this.setMaxWidth(K.MATCHES.LEFT_WIDTH);
		allTeams = new Label("All Teams");
		allTeams.setStyle("-fx-font-size: 18; -fx-background-color: #DD0000;");
	    allTeams.setTextFill(Color.WHITE);
		allTeams.setPadding(K.getInsets(7));
		allTeams.setPrefWidth(K.MATCHES.LEFT_WIDTH);
		
		scrollPane = new ScrollPane();
		events = new VBox();
		events.setPadding(K.getInsets(3));
		events.setSpacing(3);
		scrollPane.setContent(events);
		String[] colors = {"#FFC4CA", "#ffdfba", "#ffffba", "#baffc9","#bae1ff","#F9B0FF"};
		Random r = new Random();
		int last = 0;
		displayList = new ArrayList<>();
		for (Event i: arr) {
			int c = r.nextInt(6);
			EventsDisplay display = new EventsDisplay(i);
			displayList.add(display);
			while(c==last) {c=r.nextInt(6);}
			display.setColor(colors[c]);
			last = c;
		}
		getChildren().addAll(allTeams, scrollPane);
	}
	
	public void addAllEvents() {
		for (EventsDisplay i: displayList) {
			events.getChildren().add(i);
		}
	}
	
	public void filter(String s) {
		System.out.println("inner filter: " + s);
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
	
	public MatchesStage getMatchesStage() {
		return ((MatchesStage)((BorderPane) getParent()).getScene().getWindow());
	}
	
	private class eventClicked implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

class EventsDisplay extends VBox {
	private Label name;
	private Label location;
	private Label date;
	private Event event;
	private String color;
	
	public EventsDisplay(Event e) {
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
	
	public void setColor(String s) {
		color = s;
		setStyle("-fx-background-color: " + color);
	}
	
	public String dateConvert(String d) {
		String year = d.substring(0, d.indexOf('-'));
		d = d.substring(d.indexOf('-') + 1);
		String month = d.substring(0, d.indexOf('-'));
		String day = d.substring(d.indexOf('-') + 1);
		switch(month) {
		case "01": month = "January"; break;
		case "02": month = "February"; break;
		case "03": month = "March"; break;
		case "04": month = "April"; break;
		case "05": month = "May"; break;
		case "06": month = "June"; break;
		case "07": month = "July"; break;
		case "08": month = "August"; break;
		case "09": month = "September"; break;
		case "10": month = "October"; break;
		case "11": month = "November"; break;
		case "12": month = "December"; break;
		}
		return month + " " + day + ", " + year;
	}
	
}
