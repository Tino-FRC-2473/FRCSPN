package tests.python;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.google.gson.Gson;

import models.YR2017_Steamworks.Match_2017Steamworks;

public class Test4_IterateMatches {
	public static void main(String[] args) {
		try {
			ProcessBuilder pb = new ProcessBuilder("python", "python/eventslist.py", "2017");
			Process p = pb.start();
			 
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			System.out.print("waiting");
			in.readLine();
			System.out.print("...");
			in.readLine();
			System.out.println("...");
			String eventsStr = in.readLine();
			System.out.println(eventsStr);
			
			Gson gson = new Gson();
			String[] events = gson.fromJson(eventsStr, String[].class);
			
			for(int i = 0; i < events.length; i++) {
				ProcessBuilder processBuilder = new ProcessBuilder("python", "python/match.py", events[i]);
				Process process = processBuilder.start();
				 
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				System.out.print(events[i]);
				bufferedReader.readLine();
				System.out.print("...");
				System.out.println(bufferedReader.readLine());
				System.out.println("...");
				String matchesStr = bufferedReader.readLine();
				System.out.println(matchesStr);
				
				Gson g = new Gson();
				Match_2017Steamworks[] matches = g.fromJson(matchesStr, Match_2017Steamworks[].class);
				for(int j = 0; j < matches.length; j++) {
					if(matches[j] == null) System.out.println("NULL ERROR");
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
