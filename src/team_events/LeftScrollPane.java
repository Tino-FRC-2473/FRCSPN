package team_events;

import java.util.ArrayList;
import java.util.HashMap;

import constants_and_images.I;
import constants_and_images.K;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.ClickableButton;

public class LeftScrollPane extends ScrollPane {
	private VBox v;
	private ClickableButton toggleButton;

	private ArrayList<Label> labels;
	// private int selectedIdx;

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
		v.getChildren().add(
				new ImageView(I.getInstance().getSeparator(K.TEAM_EVENTS.LEFT_WIDTH - 2 * v.getPadding().getTop(), 5)));
		toggleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toggleButton.onClick();
			}
		});

		labels = new ArrayList<Label>();
		// selectedIdx = -1;
	}

	public void update(StringWithColor[] arr) {
		clear();
		for (StringWithColor sc : arr) {
			Label l = new Label(sc.getValue());
			l.setStyle("-fx-background-color: #" + sc.getColor() + "; "
					+ "-fx-font-size: 20; -fx-stroke: black; -fx-font-weight: bold");
			labels.add(l);
			l.setPrefSize(K.TEAM_EVENTS.LEFT_WIDTH - 2 * v.getPadding().getTop(), K.TEAM_EVENTS.LEFT_BUTTON_HEIGHT);
			l.setPadding(K.getInsets());
			v.getChildren().add(l);
			l.setOnMouseClicked(new onLabelClicked(sc));
		}
	}

	private void clear() {

	}

	public void handleClick(MouseEvent e) {

	}
	
	private TeamEventsStage getTeamEventsStage() {
		return ((TeamEventsStage)((BorderPane)getParent()).getScene().getWindow());
	}
	
	private CenterPane getCenterPane() {
		return ((CenterPane)((BorderPane)getParent()).getCenter());
	}

	private class onLabelClicked implements EventHandler<MouseEvent> {
		StringWithColor s;

		public onLabelClicked(StringWithColor s) {
			this.s = s;
		}

		@Override
		public void handle(MouseEvent event) {
			HashMap<StringWithColor, ArrayList<Integer>> teams = getTeamEventsStage().getTeams();
			for(StringWithColor b:teams.keySet()) {
				for(Integer d:teams.get(b)) {
					if(s.toString().equals(b.toString())) {
						getCenterPane().updateTeamInfo(d.intValue(), b.getColor());
					}
				}
			}
	}
	}
}
