package br.edu.ifce.main;

import java.io.IOException;
import java.util.Date;

import br.edu.ifce.repository.DBRepository;

public class Main {

	public static void main(String[] args) throws IOException {
//		java.util.Date dt = new java.util.Date();
//
//		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
//				"yyyy-MM-dd");
//
//		String currentTime = sdf.format(dt);
//		System.out.println(currentTime);
		Date date = new Date();
		date.setTime(1447388699986L);
		System.out.println(date.toString());
		
	}

	@SuppressWarnings("unused")
	private static void makeDonation() throws IOException {
		System.out.println("Init donation...");
		DBRepository repository = new DBRepository();

		repository.makeDonation(86, "p4lGBgAAQBAJ", 1);
		repository.makeDonation(86, "g9tyJp6IJH4C", 2);
		repository.makeDonation(87, "EezJAwAAQBAJ", 4);
		repository.makeDonation(88, "0Ce7HDOnNFMC", 1);
		repository.makeDonation(88, "ycBdAAAAQBAJ", 2);

		repository.makeDonation(86, "2TfvIJ9pyV8C", 3);
		repository.makeDonation(87, "RhqJWjZ_6o4C", 5);
		repository.makeDonation(87, "0C2YydjxYbcC", 6);
		repository.makeDonation(88, "jAnAZO6EE40C", 3);
		repository.makeDonation(88, "uLrUpMscQqcC", 4);

		repository.makeDonation(86, "3Kv7au2Lf7IC", 5);
		repository.makeDonation(86, "YC3nciMBXzkC", 6);
		repository.makeDonation(87, "VKm1RC2LzJsC", 1);
		repository.makeDonation(87, "_08hjhuq0KAC", 2);
		repository.makeDonation(88, "0mbbBQAAQBAJ", 3);

		System.out.println("Finished donation...");
	}

	@SuppressWarnings("unused")
	private static void makeRecovered() throws IOException {
		System.out.println("Init recovered...");
		DBRepository repository = new DBRepository();

		repository.makeRecovered(86, "EezJAwAAQBAJ", 4);
		repository.makeRecovered(86, "0Ce7HDOnNFMC", 1);
		repository.makeRecovered(86, "ycBdAAAAQBAJ", 2);
		repository.makeRecovered(86, "RhqJWjZ_6o4C", 5);
		repository.makeRecovered(87, "p4lGBgAAQBAJ", 1);

		repository.makeRecovered(87, "g9tyJp6IJH4C", 2);
		repository.makeRecovered(87, "2TfvIJ9pyV8C", 3);
		repository.makeRecovered(88, "VKm1RC2LzJsC", 1);
		repository.makeRecovered(88, "_08hjhuq0KAC", 2);
		repository.makeRecovered(88, "YC3nciMBXzkC", 6);

		System.out.println("Finished recovered...");
	}

	@SuppressWarnings("unused")
	private static void makeFavorite() throws IOException {
		System.out.println("Init favorite...");
		DBRepository repository = new DBRepository();

		repository.makeFavorite(86, "p4lGBgAAQBAJ");
		repository.makeFavorite(86, "g9tyJp6IJH4C");
		repository.makeFavorite(86, "EezJAwAAQBAJ");
		repository.makeFavorite(86, "uLrUpMscQqcC");
		repository.makeFavorite(86, "VKm1RC2LzJsC");
		repository.makeFavorite(86, "2TfvIJ9pyV8C");

		repository.makeFavorite(87, "0Ce7HDOnNFMC");
		repository.makeFavorite(87, "ycBdAAAAQBAJ");
		repository.makeFavorite(87, "2TfvIJ9pyV8C");
		repository.makeFavorite(87, "3Kv7au2Lf7IC");
		repository.makeFavorite(87, "_08hjhuq0KAC");
		repository.makeFavorite(87, "jAnAZO6EE40C");

		repository.makeFavorite(88, "RhqJWjZ_6o4C");
		repository.makeFavorite(88, "0C2YydjxYbcC");
		repository.makeFavorite(88, "jAnAZO6EE40C");
		repository.makeFavorite(88, "EezJAwAAQBAJ");
		repository.makeFavorite(88, "YC3nciMBXzkC");
		repository.makeFavorite(88, "0mbbBQAAQBAJ");

		System.out.println("Finished favorite...");
	}

}
