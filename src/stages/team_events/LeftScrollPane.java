package stages.team_events;

import java.util.ArrayList;

import general.constants.K;
import general.images.I;
import gui.ClickableButton;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LeftScrollPane extends ScrollPane {
	private VBox v;
	private ClickableButton toggleButton;
	private String viewedLabel;
	private ArrayList<Label> labels;

	public LeftScrollPane() {
		super();
		viewedLabel = "";
		v = new VBox();
		v.setPadding(K.getInsets());
		v.setSpacing(K.TEAM_EVENTS.SPACING);
		v.setPrefWidth(K.TEAM_EVENTS.LEFT_WIDTH);

		this.setFitToWidth(true);
		this.setStyle("-fx-background-color: #CCCCCC;");
		this.setContent(v);
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) { handleClick(e); }
		});

		toggleButton = new ClickableButton(I.Type.TE_TEAM_LIST_BTN);
		v.getChildren().add(toggleButton);
		v.getChildren().add(I.getInstance().getSeparatorBlack(K.TEAM_EVENTS.LEFT_WIDTH - 2 * v.getPadding().getTop(), 5));

		// this used to be in a on clicked for the toggle if need reference
//				toggleButton.onPress();
//				if(toggleButton.getType() == I.Type.TE_TEAM_LIST_BTN) {
//					back();
//				} else {
//					File teamlist = new File("docs/team_list.txt");
//					Scanner in = null;
//					try {
//						 in = new Scanner(teamlist);
//					}catch(Exception b) {
//						b.printStackTrace();
//					}
//					while(in.hasNextLine()) {
//						String line = in.nextLine();
//						System.out.println("file read: " + line);
//						if(line.charAt(0) == '*') {
//							String teamnums = in.nextLine();
//							Scanner nums = new Scanner(teamnums);
//							while(nums.hasNext()) {
//								int num = nums.nextInt();
//								TeamInfo info = new TeamInfo(num,line.substring(1, line.lastIndexOf("*")));
//								info.setColor(line.substring(1, line.lastIndexOf(" ")));
//								System.out.println("info: " + info);
//								boolean exists = false;
//								for(TeamInfo t:getCenterPane().getTeams()) {
//									if(t.equals(info)) {
//										exists = true;
//										break;
//									}
//								}
//								if(!exists) {
//									getCenterPane().getTeams().add(info);
//									System.out.println("info: " + info);
//								}
//							}
//						}
//					}
//					getTeamEventsStage().loadViewing();
//					edit();
//				}
//			}

		labels = new ArrayList<Label>();
	}
	
	public void initializeViewing(StringWithColor[] arr) {
		this.clear();
		for(StringWithColor sc : arr) {
			LeftLabel l = new LeftLabel(sc);
			labels.add(l);
			v.getChildren().add(l);
			l.setOnMouseClicked(new onLabelClicked(sc));
		}
	}
	
	private void clear() {
		for(int i = 0; i < v.getChildren().size(); i++) {
			if(v.getChildren().get(i).getClass().toString().equals(Label.class.toString())) {
				v.getChildren().remove(i);
				i--;
			}
		}
	}

	public void edit() {
		System.out.println("teams: " + getCenterPane().getTeams());
		Label l = new Label("Add");
		l.setStyle("-fx-background-color: #CCCCCC ; " + "-fx-font-size: 20; -fx-stroke: black; -fx-font-weight: bold");
		l.setPrefSize(K.TEAM_EVENTS.LEFT_WIDTH - 2 * v.getPadding().getTop(), K.TEAM_EVENTS.LEFT_BUTTON_HEIGHT);
		l.setPadding(K.getInsets());
		v.getChildren().add(l);
		
		getCenterPane().loadEditing();
	}

	public void back() {
		for(int i = 0; i < v.getChildren().size(); i++) {
			if(v.getChildren().get(i).equals(new Label("Add"))) {
				v.getChildren().remove(i);
			}
		}
		getCenterPane().saveChanges();
		getCenterPane().unloadEditing();
	}

	public void handleClick(MouseEvent e) {
		// System.out.println("\n" + e.getX() + " " + e.getY());
		// for(Label l : labels) {
		// System.out.println(l.getLayoutX() + " " + l.getLayoutY());
		// if(l.contains(e.getX(), e.getY())) {
		// System.out.println(l.getText());
		// }
		// }
	}

	public ArrayList<Label> getLabels() {
		return labels;
	}

//	private TeamEventsStage getTeamEventsStage() {
//		return ((TeamEventsStage)((BorderPane) getParent()).getScene().getWindow());
//	}

	private CenterPane getCenterPane() {
		return ((CenterPane)((BorderPane) getParent()).getCenter());
	}

	private class onLabelClicked implements EventHandler<MouseEvent> {
		private StringWithColor sc;

		private onLabelClicked(StringWithColor s) {
			sc = s;
		}

		@Override
		public void handle(MouseEvent event) {
			getCenterPane().addFor(sc);
			viewedLabel = sc.toString();
//			ArrayList<Integer> teams = getTeamEventsStage().getTeams(sc);
//			CenterPane cp = getCenterPane();
//			while(cp.getChildren().size() > 0) {
//				cp.getChildren().remove(0);
//			}
//			// while(cp.teams.size() > 0) {
//			// cp.teams.remove(0);
//			// }
//			for(int team : teams) {
//				getCenterPane().updateTeamInfo(team, sc);
//			}
//			// System.out.println(getCenterPane().getTeams());
		}
	}

	// make these methods
	public ArrayList<String> getAddedCategories() {
		return new ArrayList<String>();
	}

	// make these methods
	public void clearAddedCategories() {

	}

	// make these methods
	public boolean getState() {
		return true;
	}
	
	public String getViewedLabel() {
		return viewedLabel;
	}
}
