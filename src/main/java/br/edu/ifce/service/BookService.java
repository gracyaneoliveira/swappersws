package br.edu.ifce.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.dao.transaction.BookTransactionDAO;
import br.edu.ifce.dao.transaction.DonationTransactionDAO;
import br.edu.ifce.dao.transaction.FavoriteTransactionDAO;
import br.edu.ifce.dao.transaction.PlaceTransactionDAO;
import br.edu.ifce.dao.transaction.RecoveredTransactionDAO;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.Review;
import br.edu.ifce.model.User;

@Path("/book")
public class BookService {

	@POST
	@Path("/donation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDonationBook(User user) {
		Connection connection = null;
		Book book = user.getBook();

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			if (BookTransactionDAO.bookExist(connection, book)) {
				if (BookTransactionDAO.bookHasPlace(connection, book)) {
					PlaceTransactionDAO.updateBookPlace(connection, book);
				} else {
					PlaceTransactionDAO.insertBookPlace(connection, user);
				}
			} else {
				BookTransactionDAO.insertBook(connection, book);
				PlaceTransactionDAO.insertBookPlace(connection, user);
			}

			PlaceTransactionDAO.updateDonationTotal(connection, user.getBook()
					.getPlace().getId());
			DonationTransactionDAO.addDonation(connection, user);
			BookTransactionDAO.updateDonationBook(connection, book.getId());

			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}

		return Response.ok().build();
	}

	@POST
	@Path("/recovered")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRecoveredBook(User user) {
		Connection connection = null;
		Book book = user.getBook();

		try {
			
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			if(BookTransactionDAO.bookHasPlace(connection, book)){
				PlaceTransactionDAO.removeBookPlace(connection, book);
				PlaceTransactionDAO.updateRecoveredPlace(connection, book
						.getPlace().getId());
				RecoveredTransactionDAO.addRecovered(connection, user);
				BookTransactionDAO.updateRecoveredBook(connection, book.getId());
			}
			else{
				return Response.status(Status.CONFLICT).build();
			}
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
		return Response.ok().build();
	}

	@POST
	@Path("/favorite")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFavoriteBook(User user) {
		Connection connection = null;
		Book book = user.getBook();

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			if (!BookTransactionDAO.bookExist(connection, book)) {
				BookTransactionDAO.insertBook(connection, book);
				FavoriteTransactionDAO.addFavorite(connection, user);
			} else {
				if (FavoriteTransactionDAO.favoriteExist(connection, user)) {
					FavoriteTransactionDAO.deleteFavorite(connection, user);
				} else {
					FavoriteTransactionDAO.addFavorite(connection, user);
				}
			}

			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
		return Response.ok().build();
	}

	@GET
	@Path("/donation/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBookDonatedByUser(@PathParam("idUser") String idUser) {
		return DonationTransactionDAO.getBookDonated(idUser);
	}

	@GET
	@Path("/recovered/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooksRecoveredByUser(@PathParam("idUser") String idUser) {
		return RecoveredTransactionDAO.getBooksRecovered(idUser);
	}

	@GET
	@Path("/favorite/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooksFavoriteByUser(@PathParam("idUser") String idUser) {
		return FavoriteTransactionDAO.getBooksFavorite(idUser);
	}
	
	@GET
	@Path("/review/{idBook}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getReviewsByBook(@PathParam("idBook") String idBook) {
		return BookTransactionDAO.getReviewsByBook(idBook);
	}
	
	@GET
	@Path("/statistic")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBestBooksCurrentMonth() {
		return BookTransactionDAO.getBestBooksCurrentMonth();
	}

	@POST
	@Path("/review")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReviewBook(Review review) {

		Connection connection = null;
		Book book = review.getBook();

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			if (!BookTransactionDAO.bookExist(connection, book)) {
				BookTransactionDAO.insertBook(connection, book);
			}

			BookTransactionDAO.addReviewBook(connection, review);

			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}

		return Response.status(Response.Status.CREATED).build();
	}
}
