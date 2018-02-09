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
public class Database {
	private Map<R, StringBuffer> database;
	private ArrayList<R> incomplete;
	private Gson gson;
	
	public Database() {
		database = new HashMap<R, StringBuffer>();
		incomplete = new ArrayList<R>();
		gson = new Gson();
	}
	
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
		if(!found) System.out.println("request " + req + " not found");
	}
	
	public int getNumberIncompleteRequests() { return incomplete.size(); }
	
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
		if(!found) System.out.println("request " + req + " not found");
	}
	
	public void putIncompleteRequests(ArrayList<R> reqs) {
		incomplete.addAll(reqs);
	}
	
	public <E>E generalGet(R req, Class<E> clazz) {
		for(R r : database.keySet()) {
			if(r.equals(req)) {
				return gson.fromJson(database.get(r).toString(), clazz);
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
	public Match_Steamworks[] getMatchesForTeamAtEvent(int t, String e) {
		return generalGet(new R(R.Type.MATCHES_FOR_TEAM_AT_EVENT, t, e), Match_Steamworks[].class);
	}
	public Team[] getTeamsAtEvent(String e) {
		return generalGet(new R(R.Type.TEAMS_AT_EVENT, e), Team[].class);
	}
	public EventStatus getStatusForTeamAtEvent(int t, String e) {
		return generalGet(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, t, e), EventStatus.class);
	}
	
	public void printLengths() {
		for(R r : database.keySet()) {
			System.out.println("\t" + r + " = " + database.get(r).toString().length());
		}
	}
	
	public void printIncomplete() {
		System.out.println("\tincomplete requests: " + incomplete.size());
	}
}
