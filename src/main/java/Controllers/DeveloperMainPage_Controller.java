package Controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jfoenix.controls.JFXToggleButton;

import Application.Database;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DeveloperMainPage_Controller {

    @FXML
    private MenuItem changePasswordButton;

    @FXML
    private MenuItem PersonalInformationButton;

    @FXML
    private Circle propic;

    @FXML
    private TextField nome_Ticket;

    @FXML
    private Button logoutButton;

    @FXML
    private MenuItem CloseButton;

    @FXML
    private DatePicker data_Lavoro;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField Ore_effettuate;

    @FXML
    private Hyperlink bottoneSuFoto;

    @FXML
    private Hyperlink refreshPageLink;

    @FXML
    private TextField Descrizione_Lavoro;

    @FXML
    private JFXToggleButton button_setDark_Mode;

    @FXML
    private TextField Ore_Mancanti;

    @FXML
    void changePassword(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setChangePassScene();
    }

    @FXML
    void setDarkMode(ActionEvent event) throws SQLException {
    	if (button_setDark_Mode.isSelected()) {
    		root.setStyle("");
    		button_setDark_Mode.setSelected(true);
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
    		Database.setDarkMode(1);

    	}
    	else {
    		root.getStylesheets().clear();
    		button_setDark_Mode.setSelected(false);
    		root.setStyle("-fx-background-color: f5f5dc");
    		Variabili_Globali.setIsDark(0);
    		Database.setDarkMode(0);
    	}
    }

    @FXML
    void showPersonalInformation(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoUtente.fxml"));
    	Parent root = (Parent) loader.load();
		Scene scene = new Scene(root, 400, 400,Color.ORANGE);	
		stage.getIcons().add(new Image("logompj.png"));
		stage.setScene(scene);
		stage.setWidth(400);
    	stage.setHeight(400);
		stage.setTitle("My Personal Jira");
		stage.setResizable(false);
		stage.show();
    }
    @FXML
    void refreshPage(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setDeveloperMainPage();
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setLoginScene();
		Variabili_Globali.logOut();
    }

    @FXML
    void Info_Ticket(ActionEvent event) throws IOException 
    {
    	try
    	{
    		Variabili_Globali.setPassaTicket(Database.getTicket(Database.getUserTicket(Variabili_Globali.getUser_Online().getProgetto(),
    																					Variabili_Globali.getUser_Online().getUsername())));
    		Stage stage = new Stage();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoTicket.fxml"));
	    	Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 600, 500,Color.ORANGE);	
			stage.getIcons().add(new Image("logompj.png"));
			stage.setScene(scene);
			stage.setWidth(600);
	    	stage.setHeight(500);
			stage.setTitle("My Personal Jira");
			stage.setResizable(false);
			stage.show();
    	}
    	catch(SQLException e)
    	{
    		SceneHandler.getInstance().showError("Non ti è stato assegnato ancora nessun ticket.");
    	}
    }


    @FXML
    void Chiudi_Ticket(ActionEvent event) throws SQLException {
    	String A=nome_Ticket.getText();
    	System.out.println(A);
    	if (A==null || A.isBlank()) 
    	{
    		SceneHandler.getInstance().showError("Impossibile richiedere chiusura ticket");
    	}
    	else
    	{
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Conferma Stato Ticket");
	    	alert.setHeaderText("Stai per richiedere la chiusura del ticket");
	    	alert.setContentText("Sei sicuro?");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK)
	    	{
	    		Database.setTicketstate(2,A);
	    	} 
    	}
    }


    @FXML
    void setPropic(ActionEvent event) {
    	try {
        	JFileChooser chooser=new JFileChooser();
        	FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());    	
        	chooser.setFileFilter(filter);
        	chooser.showOpenDialog(null);
        	File f=chooser.getSelectedFile();
        	if (f==null) return;
        	String filename= f.getAbsolutePath();
        	Image image=new Image("file:///"+filename);
        	propic.setFill(new ImagePattern(image));
        	if (Database.setPropic(filename, Variabili_Globali.getUser_Online().getUsername()));
        	else {
    			SceneHandler.getInstance().showError("Non siamo riusciti ad aggiungere una nuova Foto Profilo");
        	}
        	}
        	catch (Exception e){
    			SceneHandler.getInstance().showError("Non siamo riusciti ad aggiungere una nuova Foto Profilo");
        	}
    }

    @FXML
    void Info_Progetto(ActionEvent event) throws Exception {
    	try 
    	{
	    	Variabili_Globali.setPassaProgetto(Database.GetProject(Variabili_Globali.getUser_Online().getProgetto()));
	    	Stage stage = new Stage();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoProgetto.fxml"));
	    	Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 400, 400,Color.ORANGE);	
			stage.getIcons().add(new Image("logompj.png"));
			stage.setScene(scene);
			stage.setWidth(400);
	    	stage.setHeight(400);
			stage.setTitle("My Personal Jira");
			stage.setResizable(false);
			stage.show();
    	}
    	catch(SQLException e)
    	{
    		SceneHandler.getInstance().showError("Non ti è stato assegnato ancora nessun progetto.");
    	}
    }

    @FXML
    void Log_Work(ActionEvent event) throws SQLException 
    {
    	if (nome_Ticket.getText()==null || nome_Ticket.getText().isEmpty()) 
    	{
    		SceneHandler.getInstance().showError("Sembra che tu non abbia nessun ticket a cui Lavorare");
    		return;
    	} 
    	else if ( Ore_effettuate.getText()==null || !(Pattern.matches("^[0-2][0-3]:[0-5][0-9]$", Ore_effettuate.getText()))) 
    	{
    		SceneHandler.getInstance().showError("Inserisci un orario lavorativo seguendo il pattern hh:mm");
        	return;
    	}
    	else if(Descrizione_Lavoro.getText()==null || (Descrizione_Lavoro.getText().isBlank())) 
    	{
    		SceneHandler.getInstance().showError("Inserisci una descrizione per il log lavorativo");
            return;
    	}
    	else if(data_Lavoro.getValue()==null)
    	{
    		SceneHandler.getInstance().showError("Inserisci una data valida per il log lavorativo");
            return;
    	}
    	else
    	{
	    	Database.logWork(nome_Ticket.getText(), data_Lavoro.getValue().toString(), 
	    			Ore_effettuate.getText(), Descrizione_Lavoro.getText());
	    	
	    	data_Lavoro.setValue(null);
	    	Ore_effettuate.clear();
	    	Descrizione_Lavoro.clear();
	    	SceneHandler.getInstance().showInfo("Log lavorativo avvenuto con successo");
    	}
    }
	public void initialize() throws SQLException {
		nome_Ticket.setText(Database.getAssociatedTicket(Variabili_Globali.getUser_Online().getUsername()));
		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
			button_setDark_Mode.setSelected(true);
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
		Image img = null;
		refreshPageLink.setBorder(null);
		try {
			img = Database.getPropic(Variabili_Globali.getUser_Online().getUsername());
		} catch (Exception e) {
			SceneHandler.getInstance().showError("Non riusciamo a trovare la sua foto profilo nei nostri archivi");
		}
		if (img != null) {
		propic.setFill(new ImagePattern(img));
		bottoneSuFoto.setBorder(null);
		}
		
	}

}