package br.edu.ifce.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;
import br.edu.ifce.model.User;
import br.edu.ifce.util.DateUtil;
import br.edu.ifce.util.RegraNegocioException;

public class PlaceTransactionDAO {
	
	public static void insertPlace(Connection con, Place place) throws SQLException{
		PreparedStatement pstm = null;
		
		if(place.getPhoto()!=null){
			pstm = con.prepareStatement("insert into place(nome,city,states,street,district,number,cep,latitude,longitude,hour_func,photo,recovered,donation) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstm.setString(1,place.getName());
			pstm.setString(2,place.getCity());
			pstm.setString(3,place.getStates());
			pstm.setString(4,place.getStreet());
			pstm.setString(5,place.getDistrict());
			pstm.setString(6,place.getNumber());
			pstm.setString(7,place.getCep());
			pstm.setDouble(8,place.getLatitude());
			pstm.setDouble(9,place.getLongitude());
			pstm.setString(10,place.getHour_func());
			pstm.setBytes(11, place.getPhoto());
			pstm.setInt(12, new Integer(0));
			pstm.setInt(13, new Integer(0));
		
			pstm.execute();
		}	
		pstm.close();
	}

	public static void updateBookPlace(Connection con, Book book) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("update book_has_place set total=total+1 where book_id = ? and place_id = ?");
		pstm.setString(1,book.getId());
		pstm.setInt(2,book.getPlace().getId());
		pstm.executeUpdate();
			
		pstm.close();
	}
	
	public static void insertBookPlace(Connection con, User user) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("insert into book_has_place(place_id,book_id,total) values (?,?,?)");
		pstm.setInt(1, user.getBook().getPlace().getId());
		pstm.setString(2, user.getBook().getId());
		pstm.setInt(3, new Integer(1));
		pstm.execute();
			
		pstm.close();
	}
	
	public static void updateDonationTotal(Connection con, int idPlace) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("update place set donation=donation+1 where id = ?");
		pstm.setInt(1,idPlace);
		pstm.executeUpdate();
			
		pstm.close();
	}
	
	public static void updateRecoveredPlace(Connection con, int idPlace) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("update place set recovered=recovered+1 where id = ?");
		pstm.setInt(1,idPlace);
		pstm.executeUpdate();
		
		pstm.close();
	}
	
	public static void removeBookPlace(Connection con, Book book) throws SQLException{
		PreparedStatement pstm = null;

		pstm = con.prepareStatement("update book_has_place set total=total-1 where book_id = ? and place_id = ?");
		pstm.setString(1,book.getId());
		pstm.setInt(2,book.getPlace().getId());
		pstm.executeUpdate();
			
		pstm.close();
	}
	
	
	public static List<Book> getBooksByIdPlace(String idPlace){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from book_has_place bp join book b where b.id=bp.book_id and bp.total > 0 and bp.place_id=?");
			pstm.setInt(1, Integer.valueOf(idPlace));
			
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
				book.setDateDonation(getDateDonation(idPlace));
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
	
	private static Date getDateDonation(String idPlace) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Date date = null;
		
		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select date_donation from donation where place_id=? order by date_donation desc limit 1");
			pstm.setInt(1, Integer.valueOf(idPlace));
			
			rs = pstm.executeQuery();

			while(rs.next()){
				date = rs.getDate("date_donation"); 
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
		return date;
	}

	public static Place getPlaceByIdWithBooks(String idPlace){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Place place = null;
		
		List<Book> books = new ArrayList<Book>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from place where id=?");
			pstm.setInt(1, Integer.valueOf(idPlace));
			
			rs = pstm.executeQuery();

			while(rs.next()){
				place = new Place();
				place.setId(rs.getInt("id"));
				place.setName(rs.getString("nome"));
				place.setCity(rs.getString("city"));
				place.setStates(rs.getString("states"));
				place.setStreet(rs.getString("street"));
				place.setDistrict(rs.getString("district"));
				place.setNumber(rs.getString("number"));
				place.setCep(rs.getString("cep"));
				place.setLatitude(rs.getDouble("latitude"));
				place.setLongitude(rs.getDouble("longitude"));
				place.setRecovered(rs.getInt("recovered"));
				place.setDonation(rs.getInt("donation"));
				place.setHour_func(rs.getString("hour_func"));
				place.setPhoto(rs.getBytes("photo"));
			}
			
			books.addAll(getBooksByIdPlace(idPlace));
			place.setBooks(books);
			
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
		return place;
	}

	public static List<Place> getPlaceByCityState(String city, String state) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Place place = null;
		List<Place> places = new ArrayList<>();
		
		
		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from place where city=? and states=?");
			pstm.setString(1, city);
			pstm.setString(2, state);
			
			rs = pstm.executeQuery();

			while(rs.next()){
				List<Book> books = new ArrayList<Book>();
				place = new Place();
				place.setId(rs.getInt("id"));
				place.setName(rs.getString("nome"));
				place.setCity(rs.getString("city"));
				place.setStates(rs.getString("states"));
				place.setDistrict(rs.getString("district"));
				place.setStreet(rs.getString("street"));
				place.setNumber(rs.getString("number"));
				place.setLatitude(rs.getDouble("latitude"));
				place.setLongitude(rs.getDouble("longitude"));
				place.setRecovered(rs.getInt("recovered"));
				place.setDonation(rs.getInt("donation"));
				place.setCep(rs.getString("cep"));
				place.setHour_func(rs.getString("hour_func"));
				place.setPhoto(rs.getBytes("photo"));
				
				books.addAll(getBooksByIdPlace(String.valueOf(place.getId())));
				place.setBooks(books);
				
				places.add(place);
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
		return places;
	}
	
	public static Place getPlaceById(String idPlace) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Place place =  new Place();
		
		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from place where id=?");
			pstm.setString(1, idPlace);
			
			rs = pstm.executeQuery();

			while(rs.next()){
				place.setId(rs.getInt("id"));
				place.setName(rs.getString("nome"));
				place.setCity(rs.getString("city"));
				place.setStates(rs.getString("states"));
				place.setDistrict(rs.getString("district"));
				place.setStreet(rs.getString("street"));
				place.setNumber(rs.getString("number"));
				place.setLatitude(rs.getDouble("latitude"));
				place.setLongitude(rs.getDouble("longitude"));
				place.setRecovered(rs.getInt("recovered"));
				place.setDonation(rs.getInt("donation"));
				place.setCep(rs.getString("cep"));
				place.setHour_func(rs.getString("hour_func"));
				place.setPhoto(rs.getBytes("photo"));
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
		return place;
	}
	
	
	public static List<Place> getBestPlaceCurrentMonth(String city, String state) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		Place place = null;
		List<Place> places = new ArrayList<Place>();
		
		String initDate = DateUtil.getInitDate();
		String endDate = DateUtil.getEndDate();

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("(select p.id as idPlace,p.nome,p.city,p.district,p.street,p.number,p.photo,p.states, "
					+ "COALESCE(don.qtd_doacoes,0) as qtd_doacoes,COALESCE(rec.qtd_retiradas,0) as qtd_retiradas, "
					+ "COALESCE(qtd_doacoes,0) + COALESCE(qtd_retiradas,0) as soma from place p natural join "
					+ "(select d.place_id, count(*) as qtd_doacoes from donation d where date_donation "
					+ "between '"+initDate+"' AND '"+endDate+"' group by place_id) as don natural left join "
					+ "(select place_id, count(*) as qtd_retiradas from recovered where date_recovered"
					+ " between '"+initDate+"' AND '"+endDate+"' group by place_id) as rec where p.id=don.place_id "
					+ "and p.city='"+city+"' and p.states='"+state+"') "
					+ "union "
					+ "(select p.id as idPlace,p.nome,p.city,p.district,p.street,p.number,p.photo,p.states, "
					+ "COALESCE(don.qtd_doacoes,0),COALESCE(rec.qtd_retiradas,0) as qtd_retiradas, "
					+ "COALESCE(qtd_doacoes,0) + COALESCE(qtd_retiradas,0) as soma from place p natural join "
					+ "(select d.place_id, count(*) as qtd_retiradas from recovered d where date_recovered "
					+ "between '"+initDate+"' AND '"+endDate+"' group by place_id) as rec natural left join "
					+ "(select place_id, count(*) as qtd_doacoes from donation where date_donation "
					+ "between '"+initDate+"' AND '"+endDate+"' group by place_id) as don where p.id=rec.place_id "
					+ "and p.city='"+city+"' and p.states='"+state+"') "
					+ "order by soma DESC");
			
			while(rs.next()){
				place = new Place();
				place.setId(rs.getInt("idPlace"));
				place.setName(rs.getString("nome")); 
				place.setCity(rs.getString("city"));
				place.setDistrict(rs.getString("district"));
				place.setStreet(rs.getString("street"));
				place.setNumber(rs.getString("number"));
				place.setStates(rs.getString("states"));
				place.setDonation(rs.getInt("qtd_doacoes"));
				place.setRecovered(rs.getInt("qtd_retiradas"));
				place.setPhoto(rs.getBytes("photo"));
				
				places.add(place);
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
		return places;
	}
	
	public static List<Place> getPlaces(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Place place = null;
		List<Place> places = new ArrayList<>();
		
		
		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from place");
			rs = pstm.executeQuery();

			while(rs.next()){
				place = new Place();
				place.setId(rs.getInt("id"));
				place.setName(rs.getString("nome"));
				place.setCity(rs.getString("city"));
				place.setStates(rs.getString("states"));
				place.setDistrict(rs.getString("district"));
				place.setStreet(rs.getString("street"));
				place.setNumber(rs.getString("number"));
				place.setLatitude(rs.getDouble("latitude"));
				place.setLongitude(rs.getDouble("longitude"));
				place.setRecovered(rs.getInt("recovered"));
				place.setDonation(rs.getInt("donation"));
				place.setCep(rs.getString("cep"));
				place.setHour_func(rs.getString("hour_func"));
				place.setPhoto(rs.getBytes("photo"));
				
				places.add(place);
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
		return places;
	}
	
	public static void delete(int idPlace) throws RegraNegocioException{
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			
			con = ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			pstm = con.prepareStatement("delete from place where id=?");
			pstm.setInt(1, idPlace);  
			pstm.executeUpdate();
			
			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}finally{
			try {
				con.setAutoCommit(true);
				pstm.close();
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void update(Connection con,Place place){
		PreparedStatement pstm = null;
		con = ConnectionFactory.getConnection();
		String stringSql="update place set"
				+ " nome=?,city=?,states=?,district=?,street=?,number=?,latitude=?,longitude=?,cep=?,hour_func=?,photo=?"
				+ " where id=?";
		try {
			
			pstm = con.prepareStatement(stringSql);
			pstm.setString(1,place.getName());
			pstm.setString(2,place.getCity());
			pstm.setString(3,place.getStates());
			pstm.setString(4,place.getDistrict());
			pstm.setString(5,place.getStreet());
			pstm.setString(6,place.getNumber());
			pstm.setDouble(7,place.getLatitude());
			pstm.setDouble(8,place.getLongitude());
			pstm.setString(9,place.getCep());
			pstm.setString(10,place.getHour_func());
			pstm.setBytes(11, place.getPhoto());
			pstm.setInt(12, place.getId());
			pstm.executeUpdate();
			
		} catch (SQLException e) {
				System.out.println(e.getMessage());
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
