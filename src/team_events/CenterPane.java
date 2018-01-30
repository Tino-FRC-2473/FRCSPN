package team_events;

import java.util.ArrayList;

import constants_and_images.K;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.ScoutingApp;
import models.Event;

public class CenterPane extends GridPane {
	public int y;
	public int x;
	private double spacing = 10;
	public ArrayList<TeamInfo> teams;

	public CenterPane() {
		super();
		teams = new ArrayList<>();
		y = 0;
		x = 0;
		setPadding(K.getInsets());
		setHgap(spacing);
		setVgap(spacing);
		setPrefWidth(K.TEAM_EVENTS.CENTER_WIDTH);
		setStyle("-fx-background-color: #FFFFFF");
		// Label test = new Label("254");
		// test.setLayoutY(y);
		// test.setStyle("-fx-background-color: #FF0000; -fx-font-size: 20");
		// getChildren().add(test);
		//
		// updateTeamInfo(Integer.parseInt(test.getText()));

	}

	public void updateTeamInfo(int teamNumber, String color) {
		System.out.println(teamNumber);
		TeamInfo team = new TeamInfo(teamNumber);
		teams.add(team);
		add(team, x, y, 1, 1);
		y++;
		team.setColor(color);
	}

	public void handleClick(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		for (int i = 0; i < teams.size(); i++) {
			TeamInfo n = teams.get(i);
			if (n.getLayoutX() <= e.getX() && n.getSizeX() + n.getLayoutX() >= e.getX() && n.getLayoutY() <= e.getY()
					&& n.getSize() + n.getLayoutY() >= e.getY()) {
				n.switchState();
				update();
				return;
			}
		}

	}

	public void update() {
		y = 0;
		x = 0;
		double height = 0;
		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().remove(i);
			i--;
		}
		for (int i = 0; i < teams.size(); i++) {

			TeamInfo n = teams.get(i);
			height += n.getSize();
			if (height >= getHeight()) {
				height = n.getSize();
				x++;
				y = 0;
				add(n, x, y, 1, 1);
				y++;
			} else {
				add(n, x, y, 1, 1);
				y++;
			}

		}
	}
}

class TeamInfo extends VBox {

	double sizeOpened = 0;
	double sizeClosed = 0;
	double titleSize = 32;
	double eventTitleSize = 22;
	double textSize = 17;
	public boolean state = false;
	Event[] events;
	Label name;
	public boolean opened = false;
	int number;

	public TeamInfo(int teamNum) {
		super(2);
		number = teamNum;
		setStyle("-fx-border-color: black; -fx-border-width: 3;");
		setPadding(K.getInsets());
		name = new Label(Integer.toString(teamNum));
		name.setStyle("-fx-font-size: 25; -fx-font-color: #346233");
		this.getChildren().add(name);
		sizeOpened+=titleSize;
		sizeClosed+=titleSize;
		events = ScoutingApp.getRequester().getTeamEventsForYear(teamNum, 2018);
	}

	public void addEvents() {
		for (Event i : events) {
			Label eventName = new Label(i.name);
			eventName.setStyle("-fx-font-size: 15");
			if (!opened)
				sizeOpened += eventTitleSize;
			Label eventInfo = new Label(
					i.city + ", " + i.state_prov + "\t" + dateConvert(i.start_date) + " - " + dateConvert(i.end_date));
			if (!opened)
				sizeOpened += textSize;
			getChildren().addAll(eventName, eventInfo);
		}
		if (!opened)
			opened = !opened;
	}

	public void removeEvents() {
		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().remove(i);
			i--;
		}
		getChildren().add(name);
	}

	public String dateConvert(String d) {
		String year = d.substring(0, d.indexOf('-'));
		d = d.substring(d.indexOf('-') + 1);
		String month = d.substring(0, d.indexOf('-'));
		String day = d.substring(d.indexOf('-') + 1);
		switch (month) {
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

	public double getSize() {
		if (state) {
			return sizeOpened;
		} else {
			return sizeClosed;
		}
	}

	public double getSizeX() {
		return this.getWidth();
	}

	public double getSizeY() {
		return this.getHeight();
	}

	public void switchState() {
		System.out.println("update:" + number + "\n");
		state = !state;
		if (state) {
			addEvents();
		} else {
			removeEvents();
		}
	}

	public void setColor(String color) {
		setStyle("-fx-background-color: #" + color);
	}

}
