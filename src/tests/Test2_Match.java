package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import models.YR2017_Steamworks.Match_2017Steamworks;

public class Test2_Match {
	public static void main(String[] args) {
		try {
			String event = "2017casj";
			URL url = new URL("https://www.thebluealliance.com/api/v3/event/"+event+"/matches");
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
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				//System.out.println(inputLine);
				response.append(inputLine);
			}
			in.close();
			
			Gson gson = new Gson();
			Match_2017Steamworks[] objs = gson.fromJson(response.toString(), Match_2017Steamworks[].class);
			System.out.println("JSON Object");
			System.out.println(objs + "\n");
			for(int i = 0; i < objs.length; i++) {
				System.out.println(objs[i]);
				System.out.println(objs[i].key);
				System.out.println(objs[i].time);
				System.out.println("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}