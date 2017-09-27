package br.edu.ifce.service.test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PlaceServiceTest {

	@Ignore
	@Test
	public void getBooksByIdPlaceTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/place/books/6").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
	
	@Ignore
	@Test
	public void getPlaceByCityStateTest() throws JSONException{
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/place")
				.queryParam("city", "Fortaleza").queryParam("states", "Ceará")
				.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);
		
		//Gson gson = new Gson();
		 Gson gson = new GsonBuilder()
         .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
         .create();
		
		List<Place> places = new ArrayList<>();
		JSONObject jso = new JSONObject(output);
	    JSONArray a =  jso.getJSONArray("place");
	    for (int i = 0; i < a.length(); i++) {
	    	Place place = new Place();
	    	
	    	place.setId(a.getJSONObject(i).getInt("id"));
	        place.setName(a.getJSONObject(i).getString("name"));
	        place.setStates(a.getJSONObject(i).getString("states"));
	        place.setCity(a.getJSONObject(i).getString("city"));
	        place.setDistrict(a.getJSONObject(i).getString("district"));
	        place.setStreet(a.getJSONObject(i).getString("street"));
	        place.setNumber(a.getJSONObject(i).getString("number"));
	        place.setCep(a.getJSONObject(i).getString("cep"));
	        place.setHour_func(a.getJSONObject(i).getString("hour_func"));
	        place.setLatitude(a.getJSONObject(i).getDouble("latitude"));
	        place.setLongitude(a.getJSONObject(i).getDouble("longitude"));
	        place.setDonation(a.getJSONObject(i).getInt("donation"));
	        place.setRecovered(a.getJSONObject(i).getInt("recovered"));
	        place.setPhoto2(new String(a.getJSONObject(i).getString("photo").getBytes(Charset.forName("UTF-8"))));
	    	
	    			 if(a.getJSONObject(i).has("books") && a.getJSONObject(i).getString("books").contains("[")){
	    				String jsonBooks = a.getJSONObject(i).getString("books");
	    				java.lang.reflect.Type collectionType = new TypeToken<ArrayList<Book>>(){}.getType();
	    				ArrayList<Book> books = gson.fromJson(jsonBooks, collectionType);
	    				place.setBooks(books);
	    				System.out.println("Place ID="+a.getJSONObject(i).getString("id")+"- Qtd Book = "+books.size());
	    			 }else if(a.getJSONObject(i).has("books")){
	    				String jsonBooks = a.getJSONObject(i).getString("books");
	    				Book book = gson.fromJson(jsonBooks, Book.class);
	    				place.setBooks(Arrays.asList(book));
	    				System.out.println("Place ID="+a.getJSONObject(i).getString("id")+"- One Book = "+book.getTitle());
	    			 }
	    			 
	    	places.add(place);
		}
	    
		Assert.assertEquals(200,response.getStatus());
		Assert.assertTrue(places.size()>1);
		Assert.assertTrue(!output.equals("null"));
	}
	
	//@Ignore
	@Test
	public void getPlaceByIdWithBooksTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/place/11").get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertTrue(!output.equals("null"));
	}
	
	@Ignore
	@Test
	public void getBestPlaceCurrentMonthTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/place/statistic").
				queryParam("city", "Fortaleza").queryParam("state", "Ceará").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
	}
}
