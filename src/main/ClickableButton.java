package main;

import constants_and_images.I;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import team_events.TeamEventsStage.State;

public class ClickableButton extends ImageView {
	private I.Type type;
	private Image normal;
	private Image indented;
	private String desc;
	//public width = getFitWidth();

	public ClickableButton(I.Type i, I.Type c, String d) {
		type = i;
		
		normal = I.getInstance().getImg(i);
		indented = I.getInstance().getImg(c);
		desc = d;
		
		setImage(normal);
	}
	
	public ClickableButton(I.Type i) {
		this(i, null, null);
	}

	public void onPress() {
		System.out.println("on press");
		if(indented == null)
			doAction();
		else
			setImage(indented);
		
	}
	
	public void onRelease() {
		System.out.println("on release");
		if(indented != null) {
			setImage(normal);
			doAction();
		}
	}
	
	public void doAction() {
		switch(type) {
		case TEAM_EVENTS_BTN:
			ScoutingApp.launchTeamEvents();
			break;
		case COMING_SOON_BTN:
			System.out.println("COMING SOON");
			break;
		case TE_TEAM_LIST_BTN:
			setType(I.Type.TE_BACK_BTN);
			ScoutingApp.teStage.setState(State.TEAM_LIST);
			break;
		case TE_BACK_BTN:
			setType(I.Type.TE_TEAM_LIST_BTN);
			ScoutingApp.teStage.setState(State.NORMAL);
			break;
		default:
			System.out.println("Unknown button action.");
			break;
		}
	}
	
	

	public void setType(I.Type i) {
		type = i;
		setImage(I.getInstance().getImg(type));
	}
	
	public I.Type getType() {
		return type;
	}
	
	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return type.toString();
	}
}