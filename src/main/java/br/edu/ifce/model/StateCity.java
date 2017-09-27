package br.edu.ifce.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StateCity {
	
	private String city;
	private String state;
	
	public StateCity(){
		
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
