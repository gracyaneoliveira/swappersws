package br.edu.ifce.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.ifce.dao.TimelineDAO;
import br.edu.ifce.model.Notification;

@Path("/timeline")
public class TimelineService {
	
	@GET
	@Path("/{region}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notification> getTimeline(@PathParam("region") String region) {
		return TimelineDAO.getTimeline(region);
	}

}
