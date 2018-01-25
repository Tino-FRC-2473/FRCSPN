package tests.python;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test2_TBAStatus {
	public static void main(String[] args) {
		try {
			ProcessBuilder pb = new ProcessBuilder("python", "python/status.py");
			Process p = pb.start();
			 
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = in.readLine();
			System.out.println(str);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
