package Application;

import java.sql.SQLException;

public class Ticket {
	//-----------------------------------------------
	//attributi
	private String ID;
	private String Creator;
	private String Priority;
	private String Description;
	private String ProgettoAssociato;
	private int Status;
	private String User;
	private String LoggedWork;
	//--------------------------------------------------------------------
	//metodi
	public String getID() {return ID;}
	public void setID(String iD) {ID = iD;}
	public String getCreator() {return Creator;}
	public void setCreator(String Creator) {this.Creator = Creator;}
	public String getPriority() {return Priority;}
	public void setPriority(String priority) {Priority = priority;}
	public int getStatus() {return Status;}
	public String getloggedWork() {return LoggedWork;}
	public String getUser() {return User;}
	public String getDescription() {return Description;}
	public void setDescription(String description) {Description = description;}
	public Ticket() throws SQLException {
		 ID="";
		 Creator="";
		 Priority="-";
		 Description="";
		 ProgettoAssociato="";
		 Status=0;
		 User="";
		 LoggedWork="";
	}
	public String getProgettoAssociato() {
		return ProgettoAssociato;
	}
	public void setProgettoAssociato(String project) throws SQLException {
		ProgettoAssociato = project;
	}
	public void setStatus(int status) {
		Status=status;
	}
	public void setUser(String user) {
		User=user;
	}
	public void setLoggedWork(String loggedWork) {
		LoggedWork=loggedWork;
	}
	
	

}
