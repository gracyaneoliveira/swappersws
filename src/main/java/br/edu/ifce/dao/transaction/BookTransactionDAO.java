package br.edu.ifce.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.Review;
import br.edu.ifce.util.DateUtil;

public class BookTransactionDAO {

	public static boolean bookExist(Connection con, Book book)
			throws SQLException {
		Statement stm = null;
		ResultSet rs = null;

		boolean bookExist = false;

		stm = con.createStatement();
		rs = stm.executeQuery("select * from book where id = '" + book.getId() + "'");

		while (rs.next()) {
			bookExist = true;
		}

		stm.close();
		rs.close();

		return bookExist;
	}

	public static boolean bookHasPlace(Connection con, Book book)
			throws SQLException {
		Statement stm = null;
		ResultSet rs = null;

		boolean bookExistHasPlace = false;

		stm = con.createStatement();
		rs = stm.executeQuery("select * from book_has_place where book_id = '"
				+ book.getId() + "' and place_id=" + "'"
				+ book.getPlace().getId() + "'" + " and total > 0" );

		while (rs.next()) {
			bookExistHasPlace = true;
		}

		stm.close();
		rs.close();
		return bookExistHasPlace;
	}

	public static void insertBook(Connection con, Book book) throws SQLException {
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("insert into book(id,title,author,publishing,book_photo,numPage,evaluationAverage,recovered,synopsis,donations) values (?,?,?,?,?,?,?,?,?,?)");
		pstm.setString(1, book.getId());
		pstm.setString(2, book.getTitle());
		pstm.setString(3, book.getAuthor());
		pstm.setString(4, book.getPublisher());
		pstm.setString(5, book.getPhoto());
		pstm.setInt(6, book.getNumberPage());
		pstm.setDouble(7, book.getEvaluationAverage());
		pstm.setInt(8, book.getRecovered());
		pstm.setString(9, book.getSynopsis());
		pstm.setInt(10, book.getDonation());
		pstm.execute();

		pstm.close();
	}

	public static void updateDonationBook(Connection con, String idBook) throws SQLException {
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("update book set donations=donations+1 where id = ?");
		pstm.setString(1, idBook);
		pstm.executeUpdate();

		pstm.close();
	}

	public static void updateRecoveredBook(Connection con, String idBook) throws SQLException {
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("update book set recovered=recovered+1 where id = ?");
		pstm.setString(1, idBook);
		pstm.executeUpdate();

		pstm.close();
	}

	public static void addReviewBook(Connection con,Review review) throws SQLException {
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("insert into review(users_id,book_id,reviewText,dateReview) values (?,?,?,?)");
		pstm.setInt(1, review.getIdUser());
		pstm.setString(2, review.getBook().getId());
		pstm.setString(3, review.getReview());
		
		Date date = new Date();
		Object paramDate = new Timestamp(date.getTime());    
		pstm.setObject(4, paramDate);

		pstm.execute();

		pstm.close();
	}
	
	public static List<Review> getReviewsByBook(String idBook){
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		Review review = null;
		List<Review> reviews = new ArrayList<Review>();

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT u.id, u.name, r.dateReview, r.reviewText FROM review r natural join users u where u.id=r.users_id AND r.book_id='"+idBook+"'");
			
			while(rs.next()){
				review = new Review();
				review.setName(rs.getString("name")); 
				review.setReview((rs.getString("reviewText")));
				review.setDataReview(rs.getDate("dateReview"));
				reviews.add(review);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				stm.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return reviews;
		
	}
	
	public static List<Book> getBestBooksCurrentMonth(){
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();
		
		String initDate = DateUtil.getInitDate();
		String endDate = DateUtil.getEndDate();

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select book.id, book.title, book.book_photo,book.author , dr.quant_doados, COALESCE(dr.quant_retirado,0) as quant_retirado, "
					+ "dr.quant_doados + COALESCE(dr.quant_retirado,0) as soma from book join "
					+ "(select rec.book_id as recovered, don.book_id as donated, don.quant_doados, rec.quant_retirado from "
					+ "(select book_id, count(*) as quant_retirado from recovered "
					+ "where date_recovered BETWEEN '"+initDate+"' AND '"+endDate+"' GROUP BY book_id) as rec natural right join "
					+ "(select book_id , count(*) as quant_doados from donation where date_donation "
					+ "BETWEEN '"+initDate+"' AND '"+endDate+"' GROUP BY book_id) as don) as dr where book.id = dr.donated "
					+ "ORDER BY dr.quant_doados DESC, dr.quant_retirado DESC limit 10");
			
			while(rs.next()){
				book = new Book();
				book.setId(rs.getString("id"));
				book.setTitle(rs.getString("title")); 
				book.setPhoto(rs.getString("book_photo"));
				book.setDonation(rs.getInt("quant_doados"));
				book.setRecovered(rs.getInt("quant_retirado"));
				books.add(book);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				stm.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return books;
	}

}
