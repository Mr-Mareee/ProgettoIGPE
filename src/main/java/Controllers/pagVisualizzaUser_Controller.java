package Controllers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import Application.Database;
import Application.DbConnection;
import Application.SceneHandler;
import Application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class pagVisualizzaUser_Controller{
    @FXML
    private ListView<String> ListaVisualizzazioneUtenti;
    @FXML
    private Button backToHomeButton;
    @FXML
    private Button bottoneDettagliUtente;
    @FXML
    private AnchorPane root;
    @FXML
    private Button DownGrade_Utente;
    @FXML
    private Button Pulsante_SetTeamLeader;
    @FXML
    private Button UpgradeUtente;
    @FXML
    private Button Delete;
    @FXML
    void Back_Home(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setTLMainPageScene();
    }
    @FXML
    void DeleteUser(ActionEvent event) throws Exception {
    	if (!checkSelected()) return;
    	User A=Database.getUser(ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem());
    	System.out.println(A.getProgetto());
    	if (!(A.getProgetto().equals("-")) || A.getProgetto().isBlank()) {
    		SceneHandler.getInstance().showError("Ci dispiace, l'utente è associato ad un progetto"
    				+ " e non è possibile cancellarlo.");
    		return;
    	}
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Confermare eliminazione Utente");
    	dialog.setHeaderText("Si prega di confermare eliminazione Utente");
    	dialog.setContentText("Inserire Codice inviato tramite Email:");
    	Random rnd = new Random();
      	 int number = rnd.nextInt(999999);
      	 String riconoscimento=String.format("%06d", number);
   	    SendMail.Send_CodiceDistruzione(A.getEmail(), riconoscimento,A.getUsername());
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    if (result.get().equals(riconoscimento)){
    	    	Database.deletUser(ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem());
    	    	SceneHandler.getInstance().setShowAllUserPage();
    	    }
    	}	    
    }
    @FXML
    void Upgrade_Utente(ActionEvent event) throws SQLException {
    	if (!checkSelected()) return;
    	User A=Database.getUser(ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem());
    	if (A.getProgetto()!="-") {
    		SceneHandler.getInstance().showError("L'utente è associato ad un progetto"); return;
    	}
    	if (A.getRuolo().equals("User")) {
    		Database.changeRuolo("Admin",A.getUsername());
    	}
    }
    @FXML
    void visualizza_Dettagli_Utente(ActionEvent event) throws SQLException, IOException {
    	if (!checkSelected()) return;
    	Variabili_Globali.setPassaUser(Database.getUser(ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem()));
    	Stage stage = new Stage();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoUser.fxml"));
    	Parent root = (Parent) loader.load();
		Scene scene = new Scene(root, 400, 400,Color.ORANGE);	
		stage.getIcons().add(new Image("logompj.png"));
		stage.setScene(scene);
		stage.setWidth(400);
    	stage.setHeight(400);
		stage.setTitle("My Personal Jira");
		stage.setResizable(false);
		stage.show();
    }
    @FXML
    void Downgrade_Utente(ActionEvent event) throws SQLException {
    	if (!checkSelected()) return;
    	
    	User A=Database.getUser(ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem());
    	if (A.getProgetto()!="-") {
    		SceneHandler.getInstance().showError("L'utente è associato ad un progetto"); return;
    	}
    	if (A.getRuolo().equals("Admin")) {
    		Database.changeRuolo("User",A.getUsername());
    	}
    }
    @FXML
    void visualizza_utenti_liberi(ActionEvent event) throws SQLException {
    	String sql = "SELECT * FROM users WHERE Progetto='-';";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> utenti=new ArrayList<>();
		while(rs.next()) {
			utenti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneUtenti.getItems().clear();
		ListaVisualizzazioneUtenti.getItems().addAll(utenti);
    }
    @FXML
    void visualizza_utenti_occupati(ActionEvent event) throws SQLException {
    	String sql = "SELECT * FROM users WHERE Progetto<>'-';";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> utenti=new ArrayList<>();
		while(rs.next()) {
			utenti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneUtenti.getItems().clear();
		ListaVisualizzazioneUtenti.getItems().addAll(utenti);
    }
    @FXML
    void Show_Admin(ActionEvent event) throws SQLException {
    	String sql = "SELECT * FROM users WHERE Ruolo='Admin';";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> utenti=new ArrayList<>();
		while(rs.next()) {
			utenti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneUtenti.getItems().clear();
		ListaVisualizzazioneUtenti.getItems().addAll(utenti);
    }

    @FXML
    void Show_Developers(ActionEvent event) throws SQLException {
    	
    	String sql = "SELECT * FROM users WHERE Ruolo='User';";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> utenti=new ArrayList<>();
		while(rs.next()) {
			utenti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneUtenti.getItems().clear();
		ListaVisualizzazioneUtenti.getItems().addAll(utenti);
    }
    @FXML
    void SetUtenteTeamLeader(ActionEvent event) throws Exception {
    	if (!checkSelected()) return;
    	if (Database.getUser(ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem()).getProgetto()!="-") {
    		SceneHandler.getInstance().showError("L'utente è associato ad un progetto"); return;
    	}
    	
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Confermare Cambio Team Leader");
    	dialog.setHeaderText("Si prega di confermare cambio Team Leader");
    	dialog.setContentText("Inserire Password:");
    	Random rnd = new Random();
   	 int number = rnd.nextInt(999999);
   	 String riconoscimento=String.format("%06d", number);
   	 SendMail.Send_CodiceDistruzione(Variabili_Globali.getTeamLeader().getEmail(), riconoscimento,Variabili_Globali.getTeamLeader().getUsername());
   	Optional<String> result = dialog.showAndWait();
   	if (result.isPresent()){
   	    if (result.get().equals(riconoscimento)){
   	    	Database.setTl(Database.getUser(
   	    			ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem()));
   	    	SceneHandler.getInstance().setLoginScene();
   			Variabili_Globali.logOut();
   	    }
   	}	
    }
    private boolean checkSelected() {
    	if (ListaVisualizzazioneUtenti.getSelectionModel().getSelectedItem()==null) {
    		SceneHandler.getInstance().showError("Selezionare un Utente");
    		return false;
    	}
    	else return true;
    }
	public void initialize() throws SQLException {
		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
		ArrayList<User> utenti=Database.getAllUsers();
		for (int i=0;i<utenti.size();i++) ListaVisualizzazioneUtenti.getItems().add(utenti.get(i).getUsername());
	}
}
