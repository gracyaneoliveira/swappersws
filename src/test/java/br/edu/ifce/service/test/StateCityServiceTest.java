package br.edu.ifce.service.test;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class StateCityServiceTest {

//	@Ignore
	@Test
	public void getStateCityTest(){
		Client client = Client.create();
		WebResource webResource = client.resource("http://swappersws-oliv.rhcloud.com/swappersws/swappersws");
		
		ClientResponse response = webResource.path("/statecity").type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
		System.out.println(response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);

		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(output.isEmpty(),false);
	}
}
