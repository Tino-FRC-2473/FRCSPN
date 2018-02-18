package general.images;

import java.util.HashMap;

import general.constants.K;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class I {
	private static I instance;
	private static HashMap<Type, Image> images;
	private static final String BASE = "file:images/";
	
	private I() {
		images = new HashMap<Type, Image>();
	}
	
	public Image getImg(Type i) {
		if(images.get(i) == null) addImg(i);
		return images.get(i);
    }
	
	public void addImg(Type i) {
		Image img = null;
		
		switch(i) {
		case MAIN_ICON:
			img = new Image(BASE + "main" + "/icon.png");
			break;
		case COMING_SOON_BTN:
			img = new Image(BASE + "main" + "/coming_soon.png", K.MAIN.BTN_LEN, 0, true, true);
			break;
		case COMING_SOON_BTN_CLICKED:
			img = new Image(BASE + "main" + "/coming_soon_clicked.png", K.MAIN.BTN_LEN, 0, true, true);
			break;
		case MATCHES_BTN:
			img = new Image(BASE + "main" + "/matches.png", K.MAIN.BTN_LEN, 0, true, true);
			break;
		case MATCHES_BTN_CLICKED:
			img = new Image(BASE + "main" + "/matches_clicked.png", K.MAIN.BTN_LEN, 0, true, true);
			break;
		case TEAM_EVENTS_BTN:
			img = new Image(BASE + "main" + "/team_events.png", K.MAIN.BTN_LEN, 0, true, true);
			break;
		case TEAM_EVENTS_BTN_CLICKED:
			img = new Image(BASE + "main" + "/team_events_clicked.png", K.MAIN.BTN_LEN, 0, true, true);
			break;
		case TE_ADD_BTN:
			img = new Image(BASE + "team_events" + "/add.png", K.TEAM_EVENTS.EDIT_TEAMS_WIDTH, 0, true, true);
			break;
		case TE_BACK_BTN:
			img = new Image(BASE + "team_events" + "/back.png", K.TEAM_EVENTS.EDIT_TEAMS_WIDTH, 0, true, true);
			break;
		case TE_TEAM_LIST_BTN:
			img = new Image(BASE + "team_events" + "/edit_teams.png", K.TEAM_EVENTS.EDIT_TEAMS_WIDTH, 0, true, true);
			break;
		case M_SEARCH_BTN:
			img = new Image(BASE + "matches" + "/search.png", K.MATCHES.SEARCH_BTN_SIZE, 0, true, true);
			break;
		case M_SELECT_BTN:
			img = new Image(BASE + "matches" + "/select.png", 0, K.MATCHES.SEARCH_BTN_SIZE, true, true);
			break;
		default:
			System.out.println("Image of type " + i + " not found.");
			return;
		}
		
		images.put(i, img);
	}
	
	public ImageView getSeparatorBlack(double w, double h) {
		return new ImageView(new Image(BASE + "main" + "/separator_black.png", w, h, false, true));
	}
	
	public ImageView getSeparatorWhite(double w, double h) {
		return new ImageView(new Image(BASE + "main" + "/separator_white.png", w, h, false, true));
	}
	
	public static I getInstance() {
		if(instance == null) instance = new I();
		return instance;
	}
	
	public enum Type {
		TEAM_EVENTS_BTN, TEAM_EVENTS_BTN_CLICKED,
		MATCHES_BTN, MATCHES_BTN_CLICKED,
		COMING_SOON_BTN, COMING_SOON_BTN_CLICKED,
		MAIN_ICON,
		
		M_SEARCH_BTN, M_SELECT_BTN,
		
		TE_TEAM_LIST_BTN, TE_BACK_BTN, TE_ADD_BTN
	}
}
