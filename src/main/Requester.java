package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import models.*;
import models.YR2017_Steamworks.*;
import models.YR2018_PowerUp.*;

/*
 * /team/{team_key}/event/{event_key}/status
 */

public class Requester {
	private boolean debug;
	private Gson gson;
	private final String BASE = "https://www.thebluealliance.com/api/v3/";
	
	public Requester(boolean d) {
		debug = d;
		gson = new Gson();
	}
	
	public <E>E generalRequest(String str, Class<E> clazz) {
		try {
			HttpURLConnection c = getConnection(str);
			if(c == null) return null;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return gson.fromJson(response.toString(), clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private HttpURLConnection getConnection(String s) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(s).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		ifDebugPrintln("Sending 'GET' request to URL: " + s);
		int responseCode;
		if((responseCode = con.getResponseCode()) == 200) {
			ifDebugPrintln("HTTP Connected");
			return con;
		} else {
			ifDebugPrintln("Response Code: " + responseCode);
			return null;
		}
	}
	
	private void ifDebugPrintln(String s) {
		if(debug) System.out.println(s);
	}
	
	public Status getTBAStatus() {
		return this.<Status>generalRequest(BASE+"status", Status.class);
	}
	
	public String[] eventKeysForYear(int yr) {
		return this.<String[]>generalRequest(BASE+"events/"+yr+"/keys", String[].class);
	}
	
	public Match_2017Steamworks[] getMatchesAt2017Event(String event) {
		if(!event.substring(0, 4).equals("2017")) return null;
		return this.<Match_2017Steamworks[]>generalRequest(BASE+"event/"+event+"/matches", Match_2017Steamworks[].class);
	}
	
	public Match_2018PowerUp[] getMatchesAt2018Event(String event) {
		if(!event.substring(0, 4).equals("2018")) return null;
		return this.<Match_2018PowerUp[]>generalRequest(BASE+"event/"+event+"/matches", Match_2018PowerUp[].class);
	}
	
	public String[] getTeamEventsForYear(int team, int yr) {
		return this.<String[]>generalRequest(BASE+"team/frc"+team+"/events/"+yr+"/keys", String[].class);
	}
	
	public Team[] getTeamsAtEvent(String event) {
		return this.<Team[]>generalRequest(BASE+"event/"+event+"/teams/simple", Team[].class);
	}
	
	public Event getEventInfo(String key) {
		return this.<Event>generalRequest(BASE+"event/"+key+"/simple", Event.class);
	}
}