package general.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import general.ScoutingApp;

/*
File f = new File(filePathString);
if(f.exists() && !f.isDirectory()) { 
    // do something
}
 */

public class RequesterThread extends Thread {
	private ArrayList<R> requests;
	private boolean alive;
	private boolean debug;
	
	private final String BASE = "https://www.thebluealliance.com/api/v3/";
	
	public RequesterThread() {
		debug = general.constants.K.DEBUG;
		requests = new ArrayList<R>();
		alive = true;
	}
	
	@Override
	public void run() {
		while(alive) {
			try {
				long startTime = System.currentTimeMillis();
				ScoutingApp.getDatabase().putIncompleteRequests(requests);
				while(requests.size() > 0) {
					requestAndStore(requests.remove(0));
				}
				
				Thread.sleep(Math.max(0, 20 - (System.currentTimeMillis()-startTime)));
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		System.out.println(this.getClass().getSimpleName() + " loop ended");
	}
	
	public void requestAndStore(R req) {
		try {
			HttpURLConnection c = getConnection(req);
			if(c != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuffer response = new StringBuffer();
				
				String line;
				while((line = reader.readLine()) != null) {
					response.append(line + "\n");
				}
				
				reader.close();
				ifDebugPrintln("Response Length: " + response.toString().length() + "\n");
				
				ScoutingApp.getDatabase().put(req, response);
			} else {
				ScoutingApp.getDatabase().indicateRequestFailed(req);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private HttpURLConnection getConnection(R req) throws IOException {
//		System.out.println(getTimeStamp());
		HttpURLConnection con = (HttpURLConnection) new URL(BASE + req).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
//		con.setRequestProperty("If-Modified-Since", getTimeStamp());
		ifDebugPrintln("Sending 'GET' request to URL: " + (BASE+req));
		int responseCode = con.getResponseCode();
		if (responseCode == 200 || responseCode == 304) {
			ifDebugPrintln("HTTP Connected");
			return con;
		} else {
			ifDebugPrintln("Unknown Response Code: " + responseCode);
			return null;
		}
	}
	
	public void addRequest(R r) { requests.add(r); }
	public void addRequestStatus() { addRequest(new R(R.Type.STATUS)); }
	public void addRequestEventKeysInYear(int y) { addRequest(new R(R.Type.EVENT_KEYS_IN_YEAR, y)); }
	public void addRequestEventGeneralInfo(String e) { addRequest(new R(R.Type.EVENT_GENERAL_INFO, e)); }
	public void addRequestEventsForTeamInYear(int t, int y) { addRequest(new R(R.Type.EVENTS_FOR_TEAM_IN_YEAR, t, y)); } 
	public void addRequestMatchesForTeamAtEvent(int t, String e) { addRequest(new R(R.Type.MATCHES_FOR_TEAM_AT_EVENT, t, e)); }
	public void addRequestTeamsAtEvent(String e) { addRequest(new R(R.Type.TEAMS_AT_EVENT, e)); }
	public void addRequestStatusForTeamAtEvent(int t, String e) { addRequest(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, t, e)); }
	
	public void end() {
		System.out.println(this.getClass().getSimpleName() + " ending");
		alive = false;
	}
	
	private void ifDebugPrintln(String s) {
		if(debug) System.out.println(s);
	}
}
