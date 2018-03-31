package general.neuralNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class NNThread extends Thread {
	private boolean alive;
	private NNDatabase database;

	private ArrayList<String[]> nnRequests;

	public NNThread(NNDatabase db) {
		alive = true;
		database = db;
		nnRequests = new ArrayList<String[]>();
	}

	@Override
	public void run() {
		while (alive) {
			if (nnRequests.size() > 0)
				nnRequest(nnRequests.remove(0));
			try {
				Thread.sleep(10);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void nnRequest(String[] p) {
		if (!database.contains(p)) {
			System.out.println("Req");
			ProcessBuilder writingProcessBuilder = new ProcessBuilder("C:\\Users\\Starkillr241\\Miniconda3\\python.exe",
					"neuralNetwork/writeStatsForNN.py", p[0], p[1], p[2], p[3], p[4], p[5]);
			boolean filesWritten = false;
			try {
				Process writingProcess = writingProcessBuilder.start();
				BufferedReader read = new BufferedReader(new InputStreamReader(writingProcess.getInputStream()));

				for (int i = 0; i < 35; i++) {
					try {
						String line = read.readLine();
						System.out.println(line);
						if (line == null) {
							break;
						} else if (line.equals("!")) {
							filesWritten = true;
							break;
						}
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (filesWritten) {
				ProcessBuilder nnProcessBuilder = new ProcessBuilder("C:\\Users\\Starkillr241\\Miniconda3\\python.exe",
						"neuralNetwork/NN2.py", "Y");
				try {
					String output = null;
					Process nnProcess = nnProcessBuilder.start();
					BufferedReader rdr = new BufferedReader(new InputStreamReader(nnProcess.getInputStream()));
					for (int i = 0; i < 25; i++) {
						try {
							String line = "";
							if ((line = rdr.readLine()).charAt(0) == '[') {
								output = line;
								break;
							}
							Thread.sleep(25);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					if (output != null) {
						System.out.println("Output: " + output);
						int idx = 0;
						String[] outputs = { "", "" };
						for (int i = 0; i < output.length(); i++) {
							char c = output.charAt(i);
							if (c == '.' || Character.isDigit(c))
								outputs[idx] += c;
							else if (outputs[0].length() > 0)
								idx = 1;
						}
						DecimalFormat formatter = new DecimalFormat("#.00");
						database.put(p, formatter.format(Double.parseDouble(outputs[0])),
								formatter.format(Double.parseDouble(outputs[1])));
					} else {
						System.out.println("\nNN DIDNT WORK\n");
						database.put(p, "-1", "-1");
					}

					rdr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("\nWRITING FILES DIDNT WORK\n");
				database.put(p, "-1", "-1");
			}
		}
	}

	public void addNNRequest(String[] arr) {
		nnRequests.add(arr);
	}
}
