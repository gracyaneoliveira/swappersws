package br.edu.ifce.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.AdoptionRecord;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.User;

public class RecoveredTransactionDAO {
	
	public static void addRecovered(Connection con, User user) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("insert into recovered(users_id,book_id,date_recovered,place_id) values (?,?,?,?)");
		pstm.setInt(1, user.getId());
		pstm.setString(2, user.getBook().getId());
		pstm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pstm.setInt(4, user.getBook().getPlace().getId());
		pstm.execute();
			
		pstm.close();
	}
	
	public static List<Book> getBooksRecovered(String idUser){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from recovered d join book b where b.id = d.book_id and users_id = ? ");
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
	
	public static List<AdoptionRecord> getAdoptionsRecord(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		AdoptionRecord adoptionRecord = null;
		List<AdoptionRecord> adoptionRecordList = new ArrayList<AdoptionRecord>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement(
					"SELECT u.name, u.email,p.nome, u.city, u.state,b.title, r.date_recovered "
					+ "FROM recovered r "
					+ "JOIN users u on u.id = r.users_id "
					+ "JOIN book b on b.id=r.book_id "
					+ "JOIN place p on p.id=r.place_id "
					+ "ORDER BY r.date_recovered DESC");
			
			rs = pstm.executeQuery();
			
			while(rs.next()){
				adoptionRecord = new AdoptionRecord();
				adoptionRecord.setNameUser(rs.getString("name"));
				adoptionRecord.setEmail(rs.getString("email"));
				adoptionRecord.setCity(rs.getString("city"));
				adoptionRecord.setState(rs.getString("state"));
				adoptionRecord.setTitleBook(rs.getString("title"));
				adoptionRecord.setDateDonation(rs.getDate("date_recovered"));
				adoptionRecord.setNameLocal(rs.getString("nome"));
				adoptionRecordList.add(adoptionRecord);
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
		return adoptionRecordList;
	}

}
