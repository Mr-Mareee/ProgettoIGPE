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
import Application.Ticket;
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

public class pagVisualizzaTickets_Controller{

    @FXML
    private ListView<String> ListaVisualizzazioneTickets;

    @FXML
    private Button backToHomeButton;

    @FXML
    private AnchorPane root;

    @FXML
    private Button bottoneDettagliTicket;
    @FXML
    private Button bottoneCloseTicket;

    @FXML
    void Back_Home(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setAdminMainPage();
    }

    @FXML
    void visualizza_Dettagli_Ticket(ActionEvent event) throws SQLException, IOException 
    {
    	if(ListaVisualizzazioneTickets.getSelectionModel().getSelectedItem()==null)
    	{
    		SceneHandler.getInstance().showError("Devi selezionare un ticket valido per poter visualizzare i dettagli");
    	}
    	else
    	{
	    	Variabili_Globali.setPassaTicket(Database.getTicket(ListaVisualizzazioneTickets.getSelectionModel().getSelectedItem()));
	    	Stage stage = new Stage();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoTicket.fxml"));
	    	Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 600, 500,Color.ORANGE);	
			stage.getIcons().add(new Image("logompj.png"));
			stage.setScene(scene);
			stage.setWidth(600);
	    	stage.setHeight(500);
			stage.setTitle("My Personal Jira");
			stage.setResizable(false);
			stage.show();
    	}
    }
    @FXML
    void reviewTicket(ActionEvent event) throws Exception 
    {
    	if(ListaVisualizzazioneTickets.getSelectionModel().getSelectedItem()==null)
    	{
    		SceneHandler.getInstance().showError("Devi selezionare un ticket valido per poter poterlo chiudere");
    	}
    	else
    	{
    		if(Database.getStatoTicket(ListaVisualizzazioneTickets.getSelectionModel().getSelectedItem())==2)
    		{
    			Variabili_Globali.setPassaTicket(Database.getTicket(ListaVisualizzazioneTickets.getSelectionModel().getSelectedItem()));
    			SceneHandler.getInstance().setReviewTicketScene();
    		}
    		else 
    			SceneHandler.getInstance().showInfo("Il ticket non è in stato di revisione");
    	}
    }
    @FXML
    void Show_Opened_Tickets(ActionEvent event) throws SQLException {
    	Connection Con=DbConnection.getInstance().getCon();
    	String sql="Select * FROM Ticket Where Aperto=1";
    	PreparedStatement ps=Con.prepareStatement(sql);
    	ResultSet rs=ps.executeQuery();
    	ListaVisualizzazioneTickets.getItems().clear();
    	while (rs.next()) ListaVisualizzazioneTickets.getItems().add(rs.getString(1));
    }

    @FXML
    void Show_closed_Tickets(ActionEvent event) throws SQLException {
    	Connection Con=DbConnection.getInstance().getCon();
    	String sql="Select * FROM Ticket Where Aperto=0";
    	PreparedStatement ps=Con.prepareStatement(sql);
    	ResultSet rs=ps.executeQuery();
    	ListaVisualizzazioneTickets.getItems().clear();
    	while (rs.next()) ListaVisualizzazioneTickets.getItems().add(rs.getString(1));
    }
    @FXML
    void Show_to_Review(ActionEvent event) throws SQLException {
    	Connection Con=DbConnection.getInstance().getCon();
    	String sql="Select * FROM Ticket Where Aperto='2'";
    	PreparedStatement ps=Con.prepareStatement(sql);
    	ResultSet rs=ps.executeQuery();
    	ListaVisualizzazioneTickets.getItems().clear();
    	while (rs.next())
    	ListaVisualizzazioneTickets.getItems().add(rs.getString(1));
    }
	public void initialize() throws SQLException 
	{
		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
		ArrayList<Ticket> A=Database.getAllTickets();
		for (Ticket T : A)
		ListaVisualizzazioneTickets.getItems().add(T.getID());
	}

}
