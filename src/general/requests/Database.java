package general.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import models.*;
import models.event_status.EventStatus;
import models.matches.yr2017.*;
import models.matches.yr2018.*;

@SuppressWarnings("unused")
/**
 * A place for Requests and their responses to be stored. Parses each response
 * from a StringBuffer to its corresponding class upon call to a get method.
 * Additionally tracks which, and how many requests have not completed yet.
 */
public class Database {
	private Map<R, StringBuffer> database;
	private ArrayList<R> incomplete;
	private Gson gson;
	
	/**
	 * Default constructor.
	 */
	public Database() {
		database = new HashMap<R, StringBuffer>();
		incomplete = new ArrayList<R>();
		gson = new Gson();
	}
	
	/**
	 * Places a request object (R.class) and its returned value (StringBuffer.class) in
	 * the HashMap of this class.
	 * @param req The request.
	 * @param value The value of the request.
	 */
	public void put(R req, StringBuffer value) {
		database.put(req, value);
		boolean found = false;
		for(R r : incomplete) {
			if(r.equals(req)) {
				incomplete.remove(r);
				found = true;
				break;
			}
		}
		if(!found) System.out.println("incomplete request \"" + req + "\" not found");
	}
	
	/**
	 * The number of requests that have pending values to return for yet.
	 * @return Such number.
	 */
	public int getNumberIncompleteRequests() { return incomplete.size(); }
	
	/**
	 * Removes a request that failed from the incomplete request list.
	 * @param req Such a request.
	 */
	public void indicateRequestFailed(R req) {
		System.out.println("request " + req + " failed");
		boolean found = false;
		for(R r : incomplete) {
			if(r.equals(req)) {
				incomplete.remove(r);
				found = true;
				break;
			}
		}
		if(!found) System.out.println("incomplete request \"" + req + "\" not found");
	}
	
	/**
	 * Adds all pending requests that are not complete yet.
	 * @param reqs An ArrayList of such requests.
	 */
	public void putIncompleteRequests(ArrayList<R> reqs) {
		incomplete.addAll(reqs);
	}
	
	/**
	 * General method to get the value of a completed request. All other get methods
	 * call this one. Returns null if such a request has not completed yet.
	 * @param req
	 * @param clazz
	 * @return
	 */
	public <E>E generalGet(R req, Class<E> clazz) {
		for(R r : database.keySet()) {
			if(r.equals(req)) {
				return gson.fromJson(database.get(r).toString(), clazz);
			}
		}
		for(R r : incomplete) {
			if(r.equals(req)) {
				return null;
			}
		}
		System.out.println("Requested request \"" + req + "\" is not pending.");
		return null;
	}
	
	public StatusTBA getStatus() {
		return generalGet(new R(R.Type.STATUS), StatusTBA.class);
	}
	public String[] getEventKeysInYear(int y) {
		return generalGet(new R(R.Type.EVENT_KEYS_IN_YEAR, y), String[].class);
	}
	public Event getEventInfo(String e) {
		return generalGet(new R(R.Type.EVENT_GENERAL_INFO, e), Event.class);
	}
	public Event[] getEventsForTeamInYear(int t, int y) {
		return generalGet(new R(R.Type.EVENTS_FOR_TEAM_IN_YEAR, t, y), Event[].class);
	} 
	public Match_Steamworks[] getMatchesForTeamAtEvent(int t, String e) {
		return generalGet(new R(R.Type.MATCHES_FOR_TEAM_AT_EVENT, t, e), Match_Steamworks[].class);
	}
	public Team[] getTeamsAtEvent(String e) {
		return generalGet(new R(R.Type.TEAMS_AT_EVENT, e), Team[].class);
	}
	public EventStatus getStatusForTeamAtEvent(int t, String e) {
		return generalGet(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, t, e), EventStatus.class);
	}
	public Event[] getEventsInYear(int y) {
		return generalGet(new R(R.Type.EVENTS_IN_YEAR, y), Event[].class);
	}
	
	//debugging methods
	
	public void printLengths() {
		for(R r : database.keySet()) {
			System.out.println("\t" + r + " = " + database.get(r).toString().length());
		}
	}
	
	public void printIncomplete() {
		System.out.println("\tincomplete requests: " + incomplete.size());
	}
}
