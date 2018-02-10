package stages.team_events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import general.ScoutingApp;
import general.constants.K;
import general.images.I;
import gui.ClickableButton;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;

public class CenterPane extends HBox {

	public int x;
	private double spacing = 10;
	public ArrayList<VBox> columns;
	public ArrayList<TeamInfo> teams;

	public boolean getState() {
		return true;
	}

	public boolean state = false; // editing
	public ArrayList<TeamInfo> newTeams;
	public ArrayList<TeamInfo> removedTeams;
	ClickableButton addButton;

	public CenterPane() {
		super();
		addButton = new ClickableButton(I.Type.TE_ADD_BTN);
		addButton.setLayoutX(50);
		addButton.setLayoutY(500);
		teams = new ArrayList<>();
		columns = new ArrayList<>();
		newTeams = new ArrayList<>();
		removedTeams = new ArrayList<>();
		x = 0;
		setPadding(K.getInsets());
		setPrefWidth(K.TEAM_EVENTS.CENTER_WIDTH);
		setStyle("-fx-background-color: #FFFFFF");
	}

	public ArrayList<TeamInfo> getTeams() {
		return teams;
	}

	public void saveChanges() {
		System.out.println("teams: " + teams);
		for (int b = 0; b < newTeams.size(); b++) {
			for (int i = 0; i < teams.size(); i++) {
				if (teams.get(i).equals(newTeams.get(b))) {
					newTeams.remove(b);
					b--;
				}
			}
		}
		teams.addAll(newTeams);
		for (int i = 0; i < removedTeams.size(); i++) {
			for (int b = 0; b < teams.size(); b++) {
				if (teams.get(b).equals(removedTeams.get(i))) {
					removedTeams.remove(i);
					teams.remove(b);
					i--;
					b--;
					break;
				}
			}
		}
		newTeams = new ArrayList<>();
		removedTeams = new ArrayList<>();
		getScrollPane().clearAddedCategories();
		writeFile();
		getTeamEventsStage().getTeams().clear();
		ArrayList<String> addedCat = new ArrayList<>();
		for(TeamInfo i:teams) {
			String cat = i.getCategory();
			System.out.println("CAT: " + i.getCategory());
			if(!addedCat.contains(cat)) {
				addedCat.add(cat);
				getTeamEventsStage().getTeams().put(new StringWithColor(i.getCategory(), i.getColor()), new ArrayList<Integer>());
			}
		}
		for(StringWithColor c:getTeamEventsStage().getTeams().keySet()) {
			for(TeamInfo i:teams) {
				if(c.getValue().equals(i.getCategory())) {
					getTeamEventsStage().getTeams().get(c).add(new Integer(i.getNumber()));
				}
			}
		}
		System.out.println("TEAMHASH:"+getTeamEventsStage().getTeams());
	}

	private void writeFile() {
		File list = new File("docs\\team_list.txt");
		FileWriter w = null;
		try {
			w = new FileWriter(list);
			w.close();
			w = new FileWriter(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> cat = new ArrayList<>();
		ArrayList<Integer> nums = new ArrayList<>();
		for (TeamInfo t : teams) {
			try {
				if (!cat.contains(t.getCategory())) {
					cat.add(t.getCategory());
					w.write("*" + t.getCategory() + "*" + " " + t.getColor());
					w.write("\n");
					for (TeamInfo b : teams) {
						System.out.println(new StringWithColor(b.getCategory(), b.getColor()));
						if (b.getCategory().equals(t.getCategory()) && !nums.contains(b.getNumber())) {
							nums.add(new Integer(b.getNumber()));
							w.write(b.getNumber() + " ");
						}
					}
					w.write("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		teams = new ArrayList<>();
	}

	private LeftScrollPane getScrollPane() {
		// (LeftScrollPane)
		return (LeftScrollPane) ((BorderPane) this.getParent()).getLeft();
	}

	private TeamEventsStage getTeamEventsStage() {
		return ((TeamEventsStage) ((BorderPane) getParent()).getScene().getWindow());
	}

	public void updateTeamInfo(int teamNumber, String category, String color) {
		System.out.println("UPDATED");
		System.out.println(teamNumber);
		TeamInfo team = new TeamInfo(teamNumber, category);
		teams.add(team);
		if (getChildren().size() == x) {
			VBox box = new VBox();
			box.setPadding(K.getInsets());
			getChildren().add(box);
			columns.add(box);
		}
		columns.get(x).getChildren().add(team);
		team.setColor(color);
	}

	public void handleClick(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		for (int i = 0; i < teams.size(); i++) {
			TeamInfo n = teams.get(i);
			if (n.getLayoutX() <= e.getX() && n.getSizeX() + n.getLayoutX() >= e.getX() && n.getLayoutY() <= e.getY()

					&& n.getSizeY() + n.getLayoutY() >= e.getY()) {
				if (state) {
					removedTeams.add(n);
					teams.remove(i);
				} else {
					n.switchState();
				}
				update();
				return;
			}
		}

	}

	public void update() {
		System.out.println("UPDATED");
		if (state) {
			for (TeamInfo t : teams) {
				t.editMode();
			}
		}
		x = 0;
		double height = 0;
		for (int i = 0; i < columns.size(); i++) {
			while (columns.get(i).getChildren().size() > 0) {
				columns.get(i).getChildren().remove(0);
			}
		}
		
		for(int i = 0; i < teams.size(); i++) {
			TeamInfo n = teams.get(i);
			height += n.getHeight() + 10;
			if (height > 600) {
				height = n.getHeight() + 10;
				x++;
				if (getChildren().size() == x) {
					VBox box = new VBox();
					box.setPadding(K.getInsets());
					getChildren().add(box);
					columns.add(box);
				}
				columns.get(x).getChildren().add(n);
			} else {
				columns.get(x).getChildren().add(n);
			}
		}
	}

	public void changeState() {
		state = !state;
		if (state) {
			getChildren().add(addButton);
		} else {
			getChildren().remove(addButton);
		}
	}
}

class TeamInfo extends VBox {

	double sizeOpened = 0;
	double sizeClosed = 0;
	double titleSize = 32;
	double eventTitleSize = 22;
	double textSize = 17;

	String color = "";

	public boolean state = false;
	Event[] events;
	Label name;
	public boolean opened = false;
	int number;
	public boolean editMode = false;
	public String category;

	public TeamInfo(int teamNum, String c) {
		super(2);
		category = c;
		System.out.println(category);
		number = teamNum;
		setStyle("-fx-border-color: black; -fx-border-width: 3;");
		setPadding(K.getInsets());
		name = new Label(Integer.toString(teamNum));
		name.setStyle("-fx-font-size: 25; -fx-font-color: #346233");
		this.getChildren().add(name);
		sizeOpened += titleSize;
		sizeClosed += titleSize;
		events = ScoutingApp.getDatabase().getEventsForTeamInYear(number, 2018);
	}

	public void addEvents() {
		for (Event i : events) {
			Label eventName = new Label(i.name);
			eventName.setStyle("-fx-font-size: 15");
			if (!opened)
				sizeOpened += eventName.getHeight();
			Label eventInfo = new Label(
					i.city + ", " + i.state_prov + "\t" + dateConvert(i.start_date) + " - " + dateConvert(i.end_date));
			if (!opened)
				sizeOpened += eventInfo.getHeight();
			getChildren().addAll(eventName, eventInfo);
		}
		if (!opened)
			opened = true;
	}

	public void removeEvents() {
		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().remove(i);
			i--;
		}
		if (editMode) {
			HBox h = new HBox();
			h.getChildren().add(name);
			Label x = new Label();
			x.setText("X");
			x.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 2");
			h.getChildren().add(x);
			getChildren().add(h);
		} else {
			getChildren().add(name);
		}

	}

	public String getCategory() {
		return category;
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

	public int getNumber() {
		return number;
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
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public boolean equals(TeamInfo o) {
		if (o.number == number && o.category.equals(category)) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return number + " " + color;
	}

	public void editMode() {
		state = false;
		removeEvents();
	}

}
