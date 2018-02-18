package stages.matches;

import java.util.ArrayList;

import gui.BoxPaddingInsets;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Event;

public class SuggestionsSPane extends ScrollPane {
	private Event[] suggested; 
	private ArrayList<SuggestedLabel> labels;
	private int n;
	private HBox box;
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
	public SuggestionsSPane(int n) {
		this.n = n;
	}
	
	public void generateSuggestions() {
		box = new HBox();
		labels = new ArrayList<SuggestedLabel>();
		
		//get all events then filter using binary search WITHOUT MODIFYING allevents
		String startdate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		//binary search
		int startIndex = bsearch(startdate, 0, getStage().getAllEvents().size()-1);
		if(startIndex < 0) {
			startIndex = 0;
		} else if(startIndex >= getStage().getAllEvents().size()) {
			startIndex = getStage().getAllEvents().size()-1;
		}
		while(startIndex>=1 && getStage().getAllEvents().get(startIndex-1).start_date.compareTo(getStage().getAllEvents().get(startIndex).start_date)==0) {
			startIndex--;
		}
		suggested = new Event[Math.min(n, getStage().getAllEvents().size()-1)-startIndex+1];
		int index = 0;
		for(int i = startIndex; i < Math.min(n, getStage().getAllEvents().size()-1); i++) {
			//System.out.println(getStage().getAllEvents().get(i).name);
			suggested[index] = getStage().getAllEvents().get(i);
			index++;
			SuggestedLabel l = new SuggestedLabel(getStage().getAllEvents().get(i));
//			Circle c = new Circle();
//			c.setFill(new Color(0.67,0.84,0.9,1.0));
//			c.setRadius(size);
//			c.setLayoutX(l.getLayoutX());
//			c.setLayoutY(l.getLayoutY());
//			s.getChildren().add(c);
//			l.setOpacity(1.0);
			l.setOnMouseClicked(new onLabelClicked(getStage().getAllEvents().get(i)));
			labels.add(l);
			box.getChildren().add(l);
		}
		box.setPadding(new Insets(BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET/2.0, BoxPaddingInsets.OFFSET*7.0/4, BoxPaddingInsets.OFFSET/2.0));
		box.setSpacing(BoxPaddingInsets.OFFSET/2.0);
		this.setContent(box);
		this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		this.setFitToHeight(true);
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

