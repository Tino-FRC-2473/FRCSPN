package stages.matches;

import java.util.ArrayList;

import general.constants.K;
import gui.BoxPaddingInsets;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;

public class SuggestionsHBox extends HBox {
	private Label title;
	private ScrollPane sPane;
	private HBox contents;
	
	private Event[] suggested; 
	private ArrayList<SuggestedLabel> labels;
	private int n;
	
	public SuggestionsHBox(int n) {
		this.n = n;
	}
	
	private class onLabelClicked implements EventHandler<MouseEvent> {
		Event event;
		public onLabelClicked(Event event) {
			this.event = event;
		}
		@Override
		public void handle(MouseEvent e) {
			getStage().getPreviewPane().setContent(this.event);
		}
		
	}
	
	public void generateSuggestions() {
		contents = new HBox();
		labels = new ArrayList<SuggestedLabel>();
		
		String startdate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		
		int startIndex = bsearch(startdate, 0, getStage().getAllEvents().size()-1);
		if(startIndex < 0)
			startIndex = 0;
		else if(startIndex >= getStage().getAllEvents().size())
			startIndex = getStage().getAllEvents().size()-1;
		
		while(startIndex>=1 && getStage().getAllEvents().get(startIndex-1).start_date.compareTo(getStage().getAllEvents().get(startIndex).start_date)==0)
			startIndex--;
		
		suggested = new Event[Math.min(n, getStage().getAllEvents().size()-1)-startIndex+1];
		int index = 0;
		for(int i = startIndex; i < Math.min(n, getStage().getAllEvents().size()-1); i++) {
			suggested[index] = getStage().getAllEvents().get(i);
			index++;
			SuggestedLabel l = new SuggestedLabel(getStage().getAllEvents().get(i));
			l.setOnMouseClicked(new onLabelClicked(getStage().getAllEvents().get(i)));
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
	
	private MatchesStage getStage() {
		return (MatchesStage)
					((BorderPane)
							((VBox)getParent())
					.getParent())
				.getScene().getWindow();
	}
	
	private int bsearch(String startdate, int first, int last){
		   if(getStage().getAllEvents().get(first).start_date.equals(startdate)) {
			   return first;
		   } else if(getStage().getAllEvents().get(last).start_date.equals(startdate)) {
			   return last;
		   } else if(last-first <= 1){
			   return Math.min(last, first);
		   } else {
			   if(startdate.compareTo(getStage().getAllEvents().get((first+last)/2).start_date) > 0)
				   return bsearch(startdate,(first+last)/2,last);
			   else if(startdate.compareTo(getStage().getAllEvents().get((first+last)/2).start_date) < 0)
				   return bsearch(startdate,first,(first+last)/2);
			   else
				   return (first+last)/2;
		   }
		}
}

