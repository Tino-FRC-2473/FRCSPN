package stages.matches;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Event;

public class PreviewPane extends StackPane {
	private Event selected;
	// private HashMap<String, String> labelInfo = new HashMap<String, String>();
	private ArrayList<String> infoTitle = new ArrayList<String>();
	private ArrayList<Label> labels = new ArrayList<Label>();

	public PreviewPane() {
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
		this.getChildren().clear();
		VBox box = new VBox();
		System.out.println("CLEARED");
		this.selected = event;
		for (int i = 0; i < 10; i++) {
			switch (infoTitle.get(i)) {
			case "Name":
				Label a = new Label(infoTitle.get(i) + ": " + selected.name);
				a.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(a);
				break;
			case "Year":
				Label b = new Label(infoTitle.get(i) + ": " + selected.year);
				b.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(b);
				break;
			case "Start Date":
				Label c = new Label(infoTitle.get(i) + ": " + selected.start_date);
				c.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(c);
				break;
			case "End Date":
				Label d = new Label(infoTitle.get(i) + ": " + selected.end_date);
				d.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(d);
				break;
			case "City":
				Label e = new Label(infoTitle.get(i) + ": " + selected.city);
				e.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(e);
				break;
			case "District":
				Label f = null;
				try {
					f = new Label(infoTitle.get(i) + ": " + selected.district.display_name);
				} catch (Exception ex) {
					f = new Label(infoTitle.get(i) + ": uknown");
				}
				f.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(f);
				break;
			case "State":
				Label g = new Label(infoTitle.get(i) + ": " + selected.state_prov);
				g.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(g);
				break;
			case "Country":
				Label h = new Label(infoTitle.get(i) + ": " + selected.country);
				h.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(h);
				break;
			case "Event Code":
				Label j = new Label(infoTitle.get(i) + ": " + selected.event_code);
				j.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(j);
				break;
			case "Key":
				Label k = new Label(infoTitle.get(i) + ": " + selected.key);
				k.setStyle(
						"-fx-background-color: #ADD8E6; -fx-font-size: 30; -fx-stroke: black; -fx-font-weight: bold");
				labels.add(k);
				break;
			default:
				break;
			}
		}
		for (Label l : labels) {
			box.getChildren().add(l);
		}
		this.getChildren().add(box);
	}
}
