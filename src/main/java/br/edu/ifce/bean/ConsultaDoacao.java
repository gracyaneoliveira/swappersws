package br.edu.ifce.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifce.dao.transaction.DonationTransactionDAO;
import br.edu.ifce.model.DonationRecord;

@ManagedBean
@ViewScoped
public class ConsultaDoacao implements Serializable{
	
	public List<DonationRecord> donationRecordList = new ArrayList<>();
	
	
	@PostConstruct
	public void inicializar(){
		this.donationRecordList.clear();
		this.donationRecordList = DonationTransactionDAO.getDonationsRecord();
	}

	public List<DonationRecord> getDonationRecordList() {
		return donationRecordList;
	}

	public void setDonationRecordList(List<DonationRecord> donationRecordList) {
		this.donationRecordList = donationRecordList;
	}
	
}
