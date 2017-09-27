package br.edu.ifce.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.edu.ifce.dao.transaction.PlaceTransactionDAO;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;

@Path("/place")
public class PlaceService {
	
	@GET
	@Path("/books/{idPlace}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooksByIdPlace(@PathParam("idPlace") String idPlace){
		return PlaceTransactionDAO.getBooksByIdPlace(idPlace);
	}
	
	@GET
	@Path("/{idPlace}")
	@Produces(MediaType.APPLICATION_JSON)
	public Place getPlaceByIdWithBooks(@PathParam("idPlace") String idPlace){
		return PlaceTransactionDAO.getPlaceByIdWithBooks(idPlace);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Place> getPlaceByCity(@QueryParam("city") String city, @QueryParam("states") String states){
		return PlaceTransactionDAO.getPlaceByCityState(city,states);
	}
	
	@GET
	@Path("/statistic")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Place> getBestPlaceCurrentMonth(@QueryParam("city") String city, @QueryParam("state") String state) {
		return PlaceTransactionDAO.getBestPlaceCurrentMonth(city,state);
	}

}
