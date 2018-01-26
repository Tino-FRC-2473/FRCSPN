package team_events;

import java.util.ArrayList;

import constants_and_images.K;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LeftScrollPane extends ScrollPane {
	private VBox v;
	private ArrayList<Label> labels;
	private int selectedIdx;
	
	public LeftScrollPane() {
		super();
		
		v = new VBox();
		v.setPadding(K.getInsets());
		v.setSpacing(K.TEAM_EVENTS.SPACING);
		
		v.setPrefWidth(K.TEAM_EVENTS.LEFT_WIDTH); //fix height eventually
		Label lTitle = new Label("Group Lists");
		lTitle.setWrapText(true);
		lTitle.setStyle("-fx-background-color: #FF0000;");
		lTitle.setStyle("-fx-font-size: 30");
		v.getChildren().add(lTitle);
		
		setStyle("-fx-background-color: #CCCCCC;");
		setContent(v);
	}
	
	public VBox getVBox() {
		return v;
	}
	
	public void handleClick(MouseEvent e) {
		
	}
}
