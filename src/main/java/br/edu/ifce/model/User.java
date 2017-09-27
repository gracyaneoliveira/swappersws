package br.edu.ifce.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String city;
	private String state;
	private Integer donationNum;
	private byte[] photo;
	private String photo2;
	private String cover;
	private Long birthday;
	private Book book;
	private List<Book> booksRetrieved;
	private List<Book> booksDonation;
	private List<Book> booksFavorite;
	
	
	public User(){
	}
	
	@XmlElement(required=true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement(required=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(required=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlElement(required=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getDonationNum() {
		return donationNum;
	}

	public void setDonationNum(Integer donationNum) {
		this.donationNum = donationNum;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public List<Book> getBooksRetrieved() {
		return booksRetrieved;
	}

	public void setBooksRetrieved(List<Book> booksRetrieved) {
		this.booksRetrieved = booksRetrieved;
	}

	public List<Book> getBooksDonation() {
		return booksDonation;
	}

	public void setBooksDonation(List<Book> booksDonation) {
		this.booksDonation = booksDonation;
	}

	public List<Book> getBooksFavorite() {
		return booksFavorite;
	}

	public void setBooksFavorite(List<Book> booksFavorite) {
		this.booksFavorite = booksFavorite;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email
				+ ", password=" + password + "]";
	}
	
}
