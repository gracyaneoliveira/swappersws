package br.edu.ifce.repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;
import br.edu.ifce.model.User;
import br.edu.ifce.util.ServiceHttp;

public class DBRepository {

	private Map<String, Book> map;

	private List<String> idBooks = Arrays.asList("p4lGBgAAQBAJ",
			"g9tyJp6IJH4C", "EezJAwAAQBAJ", "0Ce7HDOnNFMC", "ycBdAAAAQBAJ",
			"2TfvIJ9pyV8C", "RhqJWjZ_6o4C", "0C2YydjxYbcC", "jAnAZO6EE40C",
			"uLrUpMscQqcC", "3Kv7au2Lf7IC", "YC3nciMBXzkC", "VKm1RC2LzJsC",
			"_08hjhuq0KAC", "0mbbBQAAQBAJ","tWL4kaJhMqcC");
	
	public DBRepository() throws IOException {
		fillMapBookFromGoogleWs();
	}

	private void fillMapBookFromGoogleWs() throws IOException {
		map = new HashMap<>();
		for (String idBook : idBooks) {
			map.put(idBook, ServiceHttp.connectionHttp(idBook));
		}
	}

	private void makeConnectionWS(User user, String pathMethod) {

		Client client = Client.create();
		WebResource webResource = client
				.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");

		ClientResponse response = webResource.path("/book/" + pathMethod)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, user);
		System.out.println(response.getStatus());
	}

	public void makeDonation(int idUser, String idBook, int idPlace) {

		User user = new User();
		user.setId(idUser);

		Book book = map.get(idBook);

		Place place = new Place();
		place.setId(idPlace);

		book.setPlace(place);

		user.setBook(book);

		makeConnectionWS(user, "donation");
	}

	public void makeRecovered(int idUser, String idBook, int idPlace) {

		User user = new User();
		user.setId(idUser);

		Book book = map.get(idBook);

		Place place = new Place();
		place.setId(idPlace);

		book.setPlace(place);

		user.setBook(book);

		makeConnectionWS(user, "recovered");
	}

	public void makeFavorite(int idUser, String idBook) {

		User user = new User();
		user.setId(idUser);

		Book book = map.get(idBook);

		user.setBook(book);

		makeConnectionWS(user, "favorite");
	}

}
