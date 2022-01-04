package Controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jfoenix.controls.JFXToggleButton;

import Application.Database;
import Application.DbConnection;
import Application.Project;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AdminMainPage_Controller{

    @FXML
    private MenuItem changePasswordButton;

    @FXML
    private MenuItem createTicketButton;

    @FXML
    private MenuItem PersonalInformationButton;

    @FXML
    private Circle propic;

    @FXML
    private AnchorPane root;

    @FXML
    private Hyperlink newTicketLink;

    @FXML
    private Button logoutButton;

    @FXML
    private MenuItem CloseButton;

    @FXML
    private Hyperlink bottoneSuFoto;

    @FXML
    private MenuItem showAllTicketsButton;

    @FXML
    private Hyperlink helpLink;

    @FXML
    private Hyperlink refreshPageLink;

    @FXML
    private Hyperlink showProjectLink;

    @FXML
    private JFXToggleButton button_setDark_Mode;
    
    @FXML
    void addNewTicket(ActionEvent event) throws Exception {
    	try
    	{
    		Database.GetProject(Variabili_Globali.getUser_Online().getProgetto());
    		SceneHandler.getInstance().setAddNewTicketPage();
    	}
    	catch(SQLException e)
    	{
    		SceneHandler.getInstance().showError("Impossibile creare ticket, non possiedi un progetto assegnato.");
    	}
    }

    @FXML
    void showAllTickets(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setShowAllTicketsPage();
    }

    @FXML
    void changePassword(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setChangePassScene();
    }

    @FXML
    void setDarkMode(ActionEvent event) throws SQLException 
    {
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
    	SceneHandler.getInstance().setAdminMainPage();
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setLoginScene();
		Variabili_Globali.logOut();
    }

    @FXML
    void helpAdmin(ActionEvent event) {
    	Alert errorAlert = new Alert(AlertType.INFORMATION);
		errorAlert.setHeaderText("Questa è la Pagina Principale dell' Admin di progetto");
		errorAlert.setContentText("Da qui puoi controllare come procede il progetto che ti è stato assegnato"
								 +" e creare nuovi ticket da legare al progetto");
		errorAlert.showAndWait();

    }

    @FXML
    void showProject(ActionEvent event) throws IOException 
    {
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
    void addNewTicketShort(ActionEvent event) throws Exception 
    {
    	try
    	{
    		Database.GetProject(Variabili_Globali.getUser_Online().getProgetto());
    		SceneHandler.getInstance().setAddNewTicketPage();
    	}
    	catch(SQLException e)
    	{
    		SceneHandler.getInstance().showError("Impossibile creare ticket, non possiedi un progetto assegnato.");
    	}
    }
    @FXML
    void closeProject(ActionEvent event) throws SQLException {
    	boolean canBeClosed=true;
    	Project A=Database.GetProject(Variabili_Globali.getUser_Online().getProgetto());
    	String s="";
    	for (String S : A.getTicketsAssociati()) {
    		if (Database.getStatoTicket(S)!=0) {
    			canBeClosed=false;
    			s+=S+" ";
    		}
    	}
    	if (canBeClosed) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Conferma Stato Ticket");
	    	alert.setHeaderText("Stai per richiedere la chiusura del Progetto");
	    	alert.setContentText("Sei sicuro?");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK)
	    	{
	    		Database.setStatoProgetto(A.getNome(), 2);
	    	} 
    	}
    	else {
    		SceneHandler.getInstance().showError("Il Progetto non può essere chiuso, i seguenti Ticket"
    				+ " sono ancora in corso o inviati a revisione: "+s);
    	}
    }
    @FXML
    void setPropic(ActionEvent event) 
    {
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

	public void initialize() throws SQLException, IOException {
		if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
			button_setDark_Mode.setSelected(true);
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
		Image img = null;
		refreshPageLink.setBorder(null);
			img = Database.getPropic(Variabili_Globali.getUser_Online().getUsername());
		
		if (img != null) {
		propic.setFill(new ImagePattern(img));
		bottoneSuFoto.setBorder(null);
		}
		}
		
	}
