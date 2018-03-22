package general.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

import models.Award;
import models.Event;
import models.StatusTBA;
import models.Team;
import models.event_status.EventStatus;
import models.matches.yr2017.Match_Steamworks;
import models.matches.yr2018.Match_PowerUp;

/**
 * A place for Requests and their responses to be stored. Parses each response
 * from a StringBuffer to its corresponding class upon call to a get method.
 * Additionally tracks which, and how many requests have not completed yet.
 */
public class Database {
	private Map<R, BufferedReader> database;
	private ArrayList<R> incomplete;
	private Gson gson;
	
	/**
	 * Default constructor.
	 */
	public Database() {
		database = new ConcurrentHashMap<R, BufferedReader>();
		incomplete = new ArrayList<R>();
		gson = new Gson();
	}
	
	/**
	 * Places a request object (R.class) and its returned value (StringBuffer.class) in
	 * the HashMap of this class.
	 * @param req The request.
	 * @param value The value of the request.
	 */
	public void put(R req, BufferedReader value) {
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
	
	public boolean isIncomplete(R req) {
		for(R r : incomplete) {
			if(r.equals(req)) {
				return true;
			}
		}
		return false;
	}
	
	
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
		if(!found) System.out.println("failed incomplete request \"" + req + "\" not found");
	}
	
	/**
	 * Adds all pending requests that are not complete yet.
	 * @param reqs An ArrayList of such requests.
	 */
	public void putIncompleteRequests(ArrayList<R> reqs) {
		incomplete.addAll(reqs);
	}
	
	public void putIncompleteRequest(R req) {
		incomplete.add(req);
	}
	
//	public StringBuffer getRaw(R req) {
//		for(R r : database.keySet()) {
//			if(r.equals(req)) {
//				return database.get(r);
//			}
//		}
//		return null;
//	}
	
	/**
	 * General method to get the value of a completed request. All other get methods
	 * call this one. Returns null if such a request has not completed yet.
	 * @param req
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E>E generalGet(R req, Class<E> clazz) {
		for(final Iterator<R> itr = database.keySet().iterator(); itr.hasNext();) {
			R r = itr.next();
			if(r.equals(req)) {
				BufferedReader reader = database.get(r);
				
				if(r.getType() == R.Type.MATCHES_AT_EVENT) {
					//PARSE INFO FROM THE BUFFER INTO - SAY BY FINDING LINES WITH ONLY '{' OR '}' IDK
					//PUT THIS IN AN ARRAYLIST OF STRING BUFFERS, THEN FOR EACH THING IN THE STRING BUFFER,
					//ADD EACH INDIVIDUAL JSON.FROMJSON PARSING TO ANOTHER ARRAYLIST
					
					ArrayList<Match_PowerUp> individualMatches = new ArrayList<Match_PowerUp>();
					
					StringBuffer response = new StringBuffer();
					String line;
					try {
						while((line = reader.readLine()) != null) {
							individualMatches.add((Match_PowerUp) gson.fromJson(response.toString(), clazz));
						}
						return (E) individualMatches;
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else {
					StringBuffer response = new StringBuffer();
					
					String line;
					try {
						while((line = reader.readLine()) != null) {
							response.append(line + "\n");
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
					
					return gson.fromJson(response.toString(), clazz);
				}
			}
		}
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
	public Team[] getTeamsAtEvent(String e) {
		return generalGet(new R(R.Type.TEAMS_AT_EVENT, e), Team[].class);
	}
	public EventStatus getStatusForTeamAtEvent(int t, String e) {
		return generalGet(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, t, e), EventStatus.class);
	}
	public HashMap<String, EventStatus> getStatusesForTeamInYear(int t, int y) {
		return generalGet(new R(R.Type.STATUSES_FOR_TEAM_IN_YEAR, t, y), HashMap.class);
	}
	public Event[] getEventsInYear(int y) {
		return generalGet(new R(R.Type.EVENTS_IN_YEAR, y), Event[].class);
	}
	public String[] getMatchKeysForEvent(String e) {
		return generalGet(new R(R.Type.MATCH_KEYS_FOR_EVENT, e), String[].class);
	}
	public Award[] getAwardsAtEvent(String e) {
		return generalGet(new R(R.Type.AWARDS_AT_EVENT, e), Award[].class);
	}
	public Match_Steamworks get2017Match(String k) {
		return generalGet(new R(R.Type.MATCH, k), Match_Steamworks.class);
	}
	public Match_PowerUp get2018Match(String k) {
		return generalGet(new R(R.Type.MATCH, k), Match_PowerUp.class);
	}
	
	public Match_Steamworks[] getMatches2017ForEvent(String e) {
		ArrayList<Match_Steamworks> arr = new ArrayList<Match_Steamworks>();
		for(String key : getMatchKeysForEvent(e)) {
			arr.add(get2017Match(key));
		}
		return arr.toArray(new Match_Steamworks[getMatchKeysForEvent(e).length]);
	}
	
	public Match_PowerUp[] getMatches2018ForEvent(String e) {
		ArrayList<Match_PowerUp> arr = new ArrayList<Match_PowerUp>();
		for(String key : getMatchKeysForEvent(e)) {
			arr.add(get2018Match(key));
		}
		return arr.toArray(new Match_PowerUp[getMatchKeysForEvent(e).length]);
	}
	
//	public Match_Steamworks[] getMatches2017ForTeamAtEvent(int t, String e) {
//		return generalGet(new R(R.Type.MATCHES_FOR_TEAM_AT_EVENT, t, e), Match_Steamworks[].class);
//	}
//	public Match_PowerUp[] getMatches2018ForTeamAtEvent(int t, String e) {
//		return generalGet(new R(R.Type.MATCHES_FOR_TEAM_AT_EVENT, t, e), Match_PowerUp[].class);
//	}
	
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
