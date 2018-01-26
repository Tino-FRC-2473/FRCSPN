package constants_and_images;

import java.util.HashMap;

import javafx.scene.image.Image;

public class I {
	private static I instance;
	private static HashMap<imgs, Image> images;
	private static final String base = "file:images/";
	
	private I() {
		images = new HashMap<imgs, Image>();
		
		images.put(imgs.TEAM_EVENTS_BTN,
				new Image(base + "main" + "/team_events.png", K.MAIN.BTN_LEN, 0, true, true));
		images.put(imgs.TEAM_EVENTS_BTN_CLICKED,
				new Image(base + "main" + "/team_events_clicked.png", K.MAIN.BTN_LEN, 0, true, true));
		images.put(imgs.COMING_SOON_BTN,
				new Image(base + "main" + "/coming_soon.png", K.MAIN.BTN_LEN, 0, true, true));
		images.put(imgs.TE_TEAM_LIST_BTN,
				new Image(base + "team_events" + "/team_list.png", K.TEAM_EVENTS.TEAM_LIST_WIDTH, 0, true, true));
		images.put(imgs.TE_BACK_BTN,
				new Image(base + "team_events" + "/back.png", K.TEAM_EVENTS.TEAM_LIST_WIDTH, 0, true, true));
	}
	
	public Image getImg(imgs i) {
		return images.get(i);
    }
	
	public static I getInstance() {
		if(instance == null) instance = new I();
		return instance;
	}
	
	public enum imgs {
		TEAM_EVENTS_BTN, TEAM_EVENTS_BTN_CLICKED, COMING_SOON_BTN,
		TE_TEAM_LIST_BTN, TE_BACK_BTN
	}
}
