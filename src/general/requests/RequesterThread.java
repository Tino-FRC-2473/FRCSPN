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

/**
 * A separate Thread that allows for requests in the form of HttpURLConnections to carry out
 * without stalling the entire code. Continuously requests all requests that have not been
 * requested yet.
 */
public class RequesterThread extends Thread {
	private ArrayList<R> requests;
	private boolean alive;
	private boolean debug;
	
	private final String BASE = "https://www.thebluealliance.com/api/v3/";
	
	/**
	 * Default constructor.
	 */
	public RequesterThread() {
		debug = general.constants.K.DEBUG;
		requests = new ArrayList<R>();
		alive = true;
	}
	
	/**
	 * Requests all unrequested requests and indicates to the Database that they are incomplete.
	 */
	@Override
	public void run() {
		while(alive) {
			try {
//				long startTime = System.currentTimeMillis();
				
				if(requests.size() > 0) {
					ScoutingApp.getDatabase().putIncompleteRequest(requests.get(0));
					requestAndStore(requests.remove(0));
				}
				
				Thread.sleep(10);
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		System.out.println(this.getClass().getSimpleName() + " loop ended");
	}
	
	/**
	 * General request method that uses the HttpURLConnection class.
	 * @param req Any request
	 */
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
				ifDebugPrintln(" - Response Length: " + response.toString().length() + "\n");
				
				ScoutingApp.getDatabase().put(req, response);
			} else {
				ScoutingApp.getDatabase().indicateRequestFailed(req);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a connection which has connected with the online TBA server. Returns null if
	 * the request receives an invalid response code.
	 * @param req The request to obtain a connection for.
	 * @return The HttpURLConnection for the request.
	 * @throws IOException If the connection fails.
	 */
	private HttpURLConnection getConnection(R req) throws IOException {
//		System.out.println(getTimeStamp());
		HttpURLConnection con = (HttpURLConnection) new URL(BASE + req).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
		con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
//		con.setRequestProperty("If-Modified-Since", getTimeStamp());
		ifDebugPrintln("Requesting \"" + req + "\"");
		int responseCode = con.getResponseCode();
		if (responseCode == 200 || responseCode == 304) {
			ifDebugPrint("HTTP Connected");
			return con;
		} else {
			ifDebugPrintln("Unknown Response Code: " + responseCode);
			return null;
		}
	}
	
	/**
	 * General method to add a request to be called. All other add methods call
	 * this one and generate the request (R.class) inside.
	 * @param r The request to be added
	 */
	public void addRequest(R r) { requests.add(r); }
	public void addRequestStatus() { addRequest(new R(R.Type.STATUS)); }
	public void addRequestEventKeysInYear(int y) { addRequest(new R(R.Type.EVENT_KEYS_IN_YEAR, y)); }
	public void addRequestEventGeneralInfo(String e) { addRequest(new R(R.Type.EVENT_GENERAL_INFO, e)); }
	public void addRequestEventsForTeamInYear(int t, int y) { addRequest(new R(R.Type.EVENTS_FOR_TEAM_IN_YEAR, t, y)); } 
	public void addRequestTeamsAtEvent(String e) { addRequest(new R(R.Type.TEAMS_AT_EVENT, e)); }
	public void addRequestStatusForTeamAtEvent(int t, String e) { addRequest(new R(R.Type.STATUS_FOR_TEAM_AT_EVENT, t, e)); }
	public void addRequestEventsInYear(int y) { addRequest(new R(R.Type.EVENTS_IN_YEAR, y)); }
	public void addRequestMatchKeysForEvent(String e) { addRequest(new R(R.Type.MATCH_KEYS_FOR_EVENT, e)); }
	public void addRequestMatch(String k) { addRequest(new R(R.Type.MATCH, k)); }
	public void addRequestAwardsAtEvent(String e) { addRequest(new R(R.Type.AWARDS_AT_EVENT, e)); }

//	public void addRequestMatchesForTeamAtEvent(int t, String e) { addRequest(new R(R.Type.MATCHES_FOR_TEAM_AT_EVENT, t, e)); }
	
	/**
	 * Ends the thread.
	 */
	public void end() {
		System.out.println(this.getClass().getSimpleName() + " ending");
		alive = false;
	}
	
	private void ifDebugPrintln(String s) {
		if(debug) System.out.println(s);
	}
	private void ifDebugPrint(String s) {
		if(debug) System.out.print(s);
	}
}
