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
		
		Event data = req.getEventInfo("2017tur");
		
		System.out.println(data);
	}
}
