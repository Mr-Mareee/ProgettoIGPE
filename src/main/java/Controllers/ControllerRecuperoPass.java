package Controllers;

import java.util.regex.Pattern;

import Application.Database;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class ControllerRecuperoPass {

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password2;

    @FXML
    private AnchorPane root;

    @FXML
    void Recupera_Pass(ActionEvent event) {
    	String user=Variabili_Globali.getPassaUser().getUsername();
    	Cambia_Pass(user);
    }
    void Cambia_Pass(String user) {
    	if (password.getText().equals(password2.getText())) {
        	if(Pattern.matches( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_^&+=])(?=\\S+$).{8,}$", password.getText())){
        		try {
        			System.out.println(user);
					Database.setPassword(password.getText(), user);
					SceneHandler.getInstance().showInfo("Cambio Password avvenuto con successo");
					SceneHandler.getInstance().setLoginScene();
				} catch (Exception e) {SceneHandler.getInstance().showError("Errore nel recupero Password");}
        	}
        	else {
        		SceneHandler.getInstance().showError("Password non valida, immettere"
        				+ " una lettera maiuscola,una minuscola,un numero e un carattere speciale");
        	}
        }else {
        	SceneHandler.getInstance().showError("Le password non coincidono");
        }
    }
}
