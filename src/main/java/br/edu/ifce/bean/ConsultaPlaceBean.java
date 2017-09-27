package br.edu.ifce.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifce.dao.transaction.PlaceTransactionDAO;
import br.edu.ifce.model.Place;
import br.edu.ifce.util.RegraNegocioException;

@ManagedBean
@ViewScoped
public class ConsultaPlaceBean implements Serializable{
	
	public List<Place> places = new ArrayList<>();
	public Place placeSelecionado;
	
	@PostConstruct
	public void inicializar(){
		this.places.clear();
		this.places = PlaceTransactionDAO.getPlaces();
	}
	
	public void excluir() {
		try {
			PlaceTransactionDAO.delete(placeSelecionado.getId());
			this.inicializar();
			adicionarMensagem(FacesMessage.SEVERITY_INFO, "Lugar "+placeSelecionado.getName()+" excluído com sucesso!");
		} catch (RegraNegocioException e) {
			adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível excluir o lugar, "+e.getMessage());
		}
		
	}
	
	public void adicionarMensagem(Severity tipo, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(tipo, msg, msg));
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public Place getPlaceSelecionado() {
		return placeSelecionado;
	}

	public void setPlaceSelecionado(Place placeSelecionado) {
		this.placeSelecionado = placeSelecionado;
	}
	
}
