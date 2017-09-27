package br.edu.ifce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;

public class BookDAO extends ConnectionFactory{
	
	public static boolean bookExist(Book book) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		
		boolean bookExist = false;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from book where id = '"+book.getId() + "'");
			
			while(rs.next()){
				bookExist = true;
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
		return bookExist;
	}

	public static boolean bookHasPlace(Book book) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		
		boolean bookExistHasPlace = false;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from book_has_place where book_id = '"+book.getId()+ "' and place_id=" + "'" + book.getPlace().getId() + "'");
			
			while(rs.next()){
				bookExistHasPlace = true;
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
		return bookExistHasPlace;
	}
	
	public static void insertBook(Book book) {
		Connection con = null;
		PreparedStatement pstm = null;
		
		con = ConnectionFactory.getConnection();

		try {
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
			pstm.setInt(10,book.getDonation());
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
	
	public static void updateDonationBook(String idBook){
		Connection con = null;
		PreparedStatement pstm = null;

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update book set donations=donations+1 where id = ?");
			pstm.setString(1,idBook);
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
	
	public static void updateRecoveredBook(String idBook){
		Connection con = null;
		PreparedStatement pstm = null;

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update book set recovered=recovered+1 where id = ?");
			pstm.setString(1,idBook);
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
