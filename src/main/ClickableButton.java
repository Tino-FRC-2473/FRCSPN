package main;

import constants_and_images.I;
import javafx.scene.image.ImageView;
import team_events.TeamEventsStage.State;

public class ClickableButton extends ImageView {
	private I.imgs type;
	
	public ClickableButton(I.imgs i) {
		setImage(I.getInstance().getImg(i));
		type = i;
	}

	public void onClick() {
		switch(type) {
		case TEAM_EVENTS_BTN:
			ScoutingApp.launchTeamEvents();
			break;
		case COMING_SOON_BTN:
			System.out.println("COMING SOON");
			break;
		case TE_TEAM_LIST_BTN:
			setType(I.imgs.TE_BACK_BTN);
			ScoutingApp.teStage.setState(State.TEAM_LIST);
			break;
		case TE_BACK_BTN:
			setType(I.imgs.TE_TEAM_LIST_BTN);
			ScoutingApp.teStage.setState(State.NORMAL);
			break;
		default:
			System.out.println("Unknown button clicked.");
			break;
		}
			
	}
	
	public void setType(I.imgs i) {
		setImage(I.getInstance().getImg(i));
		type = i;
	}
	
	public I.imgs getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}