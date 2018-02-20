package stages.matches;

import java.util.ArrayList;

import general.ScoutingApp;
import general.constants.K;
import gui.BoxPaddingInsets;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;

public class SelectingSuggestHBox extends HBox {
	private Label title;
	private ScrollPane sPane;
	private HBox contents;
	
	private Event[] suggested; 
	private ArrayList<SuggestedLabel> labels;
	private int n;
	
	public SelectingSuggestHBox(int n) {
		this.n = n;
	}
	
	private class onLabelClicked implements EventHandler<MouseEvent> {
		Event event;
		public onLabelClicked(Event event) {
			this.event = event;
		}
		@Override
		public void handle(MouseEvent e) {
			ScoutingApp.mStage.indicateSelected(new EventsDisplay(event));
			ScoutingApp.mStage.setContent(event);
		}
		
	}
	
	public void generateSuggestions() {
		contents = new HBox();
		labels = new ArrayList<SuggestedLabel>();
		
		String startdate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		
		int startIndex = bsearch(startdate, 0, ScoutingApp.mStage.getAllEvents().length-1);
		if(startIndex < 0)
			startIndex = 0;
		else if(startIndex >= ScoutingApp.mStage.getAllEvents().length)
			startIndex = ScoutingApp.mStage.getAllEvents().length-1;
		
		while(startIndex>=1 && ScoutingApp.mStage.getAllEvents()[startIndex-1].start_date.compareTo(ScoutingApp.mStage.getAllEvents()[startIndex].start_date)==0)
			startIndex--;
		
		suggested = new Event[Math.min(n, ScoutingApp.mStage.getAllEvents().length-1)-startIndex+1];
		int index = 0;
		for(int i = startIndex; i < Math.min(n, ScoutingApp.mStage.getAllEvents().length-1); i++) {
			suggested[index] = ScoutingApp.mStage.getAllEvents()[i];
			index++;
			SuggestedLabel l = new SuggestedLabel(ScoutingApp.mStage.getAllEvents()[i]);
			l.setOnMouseClicked(new onLabelClicked(ScoutingApp.mStage.getAllEvents()[i]));
			labels.add(l);
			contents.getChildren().addAll(l);
		}
		
		contents.setPadding(new Insets(BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET*7.0/4, BoxPaddingInsets.OFFSET/2.0));
		contents.setSpacing(BoxPaddingInsets.OFFSET/2.0);
		sPane = new ScrollPane();
		sPane.setContent(contents);
		sPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sPane.setFitToHeight(true);
		
		title = new Label("Upcoming Events");
		title.setStyle("-fx-font-size: 18; -fx-background-color: #FFD32A; -fx-font-weight: bold;");
		title.setPadding(K.getInsets(7));
		title.setMinWidth(K.MATCHES.LEFT_WIDTH*0.65);
		
		this.getChildren().addAll(title, sPane);
		title.setPrefHeight(((VBox)getParent()).getHeight()*0.95);
	}
	
	private int bsearch(String startdate, int first, int last){
		if(ScoutingApp.mStage.getAllEvents()[first].start_date.equals(startdate))
			return first;
		else if(ScoutingApp.mStage.getAllEvents()[last].start_date.equals(startdate))
			return last;
		else if(last-first <= 1)
			return Math.min(last, first);
		else {
			if(startdate.compareTo(ScoutingApp.mStage.getAllEvents()[(first+last)/2].start_date) > 0)
				return bsearch(startdate,(first+last)/2,last);
			else if(startdate.compareTo(ScoutingApp.mStage.getAllEvents()[(first+last)/2].start_date) < 0)
				return bsearch(startdate,first,(first+last)/2);
			else
				return (first+last)/2;
		}
	}
}

class SuggestedLabel extends Label {
	private Event event;
	
	public SuggestedLabel(Event event) {
		super(event.name);
		this.event = event;
		this.setStyle("-fx-background-color: #B79A00; -fx-font-size: 14; -fx-stroke: black; -fx-font-weight: bold");
		this.setTextFill(Color.WHITE);
		this.setPadding(new Insets(BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET, BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET));
	}
	
	public Event getEvent() {
		return this.event;
	}
}
