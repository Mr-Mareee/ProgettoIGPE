package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Application.Database;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class pagAggiungiTicket_Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private Button addTicketButton;

    @FXML
    private ChoiceBox<String> priorityBox;

    @FXML
    private Button backToHomeButton;

    @FXML
    private TextField tkNameArea;

    @FXML
    private TextArea tkDescriptionArea;
    @FXML
    private ChoiceBox<String> userBox;
    

    @FXML
    void backToHome(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setAdminMainPage();
    }

    @FXML
    void addTIcket(ActionEvent event) throws Exception 
    {
    	if (tkDescriptionArea.getText().isBlank() ||
        		tkNameArea.getText().isBlank() ||
        		userBox.getValue()==null || 
        		priorityBox.getValue()==null)
        		{	
    				SceneHandler.getInstance().showError("I campi inseriti non sono validi, prova a reinserirli");
        		}
    	else if(userBox.getValue().equals("-"))
    	{
    		SceneHandler.getInstance().showError("Non sono disponibili altri developers a cui assegnare tickets");
    	}
        else 
        {
        	Database.insertTicket(tkNameArea.getText(),
					 Variabili_Globali.getUser_Online().getUsername(),
					 priorityBox.getValue(),tkDescriptionArea.getText(),
					 Variabili_Globali.getUser_Online().getProgetto(),
					 1,userBox.getValue());
        	Database.updateProjectLinkedTickets(Variabili_Globali.getUser_Online().getProgetto(),tkNameArea.getText());
			 
			SceneHandler.getInstance().setAddNewTicketPage();
        }
    }
    private boolean hasTkAssigned(String user) throws SQLException 
    {		
    	/*questa funzione verifica quali tra gli utenti
    	assegnati al progetto non hanno tk già assegnati*/
    	if(Database.checkAssignedTk(user))
    		return true;
    	else return false;
    }
	public void initialize() throws SQLException{
    	tkNameArea.clear();
    	tkDescriptionArea.clear();
    	priorityBox.getItems().add("Low");
    	priorityBox.getItems().add("Medium");
    	priorityBox.getItems().add("High");
    		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
	    		root.getStylesheets().add("darkmode.css");
	    		Variabili_Globali.setIsDark(1);
				}
			else root.setStyle("-fx-background-color: f5f5dc");
    		/*vado a visualizzare nel choicebox solo gli utenti che non hanno tk già assegnati*/
    		ArrayList<String> A=Database.getAssignedProjectDev(Variabili_Globali.getUser_Online().getProgetto());
    		ArrayList<String> B=new ArrayList<String>();
    		for(int i=0;i<A.size();i++)
    		{
    			if(!hasTkAssigned(A.get(i)))
    				B.add(A.get(i));
    		}
    		if(B.size()==0)
    			userBox.getItems().addAll("-");
    		else
    			userBox.getItems().addAll(B);
	}

}
