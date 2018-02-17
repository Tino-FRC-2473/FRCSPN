package stages.matches;

import javafx.scene.control.Label;

public class SuggestedLabel extends Label{
	private int size = 0;
	
	public SuggestedLabel(String name, int size) {
		super(name);
		this.size = size;
		this.setOpacity(1.0);
		this.setStyle("-fx-background-color: #ADD8E6; " + "-fx-font-size:"+size+"; -fx-stroke: black; -fx-font-weight: bold");
	}

	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
}
