package tests;

import java.util.Arrays;

import general.requests.Requester;
import models.*;
import models.YR2017.*;
import models.YR2018.*;

@SuppressWarnings("unused")
public class Driver {
	public static void main(String[] args) {
		Requester req = new Requester(false);
		
		Event data = req.getEventInfo("2017tur");
		
		System.out.println(data);
	}
}
