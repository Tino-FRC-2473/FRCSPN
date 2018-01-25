package tests.python;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import models.YR2017_Steamworks.Match_2017Steamworks;

public class Test3_Match {
	public static void main(String[] args) {
		try {
			ProcessBuilder pb = new ProcessBuilder("python", "python/match.py", "2017crc");
			Process p = pb.start();
			 
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			System.out.print("waiting");
			String str = in.readLine();
			System.out.print("...");
			in.readLine();
			System.out.println("...");
			str = in.readLine();
			
			System.out.println("Full Return:");
			System.out.println(str + "\n");
			//str = str.substring(0, str.indexOf("}, {", str.indexOf("}, {", str.indexOf("}, {")+1)+1)+1) + "]";
			System.out.println("Full Return:");
			System.out.println(str + "\n");
			
			Gson gson = new Gson();
			Match_2017Steamworks[] objs = gson.fromJson(str, Match_2017Steamworks[].class);
			System.out.println("JSON Object");
			System.out.println(objs + "\n");
			for(int i = 0; i < objs.length; i++) {
				System.out.println(objs[i]);
				System.out.println(objs[i].key);
				System.out.println("\n");
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
