package Controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import Application.Database;
import Application.DbConnection;
import Application.SceneHandler;
import Application.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
public class ControllerLogin {
	
    @FXML
     Button Login_Button;
    @FXML
    private AnchorPane Loginroot;
    @FXML
    private ToggleButton Dark_mode;
    @FXML
    private TextField Username;
    @FXML
    private ImageView immagine_luce;
    @FXML
    private PasswordField Password;
    @FXML
    void Login_Attempt(ActionEvent event) throws Exception {
    	String Pass=Password.getText();
    	String User=Username.getText();
    	if (Pattern.matches( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_^&+=])(?=\\S+$).{8,}$", Pass)) {
    		boolean isok=Database.Checkuser(User, Pass);
    		if(!isok)  SceneHandler.getInstance().showError("username o password errati");
    		else
    		{
    			User A=Database.getUser(Username.getText());
    			if(A.getRuolo().equals("TL"))
    			{
		        	Variabili_Globali.logIn(A);
		    		SceneHandler.getInstance().setTLMainPageScene();
    			}
    			else if(A.getRuolo().equals("Admin"))
    			{
    				Variabili_Globali.logIn(A);
		    		SceneHandler.getInstance().setAdminMainPage();
    			}
    			else if(A.getRuolo().equals("User"))
    			{
    				Variabili_Globali.logIn(A);
		    		SceneHandler.getInstance().setDeveloperMainPage();
    			}
    		}
    			
    	}
    	else {SceneHandler.getInstance().showError("username o password errati");}
    }
    @FXML
    void Recupera_pass(ActionEvent event) throws Exception {
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Recupero password");
    	dialog.setHeaderText("");
    	dialog.setContentText("Inserire username:");
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		Random rnd = new Random();
          	 int number = rnd.nextInt(999999);
          	 String riconoscimento=String.format("%06d", number);
          	User A=Database.getUser(result.get());
       	 try {
       		 SendMail.Send_CodiceDistruzione(A.getEmail(), riconoscimento,result.get());
       	 } catch (Exception E) {
       		 SceneHandler.getInstance().showError("Nome errato o connessione assente"); return;
       	 } 
    		TextInputDialog dialog1 = new TextInputDialog("");
        	dialog1.setTitle("Recupero password");
        	dialog1.setContentText("Inserire codice inviatole tramite email:");
        	Optional<String> result1 = dialog1.showAndWait();
       	if (result1.isPresent()){
       			if (result1.get().equals(riconoscimento)) {
       				Variabili_Globali.setPassaUser(A);
       				SceneHandler.getInstance().setRecuperaPassScene();
       			} else SceneHandler.getInstance().showError("Codice Errato");
       	    }
    	}
    }

}

