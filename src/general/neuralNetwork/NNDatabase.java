package general.neuralNetwork;

import java.util.Arrays;
import java.util.HashMap;

public class NNDatabase {
	private HashMap<String[], NNResponse> database;
	
	public NNDatabase() {
		database = new HashMap<String[], NNResponse>();
	}
	
	public void put(String[] arr, String r, String b) { database.put(arr, new NNResponse(r, b)); }
	
	public NNResponse get(String[] arr) {
		for(String[] a : database.keySet())
			if(Arrays.equals(a, arr)) {
				return database.get(a);
			}
		return null;
	}
	
	public boolean contains(String[] p) {
		for(String[] a : database.keySet()) {
			if(Arrays.equals(a, p)) {
				return true;
			}
		}
		return false;
	}
	
	public class NNResponse {
		public String red, blue;
		public NNResponse(String r, String b) {
			red = r;
			blue = b;
		}
	}
}
