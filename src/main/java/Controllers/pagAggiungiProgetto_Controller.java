package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Application.Database;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class pagAggiungiProgetto_Controller {

    @FXML
    private ListView<String> Lista_Visualizzione_User;

    @FXML
    private Label PMLabel;

    @FXML
    private Label usersLabel;

    @FXML
    private ChoiceBox<String> Lista_Users;

    @FXML
    private Button Bottone_Creazione_Progetto;

    @FXML
    private Button Bottone_Rimozione_User;

    @FXML
    private Button backToHomeButton;

    @FXML
    private Label projectNameLabel;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button Aggiungi_User;

    @FXML
    private TextField projectNameTextField;

    @FXML
    private AnchorPane root;

    @FXML
    private Label descriptionLabel;
    @FXML
    private ComboBox<String> Lista_Admin_2;

    @FXML
    void Crea_Progetto(ActionEvent event) throws Exception {
    	String Utenti="";
    	for (int i=0;i<Lista_Visualizzione_User.getItems().size();i++) {
    		Utenti+=Lista_Visualizzione_User.getItems().get(i);
    		if(i!=Lista_Visualizzione_User.getItems().size()-1) Utenti+=',';
    	}
    	if (!(projectNameTextField.getText().isBlank()) &&
    		!(descriptionTextField.getText().isBlank()) && 
    		!(Utenti.isBlank()) && 
    		!(Lista_Admin_2.getValue()==null))
    		{
    			Database.insertProject(projectNameTextField.getText(),
    			descriptionTextField.getText(), Utenti, Lista_Admin_2.getValue());
    			for (int i=0;i<Lista_Visualizzione_User.getItems().size();i++) {
    	    		Database.associateProject(Lista_Visualizzione_User.getItems().get(i), projectNameTextField.getText());
    			}
    			Database.associateProject(Lista_Admin_2.getValue(), projectNameTextField.getText());
    			SceneHandler.getInstance().setAddProjectScene();
    		}
    	else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
    		errorAlert.setHeaderText("i campi inseriti non sono validi");
    		errorAlert.setContentText("Prova a reinserirli");
    		errorAlert.showAndWait();
    	}
    }

    @FXML
    void Aggiungi_User(ActionEvent event) {
    	String value=Lista_Users.getValue();
    	if(value=="") return;
    	Lista_Visualizzione_User.getItems().add(value);
    	Lista_Users.getItems().remove(value);
    	Lista_Users.setValue("");
    }

    @FXML
    void Rimuovi_User(ActionEvent event) {
    	String value=Lista_Visualizzione_User.getSelectionModel().getSelectedItem();
    	if(value==null || value=="") return;
    	Lista_Visualizzione_User.getItems().remove(value);
    	Lista_Users.getItems().add(value);
    }
    @FXML
    void Back_Home(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setTLMainPageScene();
    }


	public void initialize() throws SQLException{
		projectNameTextField.clear();
		descriptionTextField.clear();
		Lista_Visualizzione_User.getItems().clear();
			if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
	    		root.getStylesheets().add("darkmode.css");
	    		Variabili_Globali.setIsDark(1);
				}
			else root.setStyle("-fx-background-color: f5f5dc");
			Lista_Admin_2.getItems().addAll(Database.getAvailableAdmins());
			Lista_Users.getItems().addAll(Database.getAvailableDevelopers());
	}

}