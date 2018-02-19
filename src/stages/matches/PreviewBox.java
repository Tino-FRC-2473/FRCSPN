package stages.matches;

import java.util.ArrayList;

import general.constants.K;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;

public class PreviewBox extends VBox {
	// private HashMap<String, String> labelInfo = new HashMap<String, String>();
	private ArrayList<String> infoTitle = new ArrayList<String>();
	private ArrayList<Label> labels = new ArrayList<Label>();

	public PreviewBox() {
		this.setPadding(K.getInsets());
		this.setStyle("-fx-background-color: #FFD32A");
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

	public void setColor(String color) {
		this.setStyle("-fx-background-color: " + color);
		for (Label l : labels) {
			if (l.getText().substring(0, 4).equals("Name")) {
				l.setStyle("-fx-background-color: " + color + "; -fx-font-size: 30; -fx-font-weight: bold");
			} else {
				l.setStyle("-fx-background-color: " + color + "; -fx-font-size: 30");
			}
			l.setTextFill(Color.BLACK);
		}
	}

	public void setContent(Event event) {
		while (labels.size() > 0) {
			this.getChildren().remove(labels.remove(0));
		}
		this.setStyle("-fx-background-color: #FFD32A");
		System.out.println("CLEARED");
		for (int i = 0; i < 10; i++) {
			switch (infoTitle.get(i)) {
			case "Name":
				Label a = new Label("Name: " + event.name);
				a.setStyle(
						"-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK; -fx-font-weight: bold;");
				a.setTextFill(Color.BLACK);
				labels.add(a);
				break;
			case "Year":
				Label b = new Label(infoTitle.get(i) + ": " + event.year);
				b.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				b.setTextFill(Color.BLACK);
				labels.add(b);
				break;
			case "Start Date":
				Label c = new Label(infoTitle.get(i) + ": " + event.start_date);
				c.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				c.setTextFill(Color.BLACK);
				labels.add(c);
				break;
			case "End Date":
				Label d = new Label(infoTitle.get(i) + ": " + event.end_date);
				d.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				d.setTextFill(Color.BLACK);
				labels.add(d);
				break;
			case "City":
				Label e = new Label(infoTitle.get(i) + ": " + event.city);
				e.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				e.setTextFill(Color.BLACK);
				labels.add(e);
				break;
			case "District":
				Label f = null;
				try {
					f = new Label(infoTitle.get(i) + ": " + event.district.display_name);
					f.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
					f.setTextFill(Color.BLACK);
					labels.add(f);
				} catch (Exception ex) {
					f = null;
				}
				break;
			case "State":
				Label g = new Label(infoTitle.get(i) + ": " + event.state_prov);
				g.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				g.setTextFill(Color.BLACK);
				labels.add(g);
				break;
			case "Country":
				Label h = new Label(infoTitle.get(i) + ": " + event.country);
				h.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				h.setTextFill(Color.BLACK);
				labels.add(h);
				break;
			case "Event Code":
				Label j = new Label(infoTitle.get(i) + ": " + event.event_code);
				j.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				j.setTextFill(Color.BLACK);
				labels.add(j);
				break;
			case "Key":
				Label k = new Label(infoTitle.get(i) + ": " + event.key);
				k.setStyle("-fx-background-color: #FFD32A; -fx-font-size: 30; -fx-stroke: BLACK");
				k.setTextFill(Color.BLACK);
				labels.add(k);
				break;
			default:
				break;
			}
		}
		for (Label l : labels) {
			this.getChildren().add(l);
		}
	}
}
