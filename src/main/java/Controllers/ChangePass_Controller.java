package Controllers;
import java.sql.SQLException;
import java.util.regex.Pattern;

import Application.Database;
import Application.SceneHandler;
import Application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class ChangePass_Controller{

    @FXML
    private PasswordField Pass2;

    @FXML
    private PasswordField Pass1;
    @FXML
    private PasswordField PassVecchia;
    @FXML
    private AnchorPane root;
    @FXML
    private Button Back_To_Home;

    @FXML
    private Button Cambia_Pass;
    @FXML
    void Cambia_Pass(ActionEvent event) throws Exception {
    	User A=Variabili_Globali.getUser_Online();
    	if (Pattern.matches( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_^&+=])(?=\\S+$).{8,}$",Pass1.getText())) {
	    	if (Pass1.getText().equals(Pass2.getText())) {
	    		if (Database.Checkuser(Variabili_Globali.getUser_Online().getUsername(),PassVecchia.getText())) {
	    			Database.setPassword(Pass1.getText(),Variabili_Globali.getUser_Online().getUsername());
	    			SceneHandler.getInstance().showInfo("Cambio Password Avvenuto con successo");
	    			if(A.getRuolo().equals("TL"))
	    			SceneHandler.getInstance().setTLMainPageScene();
	    			else if (A.getRuolo().equals("Admin"))SceneHandler.getInstance().setAdminMainPage();
	    			else SceneHandler.getInstance().setDeveloperMainPage(); 			}
	    	}
    	}
    	else SceneHandler.getInstance().showError("La nuova password non è valida");
    }

    @FXML
    void Go_Home(ActionEvent event) throws Exception {
    	User A=Variabili_Globali.getUser_Online();
    	if(A.getRuolo().equals("TL"))
			SceneHandler.getInstance().setTLMainPageScene();
			else if (A.getRuolo().equals("Admin"))SceneHandler.getInstance().setAdminMainPage();
			else SceneHandler.getInstance().setDeveloperMainPage(); 
    }
	public void initialize() throws SQLException {
			if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
	    		root.getStylesheets().add("darkmode.css");
	    		Variabili_Globali.setIsDark(1);
				}
			else root.setStyle("-fx-background-color: f5f5dc");
		
		
	}

}