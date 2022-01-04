package Application;

import java.util.ArrayList;

public class User {
	private String Username;
	private String Nome;	
	private String Cognome;
	private String Email;
	private String Ruolo;	
	private String Progetto;
	public String getProgetto() {
			return Progetto;
		}
	public void setProgetto(String progetto) {
		Progetto = progetto;
	}
	public ArrayList<String> getInfos() {
			// 1.Username,2.Nome,3.Cognome,4.Email,5.Ruolo
			ArrayList<String> UserInfos=new ArrayList<>();
			UserInfos.add(this.Username);
			UserInfos.add(this.Nome);
			UserInfos.add(this.Cognome);
			UserInfos.add(this.Email);
			UserInfos.add(Ruolo);
			return UserInfos;
		}
	public User(String username, String nome, String cognome, String email, String ruolo) {
		super();
		Username = username;
		Nome = nome;
		Cognome = cognome;
		Email = email;
		Ruolo = ruolo;
	}
	public User(User A) {
		super();
		Username = A.Username;
		Nome = A.Nome;
		Cognome = A.Cognome;
		Email = A.Email;
		Ruolo = A.Ruolo;
	}
	public User() {
		super();
		Username = "";
		Nome = "";
		Cognome = "";
		Email = "";
		Ruolo = "";
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getRuolo() {
		return Ruolo;
	}
	public void setRuolo(String ruolo) {
		Ruolo=ruolo;
	}

}
