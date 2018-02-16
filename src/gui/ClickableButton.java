package gui;

import general.ScoutingApp;
import general.images.I;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import stages.team_events.TeamEventsStage.State;

public class ClickableButton extends ImageView {
	private I.Type type;
	private Image normal;
	private Image indented;
	private String desc;
	//public width = getFitWidth();

	public ClickableButton(I.Type i, I.Type c, String d) {
		type = i;
		
		normal = I.getInstance().getImg(i);
		indented = (c==null)?null:I.getInstance().getImg(c);
		
		desc = d;
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) { onPress(); }
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) { onRelease(); }
		});
		
		this.setImage(normal);
	}
	
	public ClickableButton(I.Type i) {
		this(i, null, null);
	}

	
	public void onPress() {
//		System.out.println("on press");
		if(indented == null)
			doAction();
		else
			setImage(indented);
		
	}
	
	public void onRelease() {
//		System.out.println("on release");
		if(indented != null) {
			setImage(normal);
			doAction();
		}
	}
	
	private void doAction() {
//		System.out.println("do action");
		switch(type) {
		case TEAM_EVENTS_BTN:
			ScoutingApp.launchTeamEvents();
			break;
		case MATCHES_BTN:
			ScoutingApp.launchMatches();
			break;
		case COMING_SOON_BTN:
			System.out.println("COMING SOON");
			break;
		case TE_TEAM_LIST_BTN:
			setType(I.Type.TE_BACK_BTN);
			ScoutingApp.teStage.setState(State.EDITING);
			break;
		case TE_BACK_BTN:
			setType(I.Type.TE_TEAM_LIST_BTN);
			ScoutingApp.teStage.setState(State.VIEWING);
			break;
		case TE_ADD_BTN:
			ScoutingApp.teStage.getCPane().addDialog();
			break;
		case M_SEARCH_BTN:
			ScoutingApp.mStage.filterEvents();
			break;
		case M_SELECT_BTN:
			break;
		default:
			System.out.println("Unknown button action for type " + type);
			break;
		}
	}

	private void setType(I.Type i) {
		type = i;
		setImage(I.getInstance().getImg(type));
	}
	
	public I.Type getType() { return type; }
	public String getDesc() { return desc; }
	@Override public String toString() { return type.toString(); }
}