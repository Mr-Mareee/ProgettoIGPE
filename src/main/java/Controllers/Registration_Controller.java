package Controllers;

import java.sql.SQLException;
import java.util.regex.Pattern;

import Application.Database;
import Application.SceneHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;



public class Registration_Controller{
	ObservableList<String> Ruoli =FXCollections.observableArrayList("Admin","User");
	@FXML
    private PasswordField textAreaPass2;
    @FXML
    private ChoiceBox<String> Role_Picker;
    @FXML
    private Label labelCognome;

    @FXML
    private PasswordField textAreaPass1;

    @FXML
    private TextField textAreaNome;

    @FXML
    private TextField textAreaCognome;

    @FXML
    private AnchorPane root;

    @FXML
    private Button buttonRegistrati;

    @FXML
    private Label labelNome;

    @FXML
    private TextField textAreaUsername;

    @FXML
    private Button backToHome;

    @FXML
    private TextField textAreaEmail;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPass1;

    @FXML
    private Label labelPass2;

    @FXML
    void BackHome(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setTLMainPageScene();
    }

    @FXML
    void registraUtente(ActionEvent event) throws Exception {
    	if (textAreaUsername.getText().isBlank() || textAreaPass1.getText().isBlank() || textAreaNome.getText().isBlank() || textAreaCognome.getText().isBlank() || textAreaEmail.getText().isBlank()) {
    		SceneHandler.getInstance().showError("Username non valido");
    		return;
    	}
    	if (Database.userExists(textAreaUsername.getText())) {
		SceneHandler.getInstance().showError("Username già in uso");
		return;
    	}
    	if (Pattern.matches( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_^&+=])(?=\\S+$).{8,}$", textAreaPass1.getText())) {
	    	if(!(textAreaPass1.getText().equals(textAreaPass2.getText()))) {
	    		SceneHandler.getInstance().showError("Le due password non coincidono");
	    	}
    	}
    	else {SceneHandler.getInstance().showError("la Password deve contenere almeno un numero,"
    			+ "una lettera maiuscola,una minuscola e un carattere speciale");
    		return;
    	}
    	if (!(Pattern.matches( "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
    			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\"
    			+ "x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\"
    			+ "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])"
    			+ "?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
    			+ "\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b"
    			+ "\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])" , textAreaEmail.getText()))) {
    		SceneHandler.getInstance().showError("Inserire una Email valida");
    		return;	
    	}
    	if (SendMail.Send_mail(textAreaEmail.getText(), textAreaNome.getText(), textAreaCognome.getText(), textAreaUsername.getText())) {
    	SceneHandler.getInstance().showInfo("Registrazione avvenuta con successo,le abbiamo inviato una mail"
    			+ " di benvenuto.");
		Database.insertUsers(textAreaUsername.getText(), textAreaPass1.getText(), textAreaNome.getText(), textAreaCognome.getText(), textAreaEmail.getText(),Role_Picker.getValue());
    	Database.StarterPropic(textAreaUsername.getText());
    	SceneHandler.getInstance().setRegistrationScene();
    	}
    	else {
    		SceneHandler.getInstance().showInfo("Registrazione avvenuta con successo,invio mail"
        			+ " non riuscito.");
    		Database.insertUsers(textAreaUsername.getText(), textAreaPass1.getText(), textAreaNome.getText(), textAreaCognome.getText(), textAreaEmail.getText(),Role_Picker.getValue());
    		Database.StarterPropic(textAreaUsername.getText());
    	}
    }
	public void initialize() throws SQLException {
		Role_Picker.setValue("User");
		Role_Picker.setItems(Ruoli);

		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
	
}
   }
