package br.edu.ifce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;
import br.edu.ifce.model.User;

public class PlaceDAO extends ConnectionFactory{
	
	public static void updateBookPlace(Book book){
		Connection con = null;
		PreparedStatement pstm = null;

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update book_has_place set total=total+1 where book_id = ? and place_id = ?");
			pstm.setString(1,book.getId());
			pstm.setInt(2,book.getPlace().getId());
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void insertBookPlace(User user){
		Connection con = null;
		PreparedStatement pstm = null;
		con = ConnectionFactory.getConnection();

		try {
			pstm = con.prepareStatement("insert into book_has_place(place_id,book_id,total) values (?,?,?)");
			pstm.setInt(1, user.getBook().getPlace().getId());
			pstm.setString(2, user.getBook().getId());
			pstm.setInt(3, new Integer(1));
			pstm.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void updateDonationTotal(int idPlace){
		Connection con = null;
		PreparedStatement pstm = null;

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update place set donation=donation+1 where id = ?");
			pstm.setInt(1,idPlace);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void updateRecoveredPlace(int idPlace){
		Connection con = null;
		PreparedStatement pstm = null;

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update place set recovered=recovered+1 where id = ?");
			pstm.setInt(1,idPlace);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void removeBookPlace(Book book){
		Connection con = null;
		PreparedStatement pstm = null;

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update book_has_place set total=total-1 where book_id = ? and place_id = ?");
			pstm.setString(1,book.getId());
			pstm.setInt(2,book.getPlace().getId());
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
