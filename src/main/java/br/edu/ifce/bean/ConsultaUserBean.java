package br.edu.ifce.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifce.dao.UserDAO;
import br.edu.ifce.model.User;

@ManagedBean
@ViewScoped
public class ConsultaUserBean {

	private List<User> users = new ArrayList<>();
	
	@PostConstruct
	public void inicializar(){
		users = UserDAO.getUsers();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
