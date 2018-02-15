package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import models.*;
import models.matches.yr2017.*;
import models.matches.yr2018.*;


@Deprecated
public class Requester {
	private Gson gson;
	private boolean debug;
	
	private final String BASE = "https://www.thebluealliance.com/api/v3/";

	public Requester(boolean d) {
		gson = new Gson();
		debug = d;
	}
	
	private HttpURLConnection getConnection(String s) throws IOException {
//		System.out.println(getTimeStamp());
		HttpURLConnection con = (HttpURLConnection) new URL(s).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
//		con.setRequestProperty("If-Modified-Since", getTimeStamp());
		ifDebugPrintln("Sending 'GET' request to URL: " + s);
		int responseCode = con.getResponseCode();
		if (responseCode == 200 || responseCode == 304) {
			ifDebugPrintln("HTTP Connected");
			return con;
		} else {
			ifDebugPrintln("Unknown Response Code: " + responseCode);
			return null;
		}
	}
	
	//THE BELOW METHODS ARE DEPRECATED IN FAVOR OF THE DATABASING SYSTEM WITH THREADING
	
	@Deprecated
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
	
	@Deprecated
	public StatusTBA getTBAStatus() {
		return this.<StatusTBA>generalRequest(BASE + "status", StatusTBA.class);
	}

	@Deprecated
	public String[] eventKeysForYear(int yr) {
		return this.<String[]>generalRequest(BASE + "events/" + yr + "/keys", String[].class);
	}
	
	@Deprecated
	public Match_Steamworks[] getMatchesAt2017Event(String event) {
		if (!event.substring(0, 4).equals("2017"))
			return null;
		return this.<Match_Steamworks[]>generalRequest(BASE + "event/" + event + "/matches",
				Match_Steamworks[].class);
	}

	@Deprecated
	public Match_PowerUp[] getMatchesAt2018Event(String event) {
		if (!event.substring(0, 4).equals("2018"))
			return null;
		return this.<Match_PowerUp[]>generalRequest(BASE + "event/" + event + "/matches",
				Match_PowerUp[].class);
	}

	@Deprecated
	public Team[] getTeamsAtEvent(String event) {
		return this.<Team[]>generalRequest(BASE + "event/" + event + "/teams/simple", Team[].class);
	}

	@Deprecated
	public Event getEventInfo(String key) {
		return this.<Event>generalRequest(BASE + "event/" + key + "/simple", Event.class);
	}

	@Deprecated
	public Event[] getTeamEventsForYear(int team, int year) {
		return this.<Event[]>generalRequest(BASE + "team/frc" + team + "/event/" + year + "/simple", Event[].class);
	}
	
	private void ifDebugPrintln(String s) {
		if(debug) System.out.println(s);
	}
}