package br.edu.ifce.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.model.Notification;

import com.mysql.jdbc.Blob;

public class TimelineDAO {
	
	public static List<Notification> getTimeline(String state) {
		
		List<Notification> timelines = new ArrayList<Notification>();
		timelines.addAll(getTimelineDonation(state));
		timelines.addAll(getTimelineRecovered(state));
		timelines.addAll(getTimelineReviews());
		
		Collections.sort(timelines, Collections.reverseOrder(new Comparator<Notification>() {
		    public int compare(Notification t1, Notification t2) {
		        return t1.getTimestamp().compareTo(t2.getTimestamp());
		    }
		}));
		
		return timelines;
	}
	
	private static List<Notification> getTimelineDonation(String state){
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		List<Notification> timelineList = new ArrayList<Notification>();
		Notification timeline = null;
		
		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"select u.name,u.photo,d.date_donation,p.city,p.states,p.nome,b.title " 
					+"from donation d inner join users u on u.id=d.users_id "
					+ "inner join place p on p.id = d.place_id and p.states='"+state+"' "
					+ "inner join book b " 
					+ "on b.id=d.book_id "
					+ "WHERE date_donation BETWEEN CURRENT_TIMESTAMP() - INTERVAL 60 DAY AND CURRENT_TIMESTAMP()");
			
			while(rs.next()){
				timeline = new Notification();
				timeline.setUserName(rs.getString("name"));
				timeline.setEventType("donation");
				timeline.setPlaceName(rs.getString("nome"));
				timeline.setCity(rs.getString("city"));
				timeline.setRegion(rs.getString("states"));
				timeline.setTimestamp(rs.getTimestamp("date_donation"));
				timeline.setBookTitle(rs.getString("title"));
				timeline.setReviewText("");
				
				Blob blob = (Blob) rs.getBlob("photo");
				
				timeline.setPhotoUser(new String(blob.getBytes(1, (int) blob.length())));
				
				timelineList.add(timeline);
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
		return timelineList;
	}
	
	private static List<Notification> getTimelineRecovered(String state){
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		List<Notification> timelineList = new ArrayList<Notification>();
		Notification timeline = null;
		
		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"select u.name,u.photo,d.date_recovered,p.city,p.states,p.nome,b.title " 
					+"from recovered d inner join users u on u.id=d.users_id "
					+ "inner join place p on p.id = d.place_id and p.states='"+state+"' "
					+ "inner join book b " 
					+ "on b.id=d.book_id "
					+ "WHERE date_recovered BETWEEN CURRENT_TIMESTAMP() - INTERVAL 60 DAY AND CURRENT_TIMESTAMP()");
			
			while(rs.next()){
				timeline = new Notification();
				timeline.setUserName(rs.getString("name"));
				timeline.setEventType("recovered");
				timeline.setPlaceName(rs.getString("nome"));
				timeline.setCity(rs.getString("city"));
				timeline.setRegion(rs.getString("states"));
				timeline.setTimestamp(rs.getTimestamp("date_recovered"));
				timeline.setBookTitle(rs.getString("title"));
				timeline.setReviewText("");
				
				Blob blob = (Blob) rs.getBlob("photo");
				
				timeline.setPhotoUser(new String(blob.getBytes(1, (int) blob.length())));
				
				timelineList.add(timeline);
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
		return timelineList;
	}
	
	private static List<Notification> getTimelineReviews(){
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		List<Notification> timelineList = new ArrayList<Notification>();
		Notification timeline = null;
		
		con = ConnectionFactory.getConnection();
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(
					"select u.name,u.photo,d.dateReview,d.reviewText,b.title "
					+ "from review d inner join users u on u.id=d.users_id  "
					+ "inner join book b on b.id=d.book_id "
					+ "WHERE d.dateReview BETWEEN CURRENT_TIMESTAMP() - INTERVAL 60 DAY AND CURRENT_TIMESTAMP()");
			
			while(rs.next()){
				timeline = new Notification();
				timeline.setUserName(rs.getString("name"));
				timeline.setEventType("review");
				timeline.setPlaceName("");
				timeline.setCity("");
				timeline.setRegion("");
				timeline.setTimestamp(rs.getTimestamp("dateReview"));
				timeline.setBookTitle(rs.getString("title"));
				timeline.setReviewText(rs.getString("reviewText"));
				
				Blob blob = (Blob) rs.getBlob("photo");
				
				timeline.setPhotoUser(new String(blob.getBytes(1, (int) blob.length())));
				
				timelineList.add(timeline);
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
		return timelineList;
	}
}
