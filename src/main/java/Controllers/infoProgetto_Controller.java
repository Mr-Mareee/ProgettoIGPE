package Controllers;

import java.sql.SQLException;

import Application.Database;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class infoProgetto_Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private Text NomeProgetto;

    @FXML
    private ListView<String> Lista_Tickets;

    @FXML
    private TextArea Descizione_Progetto;

    @FXML
    private ListView<String> Lista_Developers;

    @FXML
    private Text Project_Manager;


	public void initialize() throws Exception{
		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
		
		Project_Manager.setText(Variabili_Globali.getPassaProgetto().getPM().getUsername());
		Descizione_Progetto.setText(Variabili_Globali.getPassaProgetto().getDescrizione());
	    for (String A : Variabili_Globali.getPassaProgetto().getUsers()) {
	    	if (A!=null)
	    	Lista_Developers.getItems().add(A);
	    }

	    for (String A : Variabili_Globali.getPassaProgetto().getTicketsAssociati()) {
	    	if (A!=null)
	    	Lista_Tickets.getItems().add(A);
	    }
	    NomeProgetto.setText(Variabili_Globali.getPassaProgetto().getNome());
	}

}
