package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import com.google.gson.Gson;

import models.YR2017.Match_Steamworks;

public class Test3_IterateMatches {
	public static void main(String[] args) {
		try {
			int year = 2017;
			URL url = new URL("https://www.thebluealliance.com/api/v3/events/"+year+"/keys");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
			con.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
			System.out.println("Sending 'GET' request to URL: " + url);
			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer eventsResponse = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				eventsResponse.append(inputLine);
			}
			in.close();
			
			Gson gson = new Gson();
			String[] events = gson.fromJson(eventsResponse.toString(), String[].class);
			System.out.println("JSON Object");
			System.out.println(events);
			System.out.println("String");
			System.out.println(Arrays.asList(events) + "\n");
			
			for(int i = 0; i < events.length; i++) {
				String e = events[i];
				URL eUrl = new URL("https://www.thebluealliance.com/api/v3/event/"+e+"/matches");
				HttpURLConnection eCon = (HttpURLConnection) eUrl.openConnection();
				eCon.setRequestMethod("GET");
				eCon.setRequestProperty("User-Agent", "X-TBA-Auth-Key:gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
				eCon.setRequestProperty("X-TBA-Auth-Key", "gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj");
				System.out.println((i+1) + "/" + (events.length) + ":");
				System.out.println("Sending 'GET' request to URL: " + eUrl);
				responseCode = eCon.getResponseCode();
				System.out.println("Response Code: " + responseCode);
				
				in = new BufferedReader(
				        new InputStreamReader(eCon.getInputStream()));
				StringBuffer eResponse = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					eResponse.append(inputLine);
				}
				in.close();
				
				Match_Steamworks[] objs = gson.fromJson(eResponse.toString(), Match_Steamworks[].class);
				System.out.println("JSON Object");
				System.out.println(objs);
				System.out.println("# Matches: " + objs.length + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}