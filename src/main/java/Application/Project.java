package Application;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
public class Project {
	private String Nome;
	private ArrayList<String> Users; 
	private User ProjectManager; 
	private ArrayList<String> ticketsAssociati;
	private String Descrizione;
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	//----------------------------------------
	public Project() throws SQLException{
		this.Nome=" ";
		this.Users=new ArrayList<>();
		this.ProjectManager=new User();
		this.ticketsAssociati=new ArrayList<String>();
	}
	@SuppressWarnings("unchecked")
	public Project(String N,ArrayList<String> U,User A,ArrayList<String> T){
		this.Nome=N;
		this.Users=(ArrayList<String>) U.clone();
		this.ProjectManager=A;
		this.ticketsAssociati=(ArrayList<String>) T.clone();
	}
	public User getPM() {
		return this.ProjectManager;
	}
	public void setPm(User user) {
		this.ProjectManager= user;
	}
	public void addUsers(String user) {Users.add(user);}
	public void setNome(String nome) {this.Nome=nome;}
	public String getNome() {return this.Nome;}
	public ArrayList<String> getUsers(){return this.Users;}
	public void setUsers(ArrayList<String> string) throws SQLException {
		Users.addAll(string);
	}
	
	public String toString() {
		String A=this.Nome+" "+this.Users+ " "+this.ProjectManager.getUsername();
		return A;
	}
	public Project clone(String N,ArrayList<String> TOI,ArrayList<String> U,User A){
			this.Nome=N;
			Collections.copy(this.Users, U);
			this.ProjectManager=A;
			return this;
	}
	public ArrayList<String> getTicketsAssociati() {
		return ticketsAssociati;
	}
	public void setTicketsAssociati(ArrayList<String> ticketsAssociati) {
		this.ticketsAssociati = ticketsAssociati;
	}
}

