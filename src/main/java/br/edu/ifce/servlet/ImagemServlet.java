package br.edu.ifce.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import br.edu.ifce.dao.transaction.PlaceTransactionDAO;
import br.edu.ifce.model.Place;

@WebServlet(urlPatterns="/photo")
public class ImagemServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Place place = PlaceTransactionDAO.getPlaceById(request.getParameter("codigo"));
		
		response.setContentType("image/jpeg");
		IOUtils.write(place.getPhoto(), response.getOutputStream());
	}
}
