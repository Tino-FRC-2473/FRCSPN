package general.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import models.matches.yr2018.Match_PowerUp;

public class Response {
	private BufferedReader reader;
	private StringBuffer response;
	private Response[] extendedResponses;
	
	public Response(BufferedReader br) {
		reader = br;
		response = null;
		extendedResponses = null;
	}
	
	public Response(StringBuffer resp) {
		reader = null;
		response = resp;
		extendedResponses = null;
	}
	
	public BufferedReader getReader() { return reader; }
	
	public void putResponse(StringBuffer resp) {
		response = resp;
		reader = null;
	}
	
	public boolean hasBeenRead() {
		return reader == null;
	}
	
	public Match_PowerUp[] updateMatchesFromReader(Gson gson) {
		if(reader != null) {
			ArrayList<Match_PowerUp> matches = new ArrayList<Match_PowerUp>();
			String line;
			StringBuffer str = new StringBuffer();
			ArrayList<Response> resps = new ArrayList<Response>();
			try {
				while((line = reader.readLine()) != null) {
					if(line.length() > 3) {
						line = line.substring(2); //un-indent line
						
						if(line.charAt(0) == '}') {
							resps.add(new Response(str.append("}\n")));
							matches.add(gson.fromJson(str.toString(), Match_PowerUp.class));
							str = new StringBuffer();
						} else {
							str.append(line + "\n");
						}
					} else if(str.length() == 0) {
						str.append("{\n");
					}
				}
				extendedResponses = resps.toArray(new Response[resps.size()]);
				return matches.toArray(new Match_PowerUp[matches.size()]);
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			Match_PowerUp[] matches = new Match_PowerUp[extendedResponses.length];
			for (int i = 0; i < matches.length; i++) {
				matches[i] = extendedResponses[i].updateFromReader(gson,Match_PowerUp.class);
			}
			return matches;
		}
		return null;
	}
	
	public <E>E updateFromReader(Gson gson, Class<E> clazz) {
		if(reader != null) {
			response = new StringBuffer();
			
			String line;
			try {
				while((line = reader.readLine()) != null) {
					response.append(line + "\n");
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			reader = null;
		}
		return gson.fromJson(response.toString(), clazz);
	}
	
	@Override
	public String toString() {
		return response == null ? "" : response.toString();
	}
}
