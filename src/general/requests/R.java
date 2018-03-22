package general.requests;

/**
 * A class that represents a request, including its type and any parameters.
 * This class additionally includes an enum to determine type.
 */
public class R {
	private Type type;
	private String[] params;
	
	/**
	 * General constructor. All other constructors call this one,
	 * but just have Strings or ints passed into the parameter array.
	 * @param t Type of request to be made
	 * @param p Array of parameters
	 */
	public R(Type t, String[] p) {
		type = t;
		params = p;
	}
	
	public R(Type t) { this(t, new String[0]); }
	
	public R(Type t, String p1) { this(t, new String[] {p1}); }
	public R(Type t, int p1) { this(t, new String[] {""+p1}); }
	
	public R(Type t, String p1, String p2) { this(t, new String[] {p1, p2}); }
	public R(Type t, int p1, String p2) { this(t, new String[] {""+p1, p2}); }
	public R(Type t, String p1, int p2) { this(t, new String[] {p1, ""+p2}); }
	public R(Type t, int p1, int p2) { this(t, new String[] {""+p1, ""+p2}); }
	
	public R(Type t, String p1, String p2, String p3) { this(t, new String[] {p1, p2, p3}); }
	public R(Type t, int p1, String p2, String p3) { this(t, new String[] {""+p1, p2, p3}); }
	public R(Type t, String p1, int p2, String p3) { this(t, new String[] {p1, ""+p2, p3}); }
	public R(Type t, String p1, String p2, int p3) { this(t, new String[] {p1, p2, ""+p3}); }
	public R(Type t, String p1, int p2, int p3) { this(t, new String[] {p1, ""+p2, ""+p3}); }
	public R(Type t, int p1, String p2, int p3) { this(t, new String[] {""+p1, p2, ""+p3}); }
	public R(Type t, int p1, int p2, String p3) { this(t, new String[] {""+p1, ""+p2, p3}); }
	public R(Type t, int p1, int p2, int p3) { this(t, new String[] {""+p1, ""+p2, ""+p3}); }
	
	@Override
	public String toString() {
		switch(type) {
		case EVENTS_FOR_TEAM_IN_YEAR:
			return "team/" + getTeamKey(params[0]) + "/events/" + params[1] + "/simple";
		case EVENT_GENERAL_INFO:
			return "event/" + params[0] + "/simple";
		case EVENT_KEYS_IN_YEAR:
			return "events/" + params[0] + "/keys";
		case MATCHES_AT_EVENT:
			return "event/" + params[0] + "/matches";
		case STATUS:
			return "status";
		case TEAMS_AT_EVENT:
			return "event/" + params[0] + "/teams/simple";
//		case MATCHES_FOR_TEAM_AT_EVENT:
//			return "team/" + getTeamKey(params[0]) + "/event/" + params[1] + "/matches";
		case STATUS_FOR_TEAM_AT_EVENT:
			return "team/" + getTeamKey(params[0]) + "/event/" + params[1] + "/status";
		case STATUSES_FOR_TEAM_IN_YEAR:
			return "team/" + getTeamKey(params[0]) + "/events/" + params[1] + "/statuses";
		case EVENTS_IN_YEAR:
			return "events/" + params[0] + "/simple";
		case MATCH:
			return "match/" + params[0];
		case MATCH_KEYS_FOR_EVENT:
			return "event/" + params[0] + "/matches/keys";
		case AWARDS_AT_EVENT:
			return "event/" + params[0] + "/awards";
		default:
			return "Unknown Request Type: " + type;
		}
	}
	
	public Type getType() {
		return type;
	}
	
	//allows for a team to input either "frc2473" or "2473" but to have it end up as "frc2473" regardless.
	private String getTeamKey(String s) { return (s.substring(0, Math.min(s.length(), 3)).equals("frc")) ? s : "frc"+s; }
	
	/**
	 * Enum representing types of requests.
	 */
	public enum Type {
		STATUS,
		EVENT_KEYS_IN_YEAR,
		TEAMS_AT_EVENT,
		EVENT_GENERAL_INFO,
		EVENTS_FOR_TEAM_IN_YEAR,
		STATUS_FOR_TEAM_AT_EVENT,
		EVENTS_IN_YEAR,
		MATCH,
		MATCH_KEYS_FOR_EVENT,
		AWARDS_AT_EVENT,
		STATUSES_FOR_TEAM_IN_YEAR,
		
		MATCHES_FOR_TEAM_AT_EVENT, MATCHES_AT_EVENT;
	}
	
	public boolean equals(R other) {
		return this.toString().equals(other.toString());
	}
	
}
