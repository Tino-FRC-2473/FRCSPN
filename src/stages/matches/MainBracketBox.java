package stages.matches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.layout.HBox;
import models.matches.yr2018.Match_PowerUp;

public class MainBracketBox extends HBox {
	private Match_PowerUp[] playoffs;
	private PlayoffMatchDisplay[][] displays;
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
		displays = new PlayoffMatchDisplay[7][4];
		
		for(StringInteger si : playoffMatches) {
			ArrayList<Match_PowerUp> arr = new ArrayList<Match_PowerUp>();
			for(Match_PowerUp m : playoffs)
				if(m.comp_level.equals(si.string) && m.set_number == si.integer)
					arr.add(m);
			int blue = 0;
			int red = 0;
			for(Match_PowerUp m : arr) {
				if(m.winning_alliance.equals("red"))
					red++;
				else if(m.winning_alliance.equals("blue"))
					blue++;
			}
		}
	}
}

class PlayoffMatchDisplay {
	
}

class StringInteger {
	public String string;
	public int integer;
	
	public StringInteger(String s, int i) {
		string = s;
		integer = i;
	}
}