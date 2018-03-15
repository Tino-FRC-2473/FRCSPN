package general.constants;

import gui.BoxPaddingInsets;

public class K {
	public static final String VERSION = "";
	public static final boolean DEBUG = true;
	
	public static final KMain MAIN = new KMain();
	public static final KTeamEvents TEAM_EVENTS = new KTeamEvents();
	public static final KMatches MATCHES = new KMatches();
	
	public static final BoxPaddingInsets getInsets() {
		return new BoxPaddingInsets();
	}
	public static final BoxPaddingInsets getInsets(int i) {
		return new BoxPaddingInsets(i);
	}
}
