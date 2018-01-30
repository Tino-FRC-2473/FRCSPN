package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

	public <E> E generalRequest(String str, Class<E> clazz) {
		try {
			HttpURLConnection c = getConnection(str);
			BufferedReader in = null;
			StringBuffer response = null;
			if (c == null)return null;
			if (c.getResponseCode() == 200) {
				in = new BufferedReader(new InputStreamReader(c.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				PrintWriter writer = new PrintWriter(removeDash("data\\"+str.substring(BASE.length())+".txt"), "UTF-8");
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
					writer.write(inputLine);
				}
				writer.close();
			}else if(c.getResponseCode() == 304) {
				in = new BufferedReader(new FileReader(new File("data\\"+str.substring(BASE.length()))));
				
			}
			in.close();
			System.out.println(response.toString());
			return gson.fromJson(response.toString(), clazz);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private HttpURLConnection getConnection(String s) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(s).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		// con.setRequestProperty("If-Modified-Since", "Mon, 15 Jan 2018 03:33:54 GMT");
		ifDebugPrintln("Sending 'GET' request to URL: " + s);
		int responseCode = con.getResponseCode();
		if ((responseCode) == 200 || (responseCode) == 304) {
			ifDebugPrintln("HTTP Connected");
			System.out.println(con.getHeaderField("Last-Modified"));
			return con;
		} else {
			return null;
		}
	}
	
	private String removeDash(String str) {
		for(int i = 5; i < str.length(); i++) {
			if(str.substring(i, i+1).equals("\\")) {
				str = str.substring(0, i) + "-" + str.substring(i+1);
			}
		}
		return str;
	}

	private void ifDebugPrintln(String s) {
		if (debug)
			System.out.println(s);
	}

	public Status getTBAStatus() {
		return this.<Status>generalRequest(BASE + "status", Status.class);
	}

	public String[] eventKeysForYear(int yr) {
		return this.<String[]>generalRequest(BASE + "events/" + yr + "/keys", String[].class);
	}

	public Match_2017Steamworks[] getMatchesAt2017Event(String event) {
		if (!event.substring(0, 4).equals("2017"))
			return null;
		return this.<Match_2017Steamworks[]>generalRequest(BASE + "event/" + event + "/matches",
				Match_2017Steamworks[].class);
	}

	public Match_2018PowerUp[] getMatchesAt2018Event(String event) {
		if (!event.substring(0, 4).equals("2018"))
			return null;
		return this.<Match_2018PowerUp[]>generalRequest(BASE + "event/" + event + "/matches",
				Match_2018PowerUp[].class);
	}

	public Team[] getTeamsAtEvent(String event) {
		return this.<Team[]>generalRequest(BASE + "event/" + event + "/teams/simple", Team[].class);
	}

	public Event getEventInfo(String key) {
		return this.<Event>generalRequest(BASE + "event/" + key + "/simple", Event.class);
	}

	public Event[] getTeamEventsForYear(int team, int year) {
		return this.<Event[]>generalRequest(BASE + "team/frc" + team + "/events/" + year + "/simple", Event[].class);
	}
}