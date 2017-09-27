package br.edu.ifce.security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.ifce.connection.ConnectionFactory;

public class SecurityDAO {
	
	public static boolean checkIfUserAdminExist(String user, String pwd) {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		
		boolean userAdminExist = false;

		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery("select * from security where nome_usuario ='"+user+"' and senha='"+pwd+"' and permissao_usuario='admin'");
			
			while(rs.next()){
				userAdminExist = true;
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
		return userAdminExist;
	}

}
