package br.edu.ifce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Blob;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Book;
import br.edu.ifce.model.Place;
import br.edu.ifce.model.User;
import br.edu.ifce.util.DateUtil;

public class UserDAO extends ConnectionFactory{
	
	public static boolean checkLogin(String email, String pwd) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		
		boolean isUserAvailable = false;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from users where email = '"+email+ "' and password=" + "'" + pwd + "'");
			
			while(rs.next()){
				isUserAvailable = true;
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
		return isUserAvailable;
	}

	public static Integer insertUser(User user) {
		Connection con = null;
		PreparedStatement pstm = null;
		Integer key = null;
		con = ConnectionFactory.getConnection();

		try {

			//Blob blob = (Blob) con.createBlob();
			//blob.setBytes(0, user.getPhoto());
			
			pstm = con.prepareStatement("insert into users(name,email,password,photo) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, user.getUsername());
			pstm.setString(2, user.getEmail());
			pstm.setString(3, user.getPassword());
//			pstm.setBytes(4, user.getPhoto());
			
			if(user.getPhoto2()!=null){
				pstm.setBytes(4, user.getPhoto2().getBytes());
			}else{
				pstm.setBytes(4, "".getBytes());
			}
//			pstm.setString(4, user.getPhoto2());
			pstm.execute();
			
			ResultSet keys = pstm.getGeneratedKeys();
			keys.next();  
			key = keys.getInt(1);
			
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
		return key;
	}
	
	public static boolean checkIfEmailAlreadyExist(String email) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		
		boolean emailExist = false;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from users where email ='"+email+ "'");
			
			while(rs.next()){
				emailExist = true;
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
		return emailExist;
	}

	public static User getUsersForID(String id) {
			Connection con = null;
			Statement stm = null;
			ResultSet rs = null;
			User person = null;

			con = ConnectionFactory.getConnection();
			try {
				stm = con.createStatement();
				rs = stm.executeQuery("select * from users where id = '"+id+"'");
				
				while(rs.next()){
					person = new User();
					person.setId(Integer.valueOf(rs.getInt("id")));
					person.setEmail(rs.getString("email"));
					person.setPassword(rs.getString("password"));
					person.setUsername(rs.getString("name"));
					
					Blob blob = (Blob) rs.getBlob("photo");
					
					person.setPhoto(blob.getBytes(1, (int) blob.length()));
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
			return person;
		}
	
	public static List<User> getDonorsForMonthCurrent(String city, String state) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<User>();

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT u.name, u.email, u.photo, u.city, u.state,u.dt_birth, count(*) as doacoes FROM donation d join users u WHERE u.id = d.users_id AND u.city='"+city+"' AND u.state='"+state +"' AND date_donation BETWEEN '"+DateUtil.getInitDate()+"' AND '"+DateUtil.getEndDate()+"' GROUP BY u.id ORDER BY doacoes desc limit 5");
			
			while(rs.next()){
				user = new User();
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setCity(rs.getString("city"));
				user.setState(rs.getString("state"));
				user.setDonationNum(rs.getInt("doacoes"));
				//user.setPhoto(rs.getBytes("photo"));
				if(rs.getDate("dt_birth") !=null){
					user.setBirthday(rs.getDate ("dt_birth").getTime());
				}
				
				Blob blob = (Blob) rs.getBlob("photo");
				
				user.setPhoto2(new String(blob.getBytes(1, (int) blob.length())));
				users.add(user);
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
		
		return users;
	}

	public static User getUsersForEmailAndPwd(String email, String pwd) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		User user = null;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from users where email = '"+email+ "' and password=" + "'" + pwd + "'");
			
			while(rs.next()){
				user = new User();
				user.setId(Integer.valueOf(rs.getInt("id")));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("name"));
				
				Blob blob = (Blob) rs.getBlob("photo");
				user.setPhoto2(new String(blob.getBytes(1, (int) blob.length())));

				if(rs.getDate("dt_birth") !=null){
					user.setBirthday(rs.getDate ("dt_birth").getTime());
				}
				
				Blob blobCover = (Blob) rs.getBlob("cover");
				if(blobCover !=null){
					user.setCover(new String(blobCover.getBytes(1, (int) blobCover.length())));
				}
				
				if(rs.getString("city")!=null){
					user.setCity(rs.getString("city"));
				}
				
				if(rs.getString("state")!=null){
					user.setState(rs.getString("state"));
				}
				
				user.setBooksDonation(getBookByIdUser(user.getId(),"donation"));
				user.setBooksRetrieved(getBookByIdUser(user.getId(),"recovered"));
				user.setBooksFavorite(getBookByIdUser(user.getId(),"favorite"));
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
		return user;
	}
	
	public static List<Book> getBookByIdUser(Integer idUser,String nameTable){
		//select * from book b join donation d where b.id=d.book_id and d.users_id=98
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Book book = null;
		List<Book> books = new ArrayList<Book>();
		
		String sql = "select * from book b join "+nameTable+" d where b.id=d.book_id and d.users_id=?";

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idUser);
			
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
	
	
	public static void updatePassword(User user){
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update users set password=? where email=?");
			pstm.setString(1, user.getPassword());
			pstm.setString(2, user.getEmail());
			pstm.executeUpdate();
			
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateBirthday(User user){
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update users set dt_birth=? where email=?");
			Object paramDate = new Timestamp(user.getBirthday());    
			pstm.setObject(1, paramDate);
			pstm.setString(2, user.getEmail());
			pstm.executeUpdate();
			
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateCover(User user) {
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update users set cover=? where email=?");
			pstm.setBytes(1, user.getCover().getBytes());
			pstm.setString(2, user.getEmail());
			pstm.executeUpdate();
			
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePhotoPerfil(User user) {
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update users set photo=? where email=?");
			pstm.setBytes(1, user.getPhoto2().getBytes());
			pstm.setString(2, user.getEmail());
			pstm.executeUpdate();
			
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//@SuppressWarnings("resource")
	public static void delete(int idUser){
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			
			con = ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
//			pstm = con.prepareStatement("delete from evaluation where users_id=?");
//			pstm.setInt(1, idUser);  
//			pstm.executeUpdate();
//			//pstm.close();
//			
//			pstm = con.prepareStatement("delete from donation where users_id=?");
//			pstm.setInt(1, idUser);  
//			pstm.executeUpdate();
//			//pstm.close();
//			
//			pstm = con.prepareStatement("delete from favorite where users_id=?");
//			pstm.setInt(1, idUser);  
//			pstm.executeUpdate();
//			//pstm.close();
//			
//			pstm = con.prepareStatement("delete from review where users_id=?");
//			pstm.setInt(1, idUser);  
//			pstm.executeUpdate();
//			//pstm.close();
//			
//			pstm = con.prepareStatement("delete from recovered where users_id=?");
//			pstm.setInt(1, idUser);  
//			pstm.executeUpdate();
			
			pstm = con.prepareStatement("delete from users where id=?");
			pstm.setInt(1, idUser);  
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
	
	public static void updateCityStateUser(User user) {
		PreparedStatement pstm = null;
		Connection con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("update users set city=?,state=? where email=?");
			pstm.setString(1, user.getCity());
			pstm.setString(2, user.getState());
			pstm.setString(3, user.getEmail());
			pstm.executeUpdate();
			
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<User> getUsers(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<>();
		
		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("select * from users");
			rs = pstm.executeQuery();

			while(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password").substring(0, 15)+"...");
				user.setCity(rs.getString("city"));
				user.setState(rs.getString("state"));
				user.setPhoto(rs.getBytes("photo"));
				if(rs.getDate("dt_birth")!=null){
					user.setBirthday(rs.getDate("dt_birth").getTime());
				}
				users.add(user);
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
		return users;
	}
	
}
