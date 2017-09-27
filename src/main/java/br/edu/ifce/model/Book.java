package br.edu.ifce.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {

	private String id;
    private String title;
    private String author;
    private String publisher;
    private String photo="";
    private String datePublisher;
    private String synopsis;
    private int recovered;
    private int donation;
    private int numberPage;
    private Date dateDonation;
    private float evaluationAverage;
    private float userEvaluation;
    private Place place;
    private List<Place> placeList;

    public Book() {
    }

    public Book(String title, String author, String publisher, float evaluationAverage, float userEvaluation) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.evaluationAverage = evaluationAverage;
        this.userEvaluation = userEvaluation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public float getEvaluationAverage() {
        return evaluationAverage;
    }
    
    public void setEvaluationAverage(float evaluationAverage) {
        this.evaluationAverage = evaluationAverage;
    }

    @XmlElement(required=false)
    public float getUserEvaluation() {
        return userEvaluation;
    }

    public void setUserEvaluation(float userEvaluation) {
        this.userEvaluation = userEvaluation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDatePublisher() {
        return datePublisher;
    }

    public void setDatePublisher(String datePublisher) {
        this.datePublisher = datePublisher;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

	public List<Place> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<Place> placeList) {
		this.placeList = placeList;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getDonation() {
		return donation;
	}

	public void setDonation(int donation) {
		this.donation = donation;
	}

	public Date getDateDonation() {
		return dateDonation;
	}

	public void setDateDonation(Date dateDonation) {
		this.dateDonation = dateDonation;
	}
	
}
