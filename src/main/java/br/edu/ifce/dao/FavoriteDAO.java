package br.edu.ifce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.User;

public class FavoriteDAO {
	
	public static void addFavorite(User user){
		Connection con = null;
		PreparedStatement pstm = null;
		con = ConnectionFactory.getConnection();

		try {
			pstm = con.prepareStatement("insert into favorite(users_id,book_id) values (?,?)");
			pstm.setInt(1, user.getId());
			pstm.setString(2, user.getBook().getId());
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
	
	public static void deleteFavorite(User user){
		Connection con = null;
		PreparedStatement pstm = null;
		con = ConnectionFactory.getConnection();

		try {
			pstm = con.prepareStatement("delete from favorite where users_id = ? and book_id = ?");
			pstm.setInt(1, user.getId());
			pstm.setString(2, user.getBook().getId());
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
	
	public static boolean favoriteExist(User user) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		
		boolean favoriteExist = false;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from favorite where book_id = '"+user.getBook().getId()+ "' and users_id=" + "'" + user.getId() + "'");
			
			while(rs.next()){
				favoriteExist = true;
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
		return favoriteExist;
	}
}
