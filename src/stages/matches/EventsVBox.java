package stages.matches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import general.ScoutingApp;
import general.constants.K;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;

public class EventsVBox extends VBox {
	private Label title;
	private ScrollPane scrollPane;
	private VBox events;
	private ArrayList<EventsDisplay> displayList;
	private EventsDisplay selected;
	
	public EventsVBox(ArrayList<Event> arr) {
		this.setMaxWidth(K.MATCHES.LEFT_WIDTH);
		
		title = new Label("All Teams");
		title.setStyle("-fx-font-size: 18; -fx-background-color: #DD0000; -fx-font-weight: bold;");
	    title.setTextFill(Color.WHITE);
		title.setPadding(K.getInsets(7));
		title.setPrefWidth(K.MATCHES.LEFT_WIDTH);
		this.getChildren().add(title);
		
		scrollPane = new ScrollPane();
		events = new VBox();
		events.setPadding(K.getInsets(3));
		events.setSpacing(3);
		scrollPane.setContent(events);
		scrollPane.setFitToWidth(true);
		scrollPane.setMinHeight(K.MATCHES.L_EVENTS_HEIGHT - K.MATCHES.ALL_TEAMS_HEIGHT - 3*3 - 1);
		
		selected = null;
		
		String[] colors = {"#FFC4CA", "#ffdfba", "#ffffba", "#baffc9", "#bae1ff", "#F9B0FF"};
		Random r = new Random();
		int last = 0;
		displayList = new ArrayList<EventsDisplay>();
		for(Event i : arr) {
			int c = r.nextInt(6);
			EventsDisplay display = new EventsDisplay(i);
			display.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent e) {
					indicateSelected(display);
				}
			});
			displayList.add(display);
			while(c==last)
				c=r.nextInt(6);
			display.setColor(colors[c]);
			last = c;
		}
		this.getChildren().add(scrollPane);
	}
	
	public Event getSelectedEvent() {
		return (selected == null) ? null : selected.getEvent();
	}
	
	public void indicateSelected(EventsDisplay d) {
//		if(selected == null || !d.equals(selected)) {
//			if(selected != null) selected.highlight(false);
//			selected = d;
//			selected.highlight(true);
//			ScoutingApp.mStage.preview(d);
//		}
		if(selected != null) selected.highlight(false);
		for(EventsDisplay e : displayList) {
			if(d.equals(e)) {
				e.highlight(true);
				selected = e;
				ScoutingApp.mStage.preview(selected);
				break;
			}
		}
	}
	
	public void addAllEvents() {
		events.getChildren().clear();
		for(EventsDisplay i : displayList) {
			events.getChildren().add(i);
		}
	}
	
	public void filter(String s) {
		events.getChildren().clear();
		for(int i = 0; i < displayList.size(); i++) {
			if(displayList.get(i).contains(s)) {
				events.getChildren().add(displayList.get(i));
			}
		}
	}
	
//	private class eventClicked implements EventHandler<MouseEvent> {
//		@Override
//		public void handle(MouseEvent e) {
//			
//		}
//	}
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
		name.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
		name.setWrapText(true);
		this.setMaxWidth(K.MATCHES.LEFT_WIDTH - 16);
		location = new Label(e.city + ", " + e.state_prov + ", " + e.country);
		date = new Label(dateConvert(e.start_date) + ((e.start_date.equals(e.end_date)) ? "" : " - " + dateConvert(e.end_date)));
		this.getChildren().addAll(name, location, date);
		this.setPadding(K.getInsets(3));
	}
	
	public boolean equals(EventsDisplay other) {
		return this.getEvent().equals(other.getEvent());
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void highlight(boolean h) {
		if(h)
			this.setStyle("-fx-border-color: yellow; -fx-border-width: 7; -fx-background-color: " + color);
		else
			this.setStyle("-fx-border-radius: 0; -fx-background-color: " + color);
	}
	
	public String getName() {
		return name.getText();
	}
	
	public boolean contains(String s) {
		if(
			this.getName().toLowerCase().indexOf(s.toLowerCase()) != -1 ||
			this.getDate().toLowerCase().indexOf(s.toLowerCase()) != -1 ||
			this.getSearchableLocation().toLowerCase().indexOf(s.toLowerCase()) != -1
		)
			return true;
		return false;
	}
	
	public String getDate() {
		return date.getText();
	}
	
	public String getLocation() {
		return location.getText();
	}
	
	public String getSearchableLocation() {
		int firstComma = getLocation().indexOf(',');
		int secondComma = getLocation().indexOf(',', firstComma+2);
		return getLocation() + " / " + StatesUtil.get(getLocation().substring(firstComma+2, secondComma));
	}
	
	public void setColor(String s) {
		color = s;
		this.setStyle("-fx-background-color: " + color);
	}
	
	public String getColor() {
		return color;
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
	
	@Override
	public String toString() {
		return event.toString();
	}
}

class StatesUtil {
	private Map<String, String> states = new HashMap<String, String>();
	
	private static StatesUtil theInstance;
	
	static {
		theInstance = new StatesUtil();
	}
	
	public static String get(String s) {
		String t = theInstance.states.get(s);
		return (t != null) ? t : "";
	}
	
	public StatesUtil() {
		states = new HashMap<String, String>();
	    states.put("AL", "Alabama");
	    states.put("AK", "Alaska");
	    states.put("AB", "Alberta");
	    states.put("AZ", "Arizona");
	    states.put("AR", "Arkansas");
	    states.put("BC", "British Columbia");
	    states.put("CA", "California");
	    states.put("CO", "Colorado");
	    states.put("CT", "Connecticut");
	    states.put("DE", "Delaware");
	    states.put("DC", "District Of Columbia");
	    states.put("FL", "Florida");
	    states.put("GA", "Georgia");
	    states.put("GU", "Guam");
	    states.put("HI", "Hawaii");
	    states.put("ID", "Idaho");
	    states.put("IL", "Illinois");
	    states.put("IN", "Indiana");
	    states.put("IA", "Iowa");
	    states.put("KS", "Kansas");
	    states.put("KY", "Kentucky");
	    states.put("LA", "Louisiana");
	    states.put("ME", "Maine");
	    states.put("MB", "Manitoba");
	    states.put("MD", "Maryland");
	    states.put("MA", "Massachusetts");
	    states.put("MI", "Michigan");
	    states.put("MN", "Minnesota");
	    states.put("MS", "Mississippi");
	    states.put("MO", "Missouri");
	    states.put("MT", "Montana");
	    states.put("NE", "Nebraska");
	    states.put("NV", "Nevada");
	    states.put("NB", "New Brunswick");
	    states.put("NH", "New Hampshire");
	    states.put("NJ", "New Jersey");
	    states.put("NM", "New Mexico");
	    states.put("NY", "New York");
	    states.put("NF", "Newfoundland");
	    states.put("NC", "North Carolina");
	    states.put("ND", "North Dakota");
	    states.put("NT", "Northwest Territories");
	    states.put("NS", "Nova Scotia");
	    states.put("NU", "Nunavut");
	    states.put("OH", "Ohio");
	    states.put("OK", "Oklahoma");
	    states.put("ON", "Ontario");
	    states.put("OR", "Oregon");
	    states.put("PA", "Pennsylvania");
	    states.put("PE", "Prince Edward Island");
	    states.put("PR", "Puerto Rico");
	    states.put("QC", "Quebec");
	    states.put("RI", "Rhode Island");
	    states.put("SK", "Saskatchewan");
	    states.put("SC", "South Carolina");
	    states.put("SD", "South Dakota");
	    states.put("TN", "Tennessee");
	    states.put("TX", "Texas");
	    states.put("UT", "Utah");
	    states.put("VT", "Vermont");
	    states.put("VI", "Virgin Islands");
	    states.put("VA", "Virginia");
	    states.put("WA", "Washington");
	    states.put("WV", "West Virginia");
	    states.put("WI", "Wisconsin");
	    states.put("WY", "Wyoming");
	    states.put("YT", "Yukon Territory");
	}
}
