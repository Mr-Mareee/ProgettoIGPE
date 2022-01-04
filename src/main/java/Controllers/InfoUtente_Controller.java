package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Application.Database;
import Application.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class InfoUtente_Controller{

    @FXML
    private AnchorPane root;

    @FXML
    private Text Email;

    @FXML
    private Text Username;

    @FXML
    private Text Ruolo;

    @FXML
    private Text Nome;

    @FXML
    private Text Progetto;

    @FXML
    private Text Cognome;
    
	public void initialize() throws SQLException {
			if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
	    		root.getStylesheets().add("darkmode.css");
	    		Variabili_Globali.setIsDark(1);
				}
			else root.setStyle("-fx-background-color: f5f5dc");
		User A=Variabili_Globali.getUser_Online();
		Username.setText(A.getUsername());
		Nome.setText(A.getNome());
		Cognome.setText(A.getCognome());
		Email.setText(A.getEmail());
		try{
			Progetto.setText(A.getProgetto());
		}catch(Exception E) { Progetto.setText("-");}
		Ruolo.setText(A.getRuolo());
	}

}