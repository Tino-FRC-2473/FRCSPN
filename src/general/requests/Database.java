package general.requests;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import models.*;
import models.YR2017.*;
import models.YR2018.*;

@SuppressWarnings("unused")
public class Database {
	private Map<R, StringBuffer> database;
	private Gson gson;
	
	public Database() {
		database = new HashMap<R, StringBuffer>();
		gson = new Gson();
	}
	
	public void put(R r, StringBuffer value) {
		database.put(r, value);
	}
	
	public <E>E generalGet(R req, Class<E> clazz) {
		for(R r : database.keySet()) {
			if(r.equals(req)) {
				return gson.fromJson(database.get(r).toString(), clazz);
			}
		}
		return null;
	}
	
	public Status getStatus() {
		return generalGet(new R(R.Type.STATUS), Status.class);
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
	
	public void printLengths() {
		for(R r : database.keySet()) {
			System.out.println("\t" + r + " = " + database.get(r).toString().length());
		}
	}
}
