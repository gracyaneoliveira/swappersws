package br.edu.ifce.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import br.edu.ifce.connection.ConnectionFactory;
import br.edu.ifce.dao.transaction.PlaceTransactionDAO;
import br.edu.ifce.model.Place;

@ManagedBean
@ViewScoped
public class CadastroLugarBean implements Serializable{
	
	private Place place = new Place();
	private transient Part file;
	private String contentfile;
	
	public void salvar(){
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			if(isEditando()){
				PlaceTransactionDAO.update(connection,this.place);
			}else{
				PlaceTransactionDAO.insertPlace(connection, this.place);
			}
			connection.commit();
			
			adicionarMensagem(FacesMessage.SEVERITY_INFO, "Lugar salvo com sucesso!");
			this.place = new Place();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
				adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar um lugar." + e.getMessage());
			} catch (SQLException e1) {
				adicionarMensagem(FacesMessage.SEVERITY_ERROR, e1.getMessage());
			}
		}
	}
	
	public void uploadFile(ActionEvent event){
		if (file != null) {
			try (InputStream is = file.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				
				int read = -1;
                byte[] buffer = new byte[1024];
                
                while ((read = is.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
				
                place.setPhoto(out.toByteArray());
			} catch (IOException e) {
				throw new RuntimeException("Erro ao enviar arquivo.", e);
			}
		}
	}
	
	public void castImageToString(){
		contentfile = new String(place.getPhoto(), StandardCharsets.UTF_8);
	}
	
	public boolean isEditando() {
		return this.place.getId() != null;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
		if(this.place==null){
			this.place = new Place();
		}
	}
	
	public void adicionarMensagem(Severity tipo, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(tipo, msg, msg));
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
	
}
