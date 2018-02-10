package general.images;

import java.util.HashMap;

import general.constants.K;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class I {
	private static I instance;
	private static HashMap<Type, Image> images;
	private static final String base = "file:images/";
	
	private I() {
		images = new HashMap<Type, Image>();
		
		images.put(Type.TEAM_EVENTS_BTN,
				new Image(base + "main" + "/team_events.png", K.MAIN.BTN_LEN, 0, true, true));
		images.put(Type.TEAM_EVENTS_BTN_CLICKED,
				new Image(base + "main" + "/team_events_clicked.png", K.MAIN.BTN_LEN, 0, true, true));
		images.put(Type.COMING_SOON_BTN,
				new Image(base + "main" + "/coming_soon.png", K.MAIN.BTN_LEN, 0, true, true));
		images.put(Type.TE_TEAM_LIST_BTN,
				new Image(base + "team_events" + "/edit_teams.png", K.TEAM_EVENTS.EDIT_TEAMS_WIDTH, 0, true, true));
		images.put(Type.TE_BACK_BTN,
				new Image(base + "team_events" + "/back.png", K.TEAM_EVENTS.EDIT_TEAMS_WIDTH, 0, true, true));
		images.put(Type.TE_ADD_BTN,
				new Image(base + "team_events" + "/add.png", K.TEAM_EVENTS.EDIT_TEAMS_WIDTH, 0, true, true));
	}
	
	public Image getImg(Type i) {
		return images.get(i);
    }
	
	public ImageView getSeparator(double w, double h) {
		return new ImageView(new Image(base + "main" + "/separator.png", w, h, false, true));
	}
	
	public static I getInstance() {
		if(instance == null) instance = new I();
		return instance;
	}
	
	public enum Type {
		TEAM_EVENTS_BTN, TEAM_EVENTS_BTN_CLICKED, COMING_SOON_BTN, COMING_SOON_BTN_CLICKED,
		TE_TEAM_LIST_BTN, TE_BACK_BTN, TE_ADD_BTN
	}
}
