package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import it.polito.tdp.newufosightings.model.Adiacenza;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings(State state, int anno) {
		String sql = "SELECT * FROM sighting WHERE state = ? AND YEAR(datetime) = ?";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, state.getId());
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<State> loadAllStates(HashMap<String, State> idMap) {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
				idMap.put(rs.getString("id"), state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Adiacenza> getArchi() {
		String sql = "SELECT * FROM neighbor";
		List<Adiacenza> result = new ArrayList<Adiacenza>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				result.add(new Adiacenza(rs.getString("state1"), rs.getString("state2")));
				
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public double getPeso(int gg, int anno, String state1, String state2) {
		// TODO Auto-generated method stub 
		String sql = "SELECT COUNT(*) AS tot FROM sighting s,sighting s2 WHERE ((s.state = ? AND s2.state = ?) OR (s.state = ? AND s2.state = ?)) AND Day(DATEDIFF(s.DATETIME,s2.DATETIME)) < ? AND YEAR(s.DATETIME) = ? AND YEAR(s2.DATETIME) = YEAR(s.DATETIME)";
		double val = 0.0;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, state1);
			st.setString(2, state2);
			st.setString(3, state2);
			st.setString(4,state1);
			st.setInt(5, gg);
			st.setInt(6, anno);
			
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				
				val = rs.getDouble("tot");
				
			}

			conn.close();
			return val;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
