package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Application.Database;
import Application.DbConnection;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class pagVisualizzaProgetti_Controller {
	 @FXML
	 private AnchorPane root;

    @FXML
    private Button backToHomeButton;

    @FXML
    private ListView<String> ListaVisualizzazioneProgetti;

    @FXML
    private Button bottoneDettagliProgetto;
    
    @FXML
    void Back_Home(ActionEvent event) throws Exception 
    {
    	SceneHandler.getInstance().setTLMainPageScene();
    }
    @FXML
    void visualizza_Dettagli_Progetto(ActionEvent event) throws SQLException, IOException {
    	if (!checkSelected()) return;
	    	Variabili_Globali.setPassaProgetto(Database.GetProject(ListaVisualizzazioneProgetti.getSelectionModel().getSelectedItem()));
	    	Stage stage = new Stage();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoProgetto.fxml"));
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
    void visualizza_Progetti_Aperti(ActionEvent event) throws SQLException {
    	String sql = "SELECT * FROM progetto WHERE Aperto=1;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Progetti=new ArrayList<>();
		while(rs.next()) {
			Progetti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneProgetti.getItems().clear();
		ListaVisualizzazioneProgetti.getItems().addAll(Progetti);
    }

    @FXML
    void visualizza_Progetti_Chiusi(ActionEvent event) throws SQLException {
    	String sql = "SELECT * FROM progetto WHERE Aperto=0;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Progetti=new ArrayList<>();
		while(rs.next()) {
			Progetti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneProgetti.getItems().clear();
		ListaVisualizzazioneProgetti.getItems().addAll(Progetti);
    }

    @FXML
    void visualizza_Progetti_da_confermare(ActionEvent event) throws SQLException {
    	String sql = "SELECT * FROM progetto WHERE Aperto=2;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ps=con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		ArrayList<String> Progetti=new ArrayList<>();
		while(rs.next()) {
			Progetti.add(rs.getString(1));
		}
		ps.close();
		rs.close();
		ListaVisualizzazioneProgetti.getItems().clear();
		ListaVisualizzazioneProgetti.getItems().addAll(Progetti);
    }
    @FXML
    void controlla_progetto(ActionEvent event) throws Exception {
    	checkSelected();
    	Variabili_Globali.setPassaProgetto(Database.GetProject(ListaVisualizzazioneProgetti.getSelectionModel().getSelectedItem()));
    	SceneHandler.getInstance().setReviewProgetto();
    }
    private boolean checkSelected() {
    	if (ListaVisualizzazioneProgetti.getSelectionModel().getSelectedItem()==null) {
    		SceneHandler.getInstance().showError("Selezionare un Progetto");
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
	ListaVisualizzazioneProgetti.getItems().addAll(Database.getAllProjects());
	}

}

