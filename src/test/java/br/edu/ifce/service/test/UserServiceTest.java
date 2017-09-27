package br.edu.ifce.service.test;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.edu.ifce.model.User;

public class UserServiceTest {
	
	@Ignore
	@Test
	public void insertUser(){
		
		Client client = Client.create();
		//WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/insert");
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		User user = new User();
		user.setUsername("teste18");
		user.setEmail("teste18@gmail.com");
		user.setPassword("123456");
		
		//ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,user);
		ClientResponse response = webResource.path("/login").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,user);
		System.out.println(response.getLocation());
		System.out.println(response.getStatus());
		Assert.assertEquals(201,response.getStatus());
	}
	
	@Ignore
	@Test
	public void getDonorsForMonthCurrentTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/login/donors").queryParam("city", "Fortaleza").queryParam("state", "Ceará")
				.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
	
	@Ignore
	@Test
	public void getUserTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/login/users")
				.queryParam("email", "bob@example.com").queryParam("password", "swappers").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertTrue(!output.equals("null"));
	}
	
	@Ignore
	@Test
	public void updatePasswordTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		User user = new User();
		user.setUsername("gracyaneoliveira");
		user.setEmail("gracyane@example.com");
		user.setPassword("12345678");
		
		ClientResponse response = webResource.path("/login/update/pwd").type(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class,user);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
	}
	
	@Ignore
	@Test
	public void updateBirthdayTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		User user = new User();
		user.setUsername("gracyaneoliveira");
		user.setEmail("gracyane@example.com");
		
		Calendar c = Calendar.getInstance();
        c.set(1999, 8, 9);
		
		user.setBirthday(c.getTime().getTime());
		System.out.println(c.getTime());
		
		ClientResponse response = webResource.path("/login/update/birthday").type(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class,user);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
	}
	
	@Ignore
	@Test
	public void deleteUserTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/login/delete/103").type(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
	}

}
