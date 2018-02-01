package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;

import models.*;
import models.YR2017_Steamworks.*;
import models.YR2018_PowerUp.*;

/*
/team/{team_key}/event/{event_key}/status

File f = new File(filePathString);
if(f.exists() && !f.isDirectory()) { 
    // do something
}
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
			
			BufferedReader reader = null;
			StringBuffer response = new StringBuffer();
			
			if (c.getResponseCode() == 200) { //updated or new
				ifDebugPrintln(""+200);
				reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
//				PrintWriter writer = new PrintWriter("data/" + str.substring(BASE.length()).replaceAll("/", "_") + ".txt");
//				writer.write(c.getHeaderField("Last-Modified") + "\n");
//				
//				String line;
//				while((line = reader.readLine()) != null) {
//					response.append(line);
//					writer.write(line + "\n");
//				}
//				writer.close();
			} else if(c.getResponseCode() == 304) { //not modified
				ifDebugPrintln(""+304);
				reader = new BufferedReader(new FileReader(
						new File("data/" + str.substring(BASE.length()).replaceAll("/", "_") + ".txt")
				));
				
			} else {
				System.out.println("Unexpected response code: " + c.getResponseCode());
			}
			
			String line;
			boolean first = (c.getResponseCode() == 304);
			while((line = reader.readLine()) != null) {
				if(first) first = false;
				else response.append(line);
			}
			
			reader.close();
			ifDebugPrintln(response.toString());
			
			return gson.fromJson(response.toString(), clazz);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private HttpURLConnection getConnection(String s) throws IOException {
		System.out.println(getTimeStamp());
		HttpURLConnection con = (HttpURLConnection) new URL(s).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
//		con.setRequestProperty("If-Modified-Since", getTimeStamp());
		ifDebugPrintln("Sending 'GET' request to URL: " + s);
		int responseCode = con.getResponseCode();
		if (responseCode == 200 || responseCode == 304) {
			ifDebugPrintln("HTTP Connected");
			return con;
		} else {
			return null;
		}
	}

	private String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(new Date());
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