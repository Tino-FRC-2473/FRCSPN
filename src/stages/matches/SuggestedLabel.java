package stages.matches;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class SuggestedLabel extends Label{
	public SuggestedLabel(String name) {
		super(name);
//		this.setStyle("-fx-background-color: #ADD8E6; -fx-stroke: white");
		this.setOpacity(1.0);
		Circle c = new Circle();
		c.setStyle("-fx-background-color: #ADD8E6; -fx-stroke: white");
		this.getParent().getChildrenUnmodifiable().add(c);
		c.setLayoutX(this.getLayoutX());
		c.setLayoutY(this.getLayoutY());
	}
}
