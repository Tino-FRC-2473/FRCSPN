package tests;

import main.Requester;
import models.*;
import models.YR2017_Steamworks.*;
import models.YR2018_PowerUp.*;

@SuppressWarnings("unused")
public class Driver {
	public static void main(String[] args) {
		Requester req = new Requester(false);
		
		Match_2017Steamworks[] matches = req.getMatchesAt2017Event("2017tur");
		
		for(Match_2017Steamworks m : matches) {
			System.out.println(m.key);
		}
	}
}
