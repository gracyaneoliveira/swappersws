package br.edu.ifce.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifce.security.SecurityDAO;

@ManagedBean
public class LoginBean implements Serializable{
	
	private String usuario;
	private String senha;
	
//	public String logar(){
//		
//		if(isAuthorized(usuario,senha)){
//			return "ConsultaPlace?faces-redirect=true";
//		}else{
//			adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Usuário ou senha incorretos.");
//			return null;
//		}
//	}
	
	public String logar(){
		try {
			this.getRequest().login(this.usuario, this.senha);
			return "Home?faces-redirect=true";
		} catch (ServletException e) {
			adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Usuário ou senha incorretos.");
			return null;
		}
	}
	
	public String sair () throws ServletException{
		this.getRequest().logout();
		return "Login?faces-redirect=true";
	}
	
	public static void adicionarMensagem(Severity tipo, String msg){
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(tipo,msg,msg));
	}
	
	public boolean isAuthorized(String user, String pwd){
		return SecurityDAO.checkIfUserAdminExist(user, pwd);
	}
	
	
	private HttpServletRequest getRequest(){
		FacesContext context = FacesContext.getCurrentInstance();
		return (HttpServletRequest)context.getExternalContext().getRequest();
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
