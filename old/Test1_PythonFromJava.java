package tests.python;

import java.io.*;

public class Test1_PythonFromJava {
	public static void main(String[] args) {
		try {
			String prg = "import sys" + "\n" + "print(int(sys.argv[1])+int(sys.argv[2]))" + "\n";
			BufferedWriter out = new BufferedWriter(new FileWriter("python/test1.py"));
			out.write(prg);
			out.close();
			int number1 = 10;
			int number2 = 32;
			 
			ProcessBuilder pb = new ProcessBuilder("python", "python/test1.py", ""+number1, ""+number2);
			Process p = pb.start();
			 
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = in.readLine();
			System.out.println(str);
			int ret = new Integer(str).intValue();
			System.out.println("value is: " + ret);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
