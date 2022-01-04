package Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import Application.Database;
import Application.Project;
import Application.SceneHandler;
import Application.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class reviewTickets_Controller {

    @FXML
    private ListView<String> LOGS;

    @FXML
    private Text PM;

    @FXML
    private Text associatedProjectText;

    @FXML
    private Text nome;

    @FXML
    private Text priorità;

    @FXML
    private AnchorPane root;

    @FXML
    private Text user;

    @FXML
    private Text workedTimeText;

    @FXML
    void backToHome(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setShowAllTicketsPage();
    }

    @FXML
    void closeTicket(ActionEvent event) throws Exception {
    	Database.setTicketstate(0, Variabili_Globali.getPassaTicket().getID());
    	SceneHandler.getInstance().showInfo("Il ticket è stato chiuso");
    	SceneHandler.getInstance().setShowAllTicketsPage();
    }

    @FXML
    void reopenTicket(ActionEvent event) throws Exception {
    	Database.setTicketstate(1, Variabili_Globali.getPassaTicket().getID());
    	SceneHandler.getInstance().showInfo("Il ticket è stato riaperto");
    	SceneHandler.getInstance().setShowAllTicketsPage();
    }
    
    public void initialize() throws SQLException {
    	if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
    	Ticket A=Variabili_Globali.getPassaTicket();
    	nome.setText(A.getID());
    	user.setText(A.getUser());
    	PM.setText(A.getCreator());
    	associatedProjectText.setText(A.getProgettoAssociato());
    	ArrayList<String> b=Database.getLogsforTickets(A.getID());
    	for(String s : b) LOGS.getItems().add(s);
    	workedTimeText.setText(Database.sommaOreLavorativeTicket(A.getID()));
    }
}