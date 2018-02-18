package stages.matches;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Event;

public class PreviewPane extends Pane {
	private Event selected;
	//private HashMap<String, String> labelInfo = new HashMap<String, String>();
	private ArrayList<String> infoTitle = new ArrayList<String>();
	private ArrayList<Label> labels = new ArrayList<Label>();
	private VBox box;

	public PreviewPane() {
		box = new VBox();
		this.getChildren().add(box);
		infoTitle.add("Name");
		infoTitle.add("Year");
		infoTitle.add("Start Date");
		infoTitle.add("End Date");
		infoTitle.add("City");
		infoTitle.add("District");
		infoTitle.add("State");
		infoTitle.add("Country");
		infoTitle.add("Event Code");
		infoTitle.add("Key");
	}

	public void setContent(Event event) {
		this.selected = event;
		for (int i = 0; i < 10; i++) {
			switch (infoTitle.get(i)) {
			case "Name":
				Label a = new Label(infoTitle.get(i)+": "+selected.name);
				labels.add(a);
			case "Year":
				Label b = new Label(infoTitle.get(i)+": "+selected.year);
				labels.add(b);
			case "Start Date":
				Label c = new Label(infoTitle.get(i)+": "+selected.start_date);
				labels.add(c);
			case "End Date":
				Label d = new Label(infoTitle.get(i)+": "+selected.end_date);
				labels.add(d);
			case "City":
				Label e = new Label(infoTitle.get(i)+": "+selected.city);
				labels.add(e);
			case "District":
				Label f = new Label(infoTitle.get(i)+": "+selected.district);
				labels.add(f);
			case "State":
				Label g = new Label(infoTitle.get(i)+": "+selected.state_prov);
				labels.add(g);
			case "Country":
				Label h = new Label(infoTitle.get(i)+": "+selected.country);
				labels.add(h);
			case "Event Code":
				Label j = new Label(infoTitle.get(i)+": "+selected.event_code);
				labels.add(j);
			case "Key":
				Label k = new Label(infoTitle.get(i)+": "+selected.key);
				labels.add(k);
			default:
				break;
			}
		}

	}
}
