package team_events;

import java.util.ArrayList;

import constants_and_images.I;
import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.ClickableButton;

public class LeftScrollPane extends ScrollPane {
	private VBox v;
	private ClickableButton toggleButton;
	
	private ArrayList<Label> labels;
//	private int selectedIdx;
	
	public LeftScrollPane() {
		super();
		
		v = new VBox();
		v.setPadding(K.getInsets());
		v.setSpacing(K.TEAM_EVENTS.SPACING);
		
		v.setPrefWidth(K.TEAM_EVENTS.LEFT_WIDTH);
		
		setFitToWidth(true);
		setStyle("-fx-background-color: #CCCCCC;");
		setContent(v);
		
		toggleButton = new ClickableButton(I.Type.TE_TEAM_LIST_BTN);
		v.getChildren().add(toggleButton);
		v.getChildren().add(new ImageView(I.getInstance().getSeparator(K.TEAM_EVENTS.LEFT_WIDTH - 2*v.getPadding().getTop(), 5)));
		toggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toggleButton.onPress();
			}
		});
		
		labels = new ArrayList<Label>();
//		selectedIdx = -1;
	}
	
	public void update(StringWithColor[] arr) {
		clear();
		for(StringWithColor sc : arr) {
			Label l = new Label(sc.getValue());
			l.setStyle("-fx-background-color: #" + sc.getColor() + "; "
					+ "-fx-font-size: 20; -fx-stroke: black; -fx-font-weight: bold");
			labels.add(l);
			l.setPrefSize(K.TEAM_EVENTS.LEFT_WIDTH - 2*v.getPadding().getTop(), K.TEAM_EVENTS.LEFT_BUTTON_HEIGHT);
			l.setPadding(K.getInsets());
			v.getChildren().add(l);
			
		}
	}
	
	private void clear() {
		
	}
	
	public void handleClick(MouseEvent e) {
		
	}
}
