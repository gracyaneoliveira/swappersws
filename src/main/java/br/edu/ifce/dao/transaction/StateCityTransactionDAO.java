package br.edu.ifce.dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.StateCity;

public class StateCityTransactionDAO {

	public static List<StateCity> getStateCity() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StateCity stateCity = null;
		List<StateCity> stateCityList = new ArrayList<StateCity>();

		con = ConnectionFactory.getConnection();
		try {
			pstm = con.prepareStatement("SELECT distinct p.city, p.states FROM place p");
			rs = pstm.executeQuery();
			
			while(rs.next()){
				stateCity = new StateCity();
				stateCity.setCity(rs.getString("city"));
				stateCity.setState(rs.getString("states"));
				
				stateCityList.add(stateCity);
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
		return stateCityList;
	}
}
