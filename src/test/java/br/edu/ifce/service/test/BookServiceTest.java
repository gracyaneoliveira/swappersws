package br.edu.ifce.service.test;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;
import br.edu.ifce.model.Review;
import br.edu.ifce.model.User;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class BookServiceTest {
	private User user;
	private Book book;
	private Place place;
	
	@Before
	public void setUp(){
		user = new User();
		user.setId(new Integer(86));
		
		book = new Book();
		book.setId("0C2YydjxYbcC");
		book.setTitle("A Guerra dos Tronos - As Crônicas de Gelo e Fogo");
		book.setAuthor("George R. R. Martin");
		book.setNumberPage(818);
		book.setPhoto("http://books.google.com.br/books/content?id=0C2YydjxYbcC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api");
		book.setEvaluationAverage((float) 5.0);
		book.setSynopsis("Quando Eddard Stark, lorde do castelo de Winterfell, aceita a prestigiada posição de Mão do Rei oferecida pelo "
				+ "velho amigo, o rei Robert Baratheon, não descon a que sua vida está prestes a ruir em sucessivas tragédias. Sabe-se que"
				+ " Lorde Stark aceitou a proposta porque descon a que o dono anterior do título fora envenenado pela manipuladora  rainha"
				+ " - uma cruel mulher do clã Lannister. E sua intenção é proteger o rei. Mas ter como inimigo os Lannister pode ser fatal:"
				+ " a ambição dessa família pelo poder parece não ter limites e o rei corre grande perigo. Agora, sozinho na corte, Eddard"
				+ " percebe que não só o rei está em apuros, mas também ele e toda a sua família. Quem vencerá a guerra dos tronos?");
		
		place = new Place();
		place.setId(2);
		book.setPlace(place);
		user.setBook(book);
	}
	
	@Ignore
	@Test
	public void makeDonationBook(){
		
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/donation").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,user);
		System.out.println(response.getHeaders());
		System.out.println(response.getStatus());
		
		Assert.assertEquals(200,response.getStatus());
	}
	
	@Ignore
	@Test
	public void makeRecoveredBook(){
		
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/recovered").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,user);
		System.out.println(response.getHeaders());
		System.out.println(response.getStatus());
		
		Assert.assertEquals(200,response.getStatus());
	}
	
	@Ignore
	@Test
	public void makeFavoriteBook(){
		
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/favorite").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,user);
		System.out.println(response.getHeaders());
		System.out.println(response.getStatus());
		
		Assert.assertEquals(200,response.getStatus());
	}
	
	@Ignore
	@Test
	public void getBookDonatedByUserTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/donation/86").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
	
	@Ignore
	@Test
	public void getBookRecoveredByUserTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/recovered/86").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
	
	@Ignore
	@Test
	public void getBookFavoriteByUserTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/favorite/86").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
	
	@Ignore
	@Test
	public void makeReviewBook(){
		
		Review review = new Review();
		review.setIdUser(95);
		review.setBook(book);
		review.setReview("Quem diria que alguém conseguiria superar tolkien?"
				+ " Martin conseguiu inventar um mundo fantástico, cheio de"
				+ " detalhes e sem deixar narrativa cansativa.");
		
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/review").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,review);
		System.out.println(response.getHeaders());
		System.out.println(response.getStatus());
		
		Assert.assertEquals(201,response.getStatus());
	}
	
	@Ignore
	@Test
	public void getReviewsBookTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/review/jAnAZO6EE40C").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
	
	@Test
	public void getBestBooksCurrentMonthTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/book/statistic").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		//Assert.assertEquals(output.isEmpty(),false);
	}
}
