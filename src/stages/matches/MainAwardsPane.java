package stages.matches;

import general.ScoutingApp;
import general.constants.K;
import gui.BoxPaddingInsets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Award;

public class MainAwardsPane extends ScrollPane {
	private VBox content;
	
	public MainAwardsPane() {
		Award[] awards = ScoutingApp.getDatabase().getAwardsAtEvent(ScoutingApp.mStage.getEvent().key);
		content = new VBox();
		content.setStyle("-fx-background-color: #FFFFAA;");
		for(Award a : awards) {
			HBox thing = new HBox();
			thing.setSpacing(.5*BoxPaddingInsets.OFFSET);
			thing.setPadding(K.getInsets((int)(.5*BoxPaddingInsets.OFFSET)));
			
			String recips = "";
			for(int i = 0; i < a.recipient_list.length; i++)
				recips += a.recipient_list[i] + ((i < a.recipient_list.length-1)?"; ":"");
			
			Label l = new Label(a.name);
			l.setStyle("-fx-font-size: 30; -fx-font-weight: bold;"); //WHY ISNT THIS WORKING
			thing.getChildren().addAll(l, new Label(recips));
			for(Node n : thing.getChildren())
				n.setStyle("-fx-background-color: #FFFFAA;");
			
			content.getChildren().add(thing);
		}
		this.setContent(content);
	}
}
