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
 * A place for Requests and their responses to be stored. Parses each response from a
 * BufferedReader to a StringBuffer to its corresponding class when a get method is
 * called. Additionally tracks which, and how many requests have not completed yet.
 */
public class Database {
	private Map<R, Response> database;
	private ArrayList<R> incomplete;
	private Gson gson;
	
	/**
	 * Default constructor.
	 */
	public Database() {
		database = new ConcurrentHashMap<R, Response>();
		incomplete = new ArrayList<R>();
		gson = new Gson();
	}
	
	/**
	 * Stores a request object (R) and its returned value (BufferedReader) in this class.
	 * @param req The request.
	 * @param value The value of the request.
	 */
	public void put(R req, BufferedReader read) {
		System.out.println("\tput " + req);
		database.put(req, new Response(read));
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
				System.out.println("\tFOUND");
				Response response = database.get(r);
				
				if(r.getType() == R.Type.MATCHES_AT_EVENT) {
					ArrayList<Match_PowerUp> matches = new ArrayList<Match_PowerUp>();
					BufferedReader reader = response.getReader();
					String line;
					StringBuffer str = new StringBuffer();
					try {
						while((line = reader.readLine()) != null) {
							if(line.length() > 3) {
								line = line.substring(2); //un-indent line
								
								if(line.charAt(0) == '}') {
									matches.add(gson.fromJson(str.append("}\n").toString(), Match_PowerUp.class));
									str = new StringBuffer();
								} else {
									str.append(line + "\n");
								}
							} else if(str.length() == 0) {
								str.append("{\n");
							}
						}
						return (E) matches.toArray(new Match_PowerUp[matches.size()]);
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("alt");
					return response.updateFromReader(gson, clazz);
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
		System.out.println("1");
		return generalGet(new R(R.Type.TEAMS_AT_EVENT, e), Team[].class);
	}
	public EventStatus getStatusForTeamAtEvent(int t, String e) {
		return generalGet(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, t, e), EventStatus.class);
	}
	@SuppressWarnings("unchecked")
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
		return generalGet(new R(R.Type.MATCHES_AT_EVENT, e), Match_PowerUp[].class);
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
