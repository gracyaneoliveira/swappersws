package br.edu.ifce.service;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.edu.ifce.dao.UserDAO;
import br.edu.ifce.model.User;

@Path("/login")
public class UserService {
	
	@GET
	@Path("/dologin")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLogin(@QueryParam("email") String email, @QueryParam("password") String pwd){
		boolean status = UserDAO.checkLogin(email, pwd);
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "login");
			obj.put("status", new Boolean(status));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	@GET
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUsersForID(@PathParam("id")String id){
		return UserDAO.getUsersForID(id);
	}
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUsersForEmailAndPwd(@QueryParam("email") String email, @QueryParam("password") String pwd){
		return UserDAO.getUsersForEmailAndPwd(email,pwd);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertUser(User user){
		
		if(UserDAO.checkIfEmailAlreadyExist(user.getEmail())){
			return Response.status(Status.CONFLICT).build();
		}
		
		Integer idUser = UserDAO.insertUser(user);
		URI uri = URI.create("/users/" + String.valueOf(idUser));
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/donors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getDonorsForMonthCurrent(@QueryParam("city") String city, @QueryParam("state") String state){
		return UserDAO.getDonorsForMonthCurrent(city,state);
	}
	
	@PUT
	@Path("/update/pwd")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassword(User user){
		UserDAO.updatePassword(user);
		return Response.status(Status.OK).build();
	}
	
	@PUT
	@Path("/update/birthday")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBirthDay(User user){
		UserDAO.updateBirthday(user);
		return Response.status(Status.OK).build();
	}
	
	@PUT
	@Path("/update/cover")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCover(User user){
		UserDAO.updateCover(user);
		return Response.status(Status.OK).build();
	}
	
	@PUT
	@Path("/update/photoperfil")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePhotoPerfil(User user){
		UserDAO.updatePhotoPerfil(user);
		return Response.status(Status.OK).build();
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id")int id){
		UserDAO.delete(id);
		return Response.status(Status.OK).build();
	}
	
	@PUT
	@Path("/update/location")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCityStateUser(User user){
		UserDAO.updateCityStateUser(user);
		return Response.status(Status.OK).build();
		
	}
	
}
