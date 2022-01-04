package Controllers;

import java.sql.SQLException;

import Application.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class infoTicket_Controller {

    @FXML
    private TextArea Descizione_Ticket;

    @FXML
    private Text associatedProject;

    @FXML
    private Text ticketStatus;

    @FXML
    private AnchorPane root;

    @FXML
    private Text NomeTicket;

    @FXML
    private Text loggedTime;

    @FXML
    private Text priority;

    @FXML
    private Text assignedTo;

    @FXML
    private Text Project_Manager;
    
    public void initialize() throws SQLException 
	{
    	if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
		NomeTicket.setText(Variabili_Globali.getPassaTicket().getID());
		Descizione_Ticket.setText(Variabili_Globali.getPassaTicket().getDescription());
		assignedTo.setText(Variabili_Globali.getPassaTicket().getUser());
		Project_Manager.setText(Variabili_Globali.getPassaTicket().getCreator());
		priority.setText(Variabili_Globali.getPassaTicket().getPriority());
		associatedProject.setText(Variabili_Globali.getPassaTicket().getProgettoAssociato());
		int status=Variabili_Globali.getPassaTicket().getStatus();
		if(status==0) ticketStatus.setText("Open");
		else if(status==1) ticketStatus.setText("Closed");
		else ticketStatus.setText("Under revision");
		loggedTime.setText(Variabili_Globali.getPassaTicket().getloggedWork());
	}

}
