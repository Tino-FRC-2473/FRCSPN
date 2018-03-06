package general.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import general.ScoutingApp;

public class RequestingThread extends Thread {
	public boolean available;
	
	private boolean debug;
	private final String BASE = "https://www.thebluealliance.com/api/v3/";
	
	public RequestingThread() {
		available = true;
		debug = general.constants.K.DEBUG;
	}
	
	public boolean isAvailable() { return available; }
	
	/**
	 * General request method that uses the HttpURLConnection class.
	 * @param req Any request
	 */
	public void requestAndStore(R req) {
		if(available) available = false;
		else System.out.println("SOMEHOW REQUESTANDSTORE WHILE NOT AVAILABLE");
		
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
		available = true;
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
	
	private void ifDebugPrintln(String s) {
		if(debug) System.out.println(s);
	}
	private void ifDebugPrint(String s) {
		if(debug) System.out.print(s);
	}
}
