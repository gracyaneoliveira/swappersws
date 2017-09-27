package br.edu.ifce.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification {

	private String userName;
	private String photoUser;
	private Date timestamp;
	private String bookTitle;
	private String eventType;
	private String placeName;
	private String city;
	private String region;
	private String reviewText;
	
//    private String userName;
//    private String placeName;
//    private String bookTitle;
//    private String city;
//    private String region;
//    private String eventType;
//    private String timestamp;
	
	public Notification(){
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhotoUser() {
		return photoUser;
	}

	public void setPhotoUser(String photoUser) {
		this.photoUser = photoUser;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
}
