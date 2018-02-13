package tests;

import java.util.Arrays;

import general.requests.Requester;
import models.*;
import models.matches.yr2017.*;
import models.matches.yr2018.*;

@SuppressWarnings({ "unused", "deprecation" })
public class Driver {
	public static void main(String[] args) {
		Requester req = new Requester(false);
		
		Event data = req.getEventInfo("2017tur");
		
		System.out.println(data);
	}
}
