package stages.matches;

import general.ScoutingApp;
import javafx.application.Platform;

public class MLoadingThread extends Thread {
	private boolean alive;
	private MatchesStage stage;
	private MLoadingScene scene;
	
	public MLoadingThread(MatchesStage st, MLoadingScene sc) {
		alive = true;
		stage = st;
		scene = sc;
	}

	public void run() {
		while(alive) {
			scene.rotate();
			
			try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
			
			if(ScoutingApp.getDatabase().getNumberIncompleteRequests() == 0 && stage.getState().equals(MatchesStage.State.LOADING)) {
				System.out.println("***ADD SET SELECTING***");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						System.out.println("CHANGE SELECTING");
						stage.setState(MatchesStage.State.SELECTING);
						end();
					}
				});
			}
		}
//		System.out.println("loading thread ended");
	}
	
	public void end() {
//		System.out.println("loading thread told to end");
		alive = false;
	}
}