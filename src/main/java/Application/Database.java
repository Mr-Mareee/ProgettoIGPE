package Application;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.springframework.security.crypto.bcrypt.BCrypt;

import Controllers.SendMail;
import Controllers.Variabili_Globali;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
public class Database{
	


	
	public static void insertUsers(String Username,String Password,String Nome,String Cognome,String Email,String Ruolo) {
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String PasswordCrypted=CryptPass(Password);
			String sql="INSERT INTO users (Username,Password,Nome,Cognome,Email,Ruolo) VALUES(?,?,?,?,?,?) ";
			ps = con.prepareStatement(sql);
			Username.replace(" ", "");
			ps.setString(1, Username);
			ps.setString(2, PasswordCrypted);
			ps.setString(3, Nome);
			ps.setString(4, Cognome);
			ps.setString(5, Email);
			ps.setString(6, Ruolo);
			ps.execute();
			ps.close();
		}
		catch(SQLException e){System.out.println(e.toString());}
	}
	public static String CryptPass(String originalPassword) {
		 String Crypted= BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		 return Crypted;		
	}
	public static Boolean userExists(String user) throws SQLException {
		String sql = "SELECT * FROM Users WHERE Username=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ResultSet rs= ps.executeQuery();
		if(!rs.next()) {
			ps.close();
			return false;
		}
		ps.close();
		return true;
	}
	public static int getNumbers(String role) throws SQLException {
		String sql = "SELECT * FROM Users WHERE Ruolo=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, role);
		int size=0;
		ResultSet rs= ps.executeQuery();
		while(rs.next()) size++;
		return size;
	}
	public static User getUser(String nome) throws SQLException{
		String sql = "SELECT * FROM Users WHERE Username=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, nome);
		ResultSet rs= ps.executeQuery();
		User A=new User();
		A.setUsername(nome);
		A.setNome(rs.getString(3));
		A.setEmail(rs.getString(5));
		A.setCognome(rs.getString(4));
		A.setRuolo(rs.getString(6));
		try {
		A.setProgetto(rs.getString(9));
		} catch(Exception E) {A.setProgetto(null);}
		ps.close();
		return A;
	}
	public static ArrayList<User> getAllUsers() throws SQLException{
		String sql = "SELECT * FROM Users;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<User> Users=new ArrayList<User>();
		while(rs.next()) {
			User A=new User();
			A.setUsername(rs.getString(1));
			A.setNome(rs.getString(3));
			A.setEmail(rs.getString(5));
			A.setCognome(rs.getString(4));
			A.setRuolo(rs.getString(6));
			Users.add(A);
		}
		ps.close();
		return Users;
	}
	public static User getTl() throws SQLException{
		String sql = "SELECT * FROM Users WHERE Ruolo='TL';";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		return Database.getUser(rs.getString(1));
	}
	public static Boolean Checkuser(String user,String Pass)  {
		try {
			String sql = "SELECT * FROM users WHERE Username=? ;";
			Connection con=DbConnection.getInstance().getCon();
			PreparedStatement ps=null;
			ps=con.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				if ( BCrypt.checkpw(Pass,rs.getString("Password"))) {
					ps.close();
					return true;
				}
			}
			ps.close();
			return false;
		}
	catch(SQLException E) { return false;}
		}
	public static void insertProject(String Nome,String Descrizione,String Users,String PM) {
			Connection con=DbConnection.getInstance().getCon();
			PreparedStatement ps=null;
			try {
				String sql="INSERT INTO progetto (Nome,Descrizione,Users,ProjectManager) VALUES(?,?,?,?) ;";
				ps = con.prepareStatement(sql);
				Nome.replace(" ", "");
				ps.setString(1, Nome);
				ps.setString(2, Descrizione);
				ps.setString(3, Users);
				ps.setString(4, PM);
				ps.execute();
				ps.close();
				System.out.println("Progetto aggiunto!");
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	public static ArrayList<Project> GetProjects() throws SQLException {
			String sql = "SELECT * FROM progetto;";
			Connection con=DbConnection.getInstance().getCon();
			PreparedStatement ps=null;
			ps=con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			ArrayList<Project> Progetti=new ArrayList<Project>();
			while(rs.next()) {
				Project A=new Project();
				A.setNome(rs.getString(1));
				A.setUsers(Database.tokProject(rs.getString(3)));
				A.setPm(Database.getUser(rs.getString(4)));
				Progetti.add(A);
			}
			ps.close();
			return Progetti;
		}
	public static Project GetProject(String nomeProgetto) throws SQLException {
			String sql = "SELECT * FROM progetto WHERE Nome=? ;";
			Connection con=DbConnection.getInstance().getCon();
			PreparedStatement ps=null;
			ps=con.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			ResultSet rs= ps.executeQuery();
			Project A=new Project();
			A.setNome(rs.getString(1));
			A.setDescrizione(rs.getString(2));
			ArrayList<String> sviluppatori=new ArrayList<>();
			A.setUsers(Database.tokProject(rs.getString(3)));
			A.setPm(Database.getUser(rs.getString(4)));
			sviluppatori.clear();
			 sviluppatori=Database.tokProject(rs.getString(5));
			A.setTicketsAssociati(sviluppatori);
			ps.close();
			return A;
		}
	public void deleteProjects() {	
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String sql="DELETE FROM progetto ;";
			ps = con.prepareStatement(sql);
			ps.execute();
			ps.close();
			System.out.println("Tabella Cancellata");
		}
		catch(SQLException e){System.out.println(e.toString());}
	}
	public static void deleteAllUsers() {
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String sql="DELETE FROM users ;";
			ps = con.prepareStatement(sql);
			ps.execute();
			ps.close();
			System.out.println("Tabella Cancellata");
			Database.insertUsers("Car_Dod", "Carmine_Dodaro1", "Carmine", "Dodaro", "crugio23@gmail.com","TL");
		}
		catch(SQLException e){System.out.println(e.toString());}
	}
	public static void deletUser(String user) {
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String sql="DELETE FROM users WHERE Username=? ;";
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.execute();
			ps.close();
			System.out.println("Utente:"+user+" Cancellato");
		}
		catch(SQLException e){System.out.println(e.toString());}
	}
	public static void insertTicket(String ID,String Creatore,int Priorità,String Linked,String Progetto,String Descrizione,String User) {
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String sql="INSERT INTO Ticket (ID,Creatore,Priorità,Linked_Tickets,Descrizione,Progetto_Associato,User) VALUES(?,?,?,?,?,?,?) ";
			ps = con.prepareStatement(sql);
			ID.replace(" ", "");
			ps.setString(1, ID);
			ps.setString(2, Creatore);
			ps.setInt(3, Priorità);
			ps.setString(4, Linked);
			ps.setString(5, Descrizione);
			ps.setString(6, Progetto);
			ps.setString(7, User);
			ps.execute();
			ps.close();
			System.out.println("Ticket aggiunto!");
		}
		catch(SQLException e){System.out.println(e.toString());}
	}
	public static ArrayList<String> tokProject(String toTokenize) {
		ArrayList<String> Risultati=new ArrayList<String>();
		StringTokenizer stok = new StringTokenizer(toTokenize, ",");
		while(stok.hasMoreTokens()) {
			String token=stok.nextToken();
			Risultati.add(token);
		}
		return Risultati;
	}
	
	public static Ticket getTicket(String Ticket) throws SQLException{
		String sql = "SELECT * FROM Ticket WHERE ID=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, Ticket);
		ResultSet rs= ps.executeQuery();
		Ticket A=new Ticket();
		A.setID(Ticket);
		A.setCreator(rs.getString(2));
		A.setPriority(rs.getString(3));
		A.setDescription(rs.getString(4));
		A.setProgettoAssociato(rs.getString(5));
		A.setStatus(rs.getInt(6));
		A.setUser(rs.getString(7));
		A.setLoggedWork(rs.getString(8));
		ps.close();
		return A; 
	}
	public static int getStatoTicket(String ticket) throws SQLException{
		String sql = "SELECT Aperto FROM Ticket WHERE ID=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, ticket);
		ResultSet rs= ps.executeQuery();
		int A = rs.getInt(1);
		return A;
	}
	public static Integer getStatoTicket(int ap,String Progetto){
		try {
		String sql = "SELECT * FROM Ticket WHERE Aperto=? AND Progetto_Associato=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setInt(1, ap);
		ps.setString(2,Progetto);
		ResultSet rs= ps.executeQuery();
		Integer A=0;
		while (rs.next()) {
			A++;
		}
		return A;
		} catch (Exception E) { return 0;}
	}
	public static Integer getStatoProgetti(int ap){
		try {
		String sql = "SELECT * FROM progetto WHERE Aperto=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setInt(1, ap);
		ResultSet rs= ps.executeQuery();
		Integer A=0;
		while (rs.next()) {
			A++;
		}
		return A;
		} catch (Exception E) { return 0;}
	}
	public static ArrayList <Ticket> getAllTickets() throws SQLException{
		String sql = "SELECT * FROM Ticket ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList <Ticket> lista = new ArrayList<Ticket>();
		while(rs.next()) {
		Ticket A=new Ticket();
		A.setID(rs.getString(1));
		A.setCreator(rs.getString(2));
		A.setPriority(rs.getString(3));
		A.setDescription(rs.getString(4));
		A.setProgettoAssociato(rs.getString(5));
		A.setStatus(rs.getInt(6));
		A.setUser(rs.getString(7));
		A.setLoggedWork(rs.getString(8));
		lista.add(A);
		}
		ps.close();
		return lista;
	}
	public static ArrayList <String> getAllTickets(String admin) throws SQLException{
		String sql = "SELECT * FROM Ticket WHERE Creatore=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1,admin);
		ResultSet rs= ps.executeQuery();
		ArrayList <String> lista = new ArrayList<String>();
		while(rs.next()) 
		{
			lista.add(rs.getString(1));
		}
		ps.close();
		return lista;
	}
	public void deleteTickets() {	
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String sql="DELETE FROM Ticket ;";
			ps = con.prepareStatement(sql);
			ps.execute();
			ps.close();
			System.out.println("Tabella Ticket Cancellata");
		}
		catch(SQLException e){System.out.println(e.toString());}
	}
	public static ArrayList<String> getCurrentUserInfo() throws SQLException{
		String Username=Variabili_Globali.getUser_Online().getUsername();
		User A=Database.getUser(Username);
		return A.getInfos();
	}
	public static boolean wantsDarkMode(User user_Online) throws SQLException{
		String sql = "SELECT * FROM Users WHERE Username=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, user_Online.getUsername());
		ResultSet rs= ps.executeQuery();
		int dark=rs.getInt(8);
		ps.close();
		if (dark==1) return true;
		else return false;
	}
	public static void setDarkMode(int A) throws SQLException{
		String sql = "UPDATE users SET Dark_Mode=? WHERE Username=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		String user=Variabili_Globali.getUser_Online().getUsername();
		ps.setInt(1, A);
		ps.setString(2, user);
		ps.executeUpdate();
		ps.close();
	}
	public static void setPassword(String pass,String user) throws SQLException{
		String sql = "UPDATE users SET Password=? WHERE Username=? ;";
		String crypted=CryptPass(pass);
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, crypted);
		ps.setString(2, user);
		ps.executeUpdate();
		ps.close();
	}
	public static String getAssociatedTicket(String user) {
		try {
		String sql = "SELECT * FROM Ticket WHERE User=? AND Aperto=1 ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ResultSet rs=ps.executeQuery();
		return rs.getString(1);
	} catch(Exception e) {
		return null;
	}}

	public static void setTl(User NewTl) throws SQLException{
		String sql = "UPDATE users SET Ruolo='Admin' WHERE Ruolo='TL' ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.executeUpdate();
		ps.close();
		sql = "UPDATE users SET Ruolo='TL' WHERE Username=? ;";
		PreparedStatement ps1=null;
		ps1=con.prepareStatement(sql);
		ps1.setString(1, NewTl.getUsername());
		ps1.executeUpdate();
		ps1.close();
	}
	public static void logWork(String ticket,String Data,String ore,String Descrizione) throws SQLException
	{
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		String sql="INSERT INTO Log (Ticket,Data,Ore,Descrizione) VALUES(?,?,?,?) ";
		ps =con.prepareStatement(sql);
		ps.setString(1, ticket);
		ps.setString(2, Data);
		ps.setString(3, ore);
		ps.setString(4, Descrizione);
		ps.execute();
		ps.close();
		//Prelevo dal db le ore precedentemente loggate
		PreparedStatement ps2=null;
		String sql2="SELECT loggedWork FROM Ticket WHERE ID=?;";
		ps2 = con.prepareStatement(sql2);
		ps2.setString(1, ticket);
		ResultSet rs=ps2.executeQuery();
		String oreLog=rs.getString(1);
		ps2.close();
		String oreAggiornate=aggiornaOre(oreLog,ore);
		//Aggiorno le ore loggate nella tabella Ticket
		PreparedStatement ps3=null;
		String sql3="UPDATE Ticket set loggedWork=? WHERE ID=?;";
		ps3 = con.prepareStatement(sql3);
		ps3.setString(1, oreAggiornate);
		ps3.setString(2, ticket);
		ps3.executeUpdate();
		ps3.close();
		String sql4="SELECT loggedWork FROM progetto WHERE Nome=?;";
		PreparedStatement ps4=con.prepareStatement(sql4);
		rs=null;
		String progetto=Database.getTicket(ticket).getProgettoAssociato();
		ps4.setString(1,progetto);
		rs=ps4.executeQuery();
		oreLog="";
		oreLog=rs.getString(1);
		oreAggiornate="";
		oreAggiornate=aggiornaOre(oreLog,ore);
		String sql5="UPDATE progetto set loggedWork=? WHERE Nome=?;";
		PreparedStatement ps5=con.prepareStatement(sql5);
		ps5.setString(1, oreAggiornate);
		ps5.setString(2, progetto);
		ps5.executeUpdate();
		ps5.close();
	}
	private static String aggiornaOre(String oreLog,String ore) {

		//Ora aggiungo alle ore loggate presenti a db le ore del nuovo logWork 
		int oreT1,oreT2,minutiT1,minutiT2,minutiTotali=0;
		String t1="",t2="";
		for(int i=0;i<2;i++)
		{
			if(oreLog.charAt(i)==':') break;
			else 
			{
				t1+=oreLog.charAt(i);
				t2+=ore.charAt(i);
			}
		}
		oreT1=Integer.parseInt(t1);
		oreT2=Integer.parseInt(t2);
		minutiTotali=(oreT1*60);
		minutiTotali+=(oreT2*60);
		t1="";t2="";
		for(int i=3;i<5;i++)
		{
			t1+=oreLog.charAt(i);
			t2+=ore.charAt(i);
		}
		minutiT1=Integer.parseInt(t1);
		minutiT2=Integer.parseInt(t2);
		minutiTotali+=minutiT1;
		minutiTotali+=minutiT2;
		int oreTotali=0;t1="";t2="";
		while(minutiTotali>=60)
		{
			minutiTotali-=60;
			oreTotali++;
		}
		if(oreTotali<10)
		{
			t1=Integer.toString(oreTotali);
			t1="0"+t1;
		}
		else t1=Integer.toString(oreTotali);
		if(minutiTotali<10)
		{
			t2=Integer.toString(minutiTotali);
			t2="0"+t2;
		}
		else t2=Integer.toString(minutiTotali);
		 String oreAggiornate=t1+":"+t2;
		return oreAggiornate;
	}
	public static void setAdmin(User NewAd) throws SQLException{
		String sql = "UPDATE users SET Ruolo='Admin' WHERE Username=? ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, NewAd.getUsername());
		ps.executeUpdate();
		ps.close();
	}
	public static boolean setPropic(String path,String user) throws SQLException, IOException {
		try {
		File f1=new File(path);
        FileInputStream fin=new FileInputStream(f1);
        Connection con=DbConnection.getInstance().getCon();
        PreparedStatement pst = con.prepareStatement("UPDATE users SET ProPic=? WHERE Username=? ;");
        pst.setBinaryStream(1,fin,fin.available());
        pst.setString(2, user);
        pst.executeUpdate();	
        pst.close();
        return true;
	}catch(Exception e) { return false;}
	}
	public static Image getPropic(String name) throws SQLException, IOException {
		String sql = "SELECT ProPic FROM users WHERE  Username=? ;";
		PreparedStatement ps=null;
		Connection con=DbConnection.getInstance().getCon();
		ps=con.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs= ps.executeQuery();
		BufferedImage im = ImageIO.read(rs.getBinaryStream("propic"));
		Image immy=SwingFXUtils.toFXImage(im, null );
		return immy;
	}
	public static void StarterPropic(String user) throws SQLException, IOException {
		String sql = "SELECT * FROM Reference_Propic ;";
		PreparedStatement ps=null;
		Connection con=DbConnection.getInstance().getCon();
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		byte byteArray[]=rs.getBytes("ProPic");
		rs.close();
        PreparedStatement pst = con.prepareStatement("UPDATE users SET ProPic=? WHERE Username=? ;");
        pst.setBytes(1, byteArray);
        pst.setString(2, user);
       pst.executeUpdate();	
        pst.close();
	}
	public static ArrayList<String> getAvailableAdmins() throws SQLException{
		String sql = "SELECT * FROM users WHERE Ruolo=? and Progetto='-' ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, "Admin");
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Users=new ArrayList<String>();
		while(rs.next()) {
			String A;
			A=rs.getString(1);
			Users.add(A);
		}
		ps.close();
		return Users;
	}
	public static ArrayList<String> getAvailableDevelopers() throws SQLException{
		String sql = "SELECT * FROM users WHERE Ruolo=? and Progetto='-' ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, "User");
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Users=new ArrayList<String>();
		while(rs.next()) {
			String A;
			A=rs.getString(1);
			Users.add(A);
		}
		ps.close();
		return Users;
	}
	public static void associateProject(String User,String Progetto) throws SQLException {
		String sql = "update users set Progetto=? where Username=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, Progetto);
		ps.setString(2, User);
		ps.executeUpdate();
	}
	public static ArrayList<String> getAllProjects() throws SQLException 
	{
		String sql = "SELECT Nome FROM progetto;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> projects=new ArrayList<String>();
		while(rs.next()) {
			String A;
			A=rs.getString(1);
			projects.add(A);
		}
		ps.close();
		return projects;
	}
	public static void changeRuolo(String Ruolo,String Username) throws SQLException {
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
			String sql="UPDATE users SET Ruolo=? WHERE Username=? ; ";
			ps = con.prepareStatement(sql);
			ps.setString(1, Ruolo);
			ps.setString(2, Username);
			ps.execute();
			ps.close();
	}
	public static Integer sommaOreLavorative(String User) throws SQLException{
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		String sql="SELECT * FROM Log WHERE User=? ; ";
		ps=con.prepareStatement(sql);
		ps.setString(1, User);
		Integer minuti=0;
		Integer ore=0;
		ResultSet rs=ps.executeQuery();
		String s;
		String Ore;
		String Minuti;
		while(rs.next()) {
			Ore="";
			Minuti="";
			s=rs.getString(3);
			Ore+=s.charAt(0);
			Ore+=s.charAt(1);
			ore+=Integer.valueOf(Ore); 
			Minuti+=s.charAt(3);
			Minuti+=s.charAt(4);
			minuti+=Integer.valueOf(Minuti);
			if (minuti>=60) {
				minuti-=60;
				ore+=1;
			}
			
		}
		return ore;
	}
	public static String sommaOreLavorativeTicket(String Ticket) throws SQLException{
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		String sql="SELECT loggedWork FROM Ticket WHERE ID=? ; ";
		ps=con.prepareStatement(sql);
		ps.setString(1, Ticket);
		ResultSet rs=ps.executeQuery();
		return rs.getString(1);
	}
	public static ArrayList<String> getAssignedProjectDev(String nomeProgetto) throws SQLException{
		String sql = "SELECT * FROM users as u WHERE u.Ruolo=? and u.Progetto=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, "User");
		ps.setString(2, nomeProgetto);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Users=new ArrayList<String>();
		while(rs.next()) {
			String A;
			A=rs.getString(1);
			Users.add(A);
		}
		ps.close();
		return Users;
	}
	
	public static void insertTicket(String ID,String Creatore,String Priority,String Descrizione,String progettoAssociato,int aperto,String user) {
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		try {
			String sql="INSERT INTO Ticket (ID,Creatore,Priorità,Descrizione,Progetto_Associato,Aperto,User) VALUES(?,?,?,?,?,?,?) ;";
			ps = con.prepareStatement(sql);
			ID.replace(" ", "");
			ps.setString(1, ID);
			ps.setString(2, Creatore);
			ps.setString(3, Priority);
			ps.setString(4, Descrizione);
			ps.setString(5, progettoAssociato);
			ps.setInt(6, aperto);
			ps.setString(7, user);
			ps.execute();
			ps.close();
			System.out.println("Ticket aggiunto!");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean checkAssignedTk(String user) throws SQLException 
	{
		String sql = "SELECT ID FROM Ticket WHERE User=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Users=new ArrayList<String>();
		while(rs.next()) {
			String A;
			A=rs.getString(1);
			Users.add(A);
		}
		ps.close();
		if(Users.size()==0)
			return false;
		else return true;
	}
	public static void updateProjectLinkedTickets(String progetto, String text) throws SQLException 
	{
		String sql1="SELECT LinkedTickets FROM progetto WHERE Nome=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql1);
		ps.setString(1, progetto);
		ResultSet rs= ps.executeQuery();
		String A=rs.getString(1);
		if(A.equals("-")) 
		{
			A=""; //inizializzo per il tk nuovo se prima non erano presenti tk
			A=text;
			
		}
		else 
		{
			A+=",";
			A+=text; //prima metto il carattere di separazione e poi il nuovo tk
		}
		rs.close();
		ps.close();
		String sql2="UPDATE progetto SET LinkedTickets=? WHERE Nome=?;";
		PreparedStatement ps2=null;
		ps2=con.prepareStatement(sql2);
		ps2.setString(1, A);
		ps2.setString(2, progetto);
		ps2.executeUpdate();
		ps2.close();
		
		
	}
	public static String getUserTicket(String progetto, String username) throws SQLException 
	{
		String sql1="SELECT ID FROM Ticket WHERE Progetto_Associato=? and User=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql1);
		ps.setString(1, progetto);
		ps.setString(2, username);
		ResultSet rs= ps.executeQuery();
		return rs.getString(1);
	}
	public static void setTicketstate(int stato,String ticket) throws SQLException {
		String sql1="UPDATE Ticket SET Aperto=? WHERE ID=?;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql1);
		ps.setInt(1, stato);
		ps.setString(2, ticket);
		ps.executeUpdate();
		if (stato==2) {SendMail.Send_MessaggioGenerico(Database.getUser(Database.getTicket(ticket).getCreator()).getEmail(),
				"Richiesta revisione ticket da parte di: "+Variabili_Globali.getUser_Online().getUsername(),
				"il Ticket: "+ticket+" è in stato di revisione");}
		else if (stato==0) {
			sql1="UPDATE Ticket SET User='-' WHERE ID=?;";
			Connection con1=DbConnection.getInstance().getCon();
			PreparedStatement ps1=null;
			ps1=con1.prepareStatement(sql1);
			ps1.setString(1, ticket);
			ps1.executeUpdate();
		}
		
	}
	public static void setStatoProgetto(String Progetto,int state) throws SQLException
	{
			String sql1="UPDATE progetto SET Aperto=? WHERE Nome=?;";
			Connection con=DbConnection.getInstance().getCon();
			PreparedStatement ps=null;
			ps=con.prepareStatement(sql1);
			ps.setInt(1, state);
			ps.setString(2, Progetto);
			ps.executeUpdate();
			if (state==2) {SendMail.Send_MessaggioGenerico(Variabili_Globali.getTeamLeader().getEmail(), "Richiesta revisione Progetto", "il Progetto"+Progetto+
					"è stato inviato a revisione da: "+Variabili_Globali.getUser_Online().getUsername());}
			if (state==0) {
				 sql1="Select * from progetto Where Nome=?;";
				PreparedStatement ps1=null;
				ps1=con.prepareStatement(sql1);
				ps1.setString(1, Progetto);
				ResultSet rs1=ps1.executeQuery();
				Project A=Database.GetProject(rs1.getString(1));
				ps1.close();
				Database.associateProject(A.getPM().getUsername(), "-");
				for (String s : A.getUsers()) Database.associateProject(s, "-");
			}
		}
	public static ArrayList<String> getLogsforTickets(String Ticket) throws SQLException{
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		String sql="SELECT * FROM Log WHERE Ticket=? ; ";
		ps=con.prepareStatement(sql);
		ps.setString(1, Ticket);
		ResultSet rs=ps.executeQuery();
		ArrayList<String> A=new ArrayList<>();
		while(rs.next()) {
			String s="";
			s+="Data:"+rs.getString(2)+", "
					+ "Descrizione:"+rs.getString(4);
		A.add(s);
		}
		return A;
	}
	public static ArrayList<String> getLogs(String Ticket) throws SQLException {
			Connection con=DbConnection.getInstance().getCon();
			PreparedStatement ps=null;
			String sql="SELECT * FROM Log WHERE Ticket=? ; ";
			ps=con.prepareStatement(sql);
			ps.setString(1, Ticket);
			ResultSet rs=ps.executeQuery();
			ArrayList<String> A=new ArrayList<>();
			while(rs.next()) {
				String s="";
				s+="Ticket:"+rs.getString(1)+", Data:"+rs.getString(2)+", "
						+ "Ore Loggate:"+rs.getString(3)+", Descrizione:"+rs.getString(4)+", User:"+rs.getString(5);
			A.add(s);
			}
			return A;
			}
}
