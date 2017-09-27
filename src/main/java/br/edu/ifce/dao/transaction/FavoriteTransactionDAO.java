package br.edu.ifce.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.User;

public class FavoriteTransactionDAO {
	
	public static void addFavorite(Connection con, User user) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("insert into favorite(users_id,book_id) values (?,?)");
		pstm.setInt(1, user.getId());
		pstm.setString(2, user.getBook().getId());
		pstm.execute();
			
		pstm.close();
	}
	
	public static void deleteFavorite(Connection con, User user) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("delete from favorite where users_id = ? and book_id = ?");
		pstm.setInt(1, user.getId());
		pstm.setString(2, user.getBook().getId());
		pstm.execute();
		
		pstm.close();
	}
	
	public static boolean favoriteExist(Connection con, User user) throws SQLException {
		Statement stm = null;
		ResultSet rs = null;
		
		boolean favoriteExist = false;

		stm = con.createStatement();
		rs = stm.executeQuery("select * from favorite where book_id = '"+user.getBook().getId()+ "' and users_id=" + "'" + user.getId() + "'");
			
		while(rs.next()){
			favoriteExist = true;
		}
			
		stm.close();
		rs.close();
		return favoriteExist;
	}

	public static List<Book> getBooksFavorite(String idUser){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from favorite d join book b where b.id = d.book_id and users_id = ? ");
			pstm.setInt(1, Integer.valueOf(idUser));
			
			rs = pstm.executeQuery();
			
			while(rs.next()){
				book = new Book();
				book.setId(String.valueOf(rs.getString("id")));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publishing"));
				book.setNumberPage(rs.getInt("numPage"));
				book.setEvaluationAverage(rs.getFloat("evaluationAverage"));
				book.setRecovered(rs.getInt("recovered"));
				book.setDonation(rs.getInt("donations"));
				book.setSynopsis(rs.getString("synopsis"));
				book.setPhoto(rs.getString("book_photo"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				pstm.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return books;
		
	}

}
