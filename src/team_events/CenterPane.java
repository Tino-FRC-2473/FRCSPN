package team_events;

import java.util.ArrayList;

import constants_and_images.I;
import constants_and_images.K;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.ClickableButton;
import main.ScoutingApp;
import models.Event;

public class CenterPane extends Pane {
	private ClickableButton toggleButton;
	private double y;
	private double spacing = 20;
	public CenterPane() {
		super();
		y=0;
		setPadding(K.getInsets());
		setPrefWidth(K.TEAM_EVENTS.CENTER_WIDTH);
		setStyle("-fx-background-color: #FFFFFF");
		
		toggleButton = new ClickableButton(I.imgs.TE_TEAM_LIST_BTN);
		getChildren().add(toggleButton);
		toggleButton.setX(K.TEAM_EVENTS.CENTER_WIDTH - K.TEAM_EVENTS.TEAM_LIST_SPACING - K.TEAM_EVENTS.TEAM_LIST_WIDTH);
		toggleButton.setY(K.TEAM_EVENTS.TEAM_LIST_SPACING);
		
//		Label test = new Label("254");
//		test.setLayoutY(y);
//		test.setStyle("-fx-background-color: #FF0000; -fx-font-size: 20");
//  		getChildren().add(test); 
//  		
//		updateTeamInfo(Integer.parseInt(test.getText()));
		updateTeamInfo(254);
		updateTeamInfo(2473);
		updateTeamInfo(846);
	}
	
	private void updateTeamInfo(int teamNumber) {
		TeamInfo team = new TeamInfo(teamNumber);
		team.setLayoutY(y);
		y+=team.getSize()+spacing;
		getChildren().add(team);
				
	}
	
	public void handleClick(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		if(toggleButton.contains(e.getX(), e.getY())) {
			toggleButton.onClick();
			return;
		}
		for (int i = 0; i < getChildren().size(); i++) {
			if (getChildren().get(i).getClass().equals(TeamInfo.class)) {
				if (getChildren().get(i).contains(e.getX(),e.getY())){
					TeamInfo n = (TeamInfo)getChildren().get(i);
					n.switchState();
					update();
					return;
				}
			}
			
		}
	}
	
	public void update() {
		y = 0;
		for (int i = 0; i < getChildren().size(); i++) {
	
			if (getChildren().get(i).getClass().equals(TeamInfo.class)) {
				TeamInfo n = (TeamInfo) getChildren().get(i);
				n.setLayoutY(y);
				y+=n.getSize() + spacing;
			}
		}
	}
	
}

class TeamInfo extends VBox{

	double sizeOpened=0;
	double sizeClosed=0;
	double titleSize = 32;
	double eventTitleSize = 22;
	double textSize = 17;
	public boolean state = false;
	ArrayList<Event> events = new ArrayList<>();
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
		sizeOpened+=titleSize;
		sizeClosed+=titleSize;
		String[] teamEvents = ScoutingApp.getRequester().getTeamEventsForYear(teamNum,2018);
		if(teamEvents == null)
			teamEvents = new String[] {"no events"};
		
		for(int i = 0; i < teamEvents.length; i++) {
			events.add(ScoutingApp.getRequester().getEventInfo(teamEvents[i]));
		}
		getChildren().add(name);
		
	}
	
	public void addEvents() {
		for (Event i : events) {
			Label eventName = new Label(i.name);
			eventName.setStyle("-fx-font-size: 15");
			if(!opened) sizeOpened+=eventTitleSize;
			Label eventInfo = new Label(i.city + ", " + i.state_prov + "\t" + dateConvert(i.start_date) + " - " + dateConvert(i.end_date));
			if(!opened) sizeOpened+=textSize;
			getChildren().addAll(eventName, eventInfo);
		}
		if (!opened) opened=!opened;
	}
	public void removeEvents() {
		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().remove(i);
			i--;
		}
		getChildren().add(name);
	}
	
	public String dateConvert(String d) {
		String year = d.substring(0,d.indexOf('-'));
		d = d.substring(d.indexOf('-')+1);
		String month = d.substring(0,d.indexOf('-'));
		String day = d.substring(d.indexOf('-')+1);
		switch(month) {
		case "01": month = "January"; break;
		case "02": month = "February";  break;
		case "03": month = "March";  break;
		case "04": month = "April";  break;
		case "05": month = "May";  break;
		case "06": month = "June";  break;
		case "07": month = "July";  break;
		case "08": month = "August";  break;
		case "09": month = "September";  break;
		case "10": month = "October";  break;
		case "11": month = "November";  break;
		case "12": month = "January";  break;
		}
		return month + " " + day + ", " + year;
	}
	public double getSize() {
		if (state) { 
			return sizeOpened;
		}
		else {
			return sizeClosed;
		}
	}
	public void switchState() {
		System.out.println("update:" + number + "\n");
		state = !state;
		if (state) {
			addEvents();
		}
		else {
			removeEvents();
		}
	}
}

