package br.edu.ifce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.User;

public class DonationDAO extends ConnectionFactory{
	
	public static void addDonation(User user){
		Connection con = null;
		PreparedStatement pstm = null;
		con = ConnectionFactory.getConnection();

		try {
			pstm = con.prepareStatement("insert into donation(users_id,book_id) values (?,?)");
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

}
