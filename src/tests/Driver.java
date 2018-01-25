package tests;

import java.util.Arrays;

import main.Requester;
import models.*;
import models.YR2017_Steamworks.*;
import models.YR2018_PowerUp.*;

@SuppressWarnings("unused")
public class Driver {
	public static void main(String[] args) {
		Requester req = new Requester(false);
		
		String[] matches = req.getTeamEventsForYear(254, 2018);
		
		System.out.println(Arrays.asList(matches));
	}
}
