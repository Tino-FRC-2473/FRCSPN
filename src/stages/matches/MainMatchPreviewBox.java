package stages.matches;

import general.ScoutingApp;
import general.constants.K;
import general.neuralNetwork.NNDatabase;
import general.neuralNetwork.NNDatabase.NNResponse;
import general.neuralNetwork.NNThread;
import gui.BoxPaddingInsets;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.matches.yr2018.Match_PowerUp;

public class MainMatchPreviewBox extends VBox {
	private NNDatabase nnDatabase;
	private NNThread nnThread;
	private HBox content;
	
	public MainMatchPreviewBox() {
		nnDatabase = new NNDatabase();
		nnThread = new NNThread(nnDatabase);
		nnThread.start();
		content = new HBox();
		this.setAlignment(Pos.TOP_CENTER);
		this.setSpacing(BoxPaddingInsets.OFFSET);
		this.setPadding(K.getInsets());
		content.setAlignment(Pos.CENTER);
		Label l = new Label("Neural Network Match Prediction");
		l.setStyle("-fx-font-size: 40; -fx-font-weight: bold;");
		this.getChildren().addAll(l, content);
	}
	
	public void display(Match_PowerUp m) {
		content.getChildren().clear();
		Label load = new Label("Loading...");
		load.setStyle("-fx-font-size: 15");
		content.getChildren().add(load);
		String[] p = new String[6];
		for(int i = 0; i < 3; i++) {
			p[i] = m.alliances.red.team_keys[i].substring(3) + "-" + compressedArrayToString(ScoutingApp.getDatabase().getEventKeysForTeam(m.alliances.red.team_keys[i]));
			p[i+3] = m.alliances.blue.team_keys[i].substring(3) + "-" + compressedArrayToString(ScoutingApp.getDatabase().getEventKeysForTeam(m.alliances.blue.team_keys[i]));
		}
		nnThread.addNNRequest(p);
		new CheckNNThread(p).start();
	}
	
	public void updateNNView(String[] p, NNResponse resp) {
		if(resp.red.equals("-1") || resp.blue.equals("-1")) {
			content.getChildren().clear();
			Label err = new Label("There is not enough data to predict this match with the neural network.");
			err.setStyle("-fx-font-size: 15");
			content.getChildren().add(err);
		} else {
			content.getChildren().clear();
			VBox left = new VBox(), right = new VBox();
			
			Label ls = new Label(resp.blue);
			ls.setStyle("-fx-font-size: 28;");
			left.getChildren().add(ls);
			Label rs = new Label(resp.red);
			rs.setStyle("-fx-font-size: 28");
			right.getChildren().add(rs);
			
			for(int i = 0; i < 3; i++) {
				Label l = new Label(p[i+3].substring(0, p[i+3].indexOf("-")));
				l.setStyle("-fx-font-size: 17;");
				left.getChildren().add(l);
				
				Label r = new Label(p[i].substring(0, p[i].indexOf("-")));
				r.setStyle("-fx-font-size: 17;");
				right.getChildren().add(r);
			}
			left.setStyle("-fx-background-color: #DDDEFF;");
			right.setStyle("-fx-background-color: #FFB9B9;");
			left.setPadding(K.getInsets());
			right.setPadding(K.getInsets());
			left.setAlignment(Pos.CENTER);
			right.setAlignment(Pos.CENTER);
			content.getChildren().addAll(left, right);
		}
	}
	
	private String compressedArrayToString(String[] arr) {
		String str = "";
		for(int i = 0; i < arr.length; i++)
			str += arr[i].substring(4) + ((i<arr.length-1)?",":"");
		return str;
	}
	
	class CheckNNThread extends Thread {
		private boolean alive;
		private String[] arr;
		
		public CheckNNThread(String[] a) {
			alive = true;
			arr = a;
		}
		
		@Override public void run() {
			System.out.println("start check");
			while(alive) {
				try { Thread.sleep(25); } catch(InterruptedException ie) { ie.printStackTrace(); }
				if(nnDatabase.get(arr) != null) {
					alive = false;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ScoutingApp.mStage.updateNNView(arr, nnDatabase.get(arr));
						}
					});
				}
			}
			System.out.println("end check");
		}
	}
}
