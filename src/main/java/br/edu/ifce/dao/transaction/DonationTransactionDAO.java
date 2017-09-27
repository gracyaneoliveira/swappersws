package br.edu.ifce.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.DonationRecord;
import br.edu.ifce.model.User;

public class DonationTransactionDAO {
	
	public static void addDonation(Connection con, User user) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("insert into donation(users_id,book_id,date_donation,place_id) values (?,?,?,?)");
		pstm.setInt(1, user.getId());
		pstm.setString(2, user.getBook().getId());
		pstm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pstm.setInt(4, user.getBook().getPlace().getId());
		pstm.execute();
			
		pstm.close();
	}
	
	public static List<Book> getBookDonated(String idUser){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from donation d join book b where b.id = d.book_id and users_id = ? ");
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
	
	public static List<DonationRecord> getDonationsRecord(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		DonationRecord donationRecord = null;
		List<DonationRecord> donationRecordList = new ArrayList<DonationRecord>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement(
					"SELECT u.name, u.email,p.nome, u.city, u.state,b.title, d.date_donation "
					+ "FROM donation d "
					+ "JOIN users u on u.id = d.users_id "
					+ "JOIN book b on b.id=d.book_id "
					+ "JOIN place p on p.id=d.place_id "
					+ "ORDER BY d.date_donation DESC");
			
			rs = pstm.executeQuery();
			
			while(rs.next()){
				donationRecord = new DonationRecord();
				donationRecord.setNameUser(rs.getString("name"));
				donationRecord.setEmail(rs.getString("email"));
				donationRecord.setCity(rs.getString("city"));
				donationRecord.setState(rs.getString("state"));
				donationRecord.setTitleBook(rs.getString("title"));
				donationRecord.setDateDonation(rs.getDate("date_donation"));
				donationRecord.setNameLocal(rs.getString("nome"));
				donationRecordList.add(donationRecord);
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
		return donationRecordList;
	}

}
