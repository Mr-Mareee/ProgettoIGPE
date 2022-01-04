package Controllers;
import java.sql.SQLException;

import Application.*;
public class Variabili_Globali {
	public static User User_Online;
	public static User TeamLeader;
	public static boolean isDark=false;
	public static Project passaProgetto;
	public static Ticket passaTicket;
	public static User passaUser;
	public static User getPassaUser() {
		return passaUser;
	}
	public static void setPassaUser(User passaUser) {
		Variabili_Globali.passaUser = passaUser;
	}
	public static Project getPassaProgetto() {
		return passaProgetto;
	}
	public static Ticket getPassaTicket() {
		return passaTicket;
	}
	public static void setPassaProgetto(Project passaProgetto) {
		Variabili_Globali.passaProgetto = passaProgetto;
	}
	public static void setPassaTicket(Ticket passaTicket) {
		Variabili_Globali.passaTicket = passaTicket;
	}
	public static  boolean getisDark() throws SQLException {
		return Database.wantsDarkMode(User_Online);
	}
	public static void setIsDark(int A) throws SQLException {
		Database.setDarkMode(A);
	}
	public static User getUser_Online() {
		return User_Online;
	}
	public static User getTeamLeader() {
		return TeamLeader;
	}

	public static void setTeamLeader(User teamLeader) {
		TeamLeader = teamLeader;
	}

	public static void logIn(User user_Online) throws NullPointerException, SQLException{
		TeamLeader= Database.getTl();
		User_Online = user_Online;
	}
	public static void logOut() {
		User_Online = null;
	}
}
