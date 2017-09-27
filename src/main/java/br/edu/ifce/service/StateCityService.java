package br.edu.ifce.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.ifce.dao.transaction.StateCityTransactionDAO;
import br.edu.ifce.model.StateCity;

@Path("/statecity")
public class StateCityService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StateCity> getStateCity() {
		return StateCityTransactionDAO.getStateCity();
	}
	
}
