package stages.team_events;

import java.util.concurrent.CyclicBarrier;

import general.ScoutingApp;
import javafx.application.Platform;

public class LoadingThread extends Thread {
	private boolean alive;
	private boolean first;
	private TeamEventsStage stage;
	
	public LoadingThread(TeamEventsStage s) {
		alive = true;
		first = true;
		stage = s;
	}

	public void run() {
		while(alive) {
			if(first) {
				first = false;
				continue;
			}
			if(ScoutingApp.getDatabase().getNumberIncompleteRequests() > 0 && !stage.getState().equals(TeamEventsStage.State.LOADING)) {
				System.out.println("***ADD SET LOADING***");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						System.out.println("CHANGE LOADING");
						stage.setState(TeamEventsStage.State.LOADING);
					}
				});

			} else if(ScoutingApp.getDatabase().getNumberIncompleteRequests() == 0 && stage.getState().equals(TeamEventsStage.State.LOADING)) {
				System.out.println("***ADD SET NORMAL***");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						System.out.println("CHANGE NORMAL");
						stage.setState(TeamEventsStage.State.NORMAL);
					}
				});
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("thread ended");
	}
	
	public void end() {
		System.out.println("thread told to end");
		alive = false;
	}
}