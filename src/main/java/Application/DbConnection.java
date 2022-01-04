package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnection{
	private Connection connessione;
	private static DbConnection instance;
	private DbConnection() {
		connessione=connect();
	}
	public static DbConnection getInstance() {
		if(instance==null) instance=new DbConnection();
		return  instance;
	}
	public Connection getCon() {
		connessione=connect();
		return connessione;
	}
	private static Connection connect() {
		Connection con=null;
		try {
			Class.forName("org.sqlite.JDBC");
			con=DriverManager.getConnection("jdbc:sqlite:UsersMPJDB.db");
			if (con!= null && !con.isClosed()) {
				return con;
			}
			else {
				System.out.println("Connessione non riuscita");
				return con=null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connessione non andata a buon fine");
		}
		return con;
	}
	public static void closeCon(Connection con) throws SQLException {
		con.close();
		System.out.println("Connessione chiusa con Successo");
	}
	
}
