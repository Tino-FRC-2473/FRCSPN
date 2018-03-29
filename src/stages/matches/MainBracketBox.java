package stages.matches;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import models.matches.SingleAlliance;
import models.matches.yr2018.Match_PowerUp;

public class MainBracketBox extends HBox {
	private Match_PowerUp[] playoffs;
//	private PlayoffMatchDisplay[][] displays;
	private ArrayList<StringInteger> playoffMatches;
	
	public MainBracketBox(Match_PowerUp[] matches) {
		ArrayList<Match_PowerUp> a = new ArrayList<Match_PowerUp>();
		for(Match_PowerUp m : matches)
			if(!m.comp_level.equals("qm"))
				a.add(m);
		playoffs = a.toArray(new Match_PowerUp[a.size()]);

		playoffMatches = new ArrayList<StringInteger>(Arrays.asList(
				new StringInteger("qf", 1),
				new StringInteger("qf", 2),
				new StringInteger("qf", 3),
				new StringInteger("qf", 4),
				new StringInteger("sf", 1),
				new StringInteger("sf", 2),
				new StringInteger("f", 1)
		));
//		displays = new PlayoffMatchDisplay[7][4];
		
		for(StringInteger si : playoffMatches) {
			ArrayList<Match_PowerUp> arr = new ArrayList<Match_PowerUp>();
			for(Match_PowerUp m : playoffs)
				if(m.comp_level.equals(si.string) && m.set_number == si.integer)
					arr.add(m);
//			int blue = 0;
//			int red = 0;
//			for(Match_PowerUp m : arr) {
//				if(m.winning_alliance.equals("red"))
//					red++;
//				else if(m.winning_alliance.equals("blue"))
//					blue++;
//			}
		}
	}
}

class PlayoffMatchDisplay extends BorderPane{
	Match_PowerUp[] matches;
	Color blueCol = Color.valueOf("");
	Color redCol = Color.valueOf("");
	public PlayoffMatchDisplay(Match_PowerUp[] m) {
		matches = m;
	}
	
	public void display() {
		Label title = new Label("Playoffs");
		setTop(title);
		if (matches[matches.length-1].score_breakdown != null) {
			HBox winners = new HBox();
			String[] winner;
			if (matches[matches.length-1].winning_alliance.equals("red")) winner = matches[0].alliances.red.team_keys;
			else winner = matches[matches.length-1].alliances.blue.team_keys;
			for (String s : winner) {
				Label l = new Label(s.substring(3));
				winners.getChildren().add(l);
			}
			setBottom(winners);
		}
		GridPane center = new GridPane();
		StackPane[] qf1 = match(0);
		
		
		
	}
	
	public StackPane[] match(int set) {
		StackPane[] alliances = new StackPane[2];
		int blue = 0;
		int red = 0;
		int x = 0;
		ArrayList<Match_PowerUp> setMatches = new ArrayList<>();
		for (int i = 0; i < matches.length; i++) {
			if (matches[i].set_number == set) {
				setMatches.add(matches[i]);
				x = i;
			}
		}
		if (matches[x].score_breakdown!=null) {
			for (Match_PowerUp m : setMatches) {
				if (m.winning_alliance.equals("blue")) blue++;
				else red++;
			}
			if (blue > red) {
				alliances[0] = alliance(matches[x].alliances.blue,blueCol,true,true);
				alliances[1] = alliance(matches[x].alliances.red,redCol,false,true);
			}
			else {
				alliances[0] = alliance(matches[x].alliances.blue,blueCol,false,true);
				alliances[1] = alliance(matches[x].alliances.red,redCol,true,true);
			}
		}
		else {
			alliances[0] = alliance(matches[x].alliances.blue,blueCol,false,false);
			alliances[1] = alliance(matches[x].alliances.red,redCol,false,false);
		}
		return alliances;
	}
	
	public StackPane alliance(SingleAlliance a, Color color, boolean won, boolean finished) {
		StackPane s = new StackPane();
		Rectangle r = new Rectangle(200, 75);
		r.setFill(color);
		r.setStroke(Color.BLACK);
		s.getChildren().add(r);
		if (finished) {
			HBox labels = new HBox(15);
			for (String t : a.team_keys) {
				Label l = new Label(t.substring(3));
				if (won) l.setStyle("-fx-font-weight: bold");
				labels.getChildren().add(l);
			}
			s.getChildren().add(labels);
		}
		return s;
	}
	
	public Group line(boolean top, boolean left) {
		Group g = new Group();
		Line hTop = new Line(g.getLayoutX(),g.getLayoutY(),g.getLayoutX()+5,g.getLayoutY());
		Line hBot = new Line(g.getLayoutX(),g.getLayoutY()+150,g.getLayoutX()+5,g.getLayoutY()+150);
		Line vTop = new Line(g.getLayoutX()+5,g.getLayoutY(),g.getLayoutX()+5,g.getLayoutY()+75);
		Line vBot = new Line(g.getLayoutX()+5,g.getLayoutY()+75,g.getLayoutX()+5,g.getLayoutY()+150);
		Line hMid = new Line(g.getLayoutX()+5,g.getLayoutY()+75,g.getLayoutX()+10,g.getLayoutY()+75); 
		if (top) {
			hTop.setStrokeWidth(4);
			vTop.setStrokeWidth(4);
		}
		return g;
	}
	
}

class StringInteger {
	public String string;
	public int integer;
	
	public StringInteger(String s, int i) {
		string = s;
		integer = i;
	}
}