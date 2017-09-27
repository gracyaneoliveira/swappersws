package br.edu.ifce.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifce.dao.transaction.RecoveredTransactionDAO;
import br.edu.ifce.model.AdoptionRecord;

@ManagedBean
@ViewScoped
public class ConsultaAdocaoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public List<AdoptionRecord> adoptionRecordList = new ArrayList<>();
	
	
	@PostConstruct
	public void inicializar(){
		this.adoptionRecordList.clear();
		this.adoptionRecordList = RecoveredTransactionDAO.getAdoptionsRecord();
	}

	public List<AdoptionRecord> getAdoptionRecordList() {
		return adoptionRecordList;
	}

	public void setDonationRecordList(List<AdoptionRecord> adoptionRecordList) {
		this.adoptionRecordList = adoptionRecordList;
	}
	
}
