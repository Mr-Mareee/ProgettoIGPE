package Controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jfoenix.controls.JFXToggleButton;

import Application.Database;
import Application.DbConnection;
import Application.SceneHandler;
import Application.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TLMainPage_Controller{

    @FXML
    private MenuItem showAllUsersButton;

    @FXML
    private MenuItem changePasswordButton;

    @FXML
    private PieChart progetti_aperti_chiusi;
    
    @FXML
    private Hyperlink newProjectLink;
    @FXML
    private BarChart<String, Integer> utente_produttivonew;
    
    @FXML
    private BarChart<String, Number> ticket_aperti_chiusi_progetto;
    
    @FXML
    private MenuItem PersonalInformationButton;

    @FXML
    private MenuItem removeUserButton;

    @FXML
    private Circle propic;

    @FXML
    private Hyperlink newUserLink;

    @FXML
    private Button logoutButton;

    @FXML
    private MenuItem CloseButton;

    @FXML
    private MenuItem addNewUserButton;

    @FXML
    private MenuItem DeleteAllButton;

    @FXML
    private MenuItem createProjectButton;

    @FXML
    private Hyperlink bottoneSuFoto;

    @FXML
    private Hyperlink helpLink;

    @FXML
    private AnchorPane root;

    @FXML
    private Hyperlink refreshPageLink;

    @FXML
    private JFXToggleButton button_setDark_Mode;

    @FXML
    private MenuItem showAllProjectsButton;
    @FXML
    private PieChart pie_admin_dev;
    @FXML
    void addNewProject(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setAddProjectScene();
    }

    @FXML
    void showAllProjects(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setShowAllProjectScene();
    }

    @FXML
    void showAllUsers(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setShowAllUserPage();
    }

    @FXML
    void addNewUser(ActionEvent event) throws Exception {
        	SceneHandler.getInstance().setRegistrationScene();
    }

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
    	SceneHandler.getInstance().setTLMainPageScene();
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setLoginScene();
		Variabili_Globali.logOut();
    }

    @FXML
    void helpTL(ActionEvent event) {
    	Alert errorAlert = new Alert(AlertType.INFORMATION);
		errorAlert.setHeaderText("Questa è la Pagina Principale del Team Leader");
		errorAlert.setContentText("Da qui puoi controllare come procedono tutti i progetti del tuo team");
		errorAlert.showAndWait();
    }


    @FXML
    void addNewUserShort(ActionEvent event) throws Exception {
    	addNewUser(event);
    }



    @FXML
    void addNewProjectShort(ActionEvent event) throws Exception {
    	addNewProject(event);
    }
    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize() throws SQLException{
			 // setto il TeamLeader Online
			if (Database.wantsDarkMode(Variabili_Globali.getTeamLeader())) {
				button_setDark_Mode.setSelected(true);
	    		root.getStylesheets().add("darkmode.css");
	    		Variabili_Globali.setIsDark(1);
				}
			else root.setStyle("-fx-background-color: f5f5dc");
		Image img = null;
		refreshPageLink.setBorder(null);
		try {
			img = Database.getPropic(Variabili_Globali.getTeamLeader().getUsername());
		} catch (Exception e) {
			SceneHandler.getInstance().showError("Non riusciamo a trovare la sua foto profilo nei nostri archivi");
		} 
		propic.setFill(new ImagePattern(img));
		bottoneSuFoto.setBorder(null);
		ObservableList<PieChart.Data> pieChartData = null;
			pieChartData = FXCollections.observableArrayList(
			        new PieChart.Data("Admins: ", Database.getNumbers("Admin")),
			        new PieChart.Data("Developers: ", Database.getNumbers("User")));
		 pieChartData.forEach(data ->
		 data.nameProperty().
		 bind(Bindings.concat(data.getName(), " ", data.pieValueProperty())));

        pie_admin_dev.getData().addAll(pieChartData);
        //----------------------------------------
        String sql = "SELECT DISTINCT Progetto_Associato FROM Ticket ;";
		Connection con=DbConnection.getInstance().getCon();
		PreparedStatement ps=null;
		ResultSet rs=null;
		ps=con.prepareStatement(sql);
		rs= ps.executeQuery();
		int conta=0;
		ArrayList<String> ListaProgetti=new ArrayList<>();
			while (rs.next()) {
				ListaProgetti.add(rs.getString(1));
				conta++;
			}
		
		rs.close();ps.close();
		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
        series2.setName("Ticket chiusi");   
        series1.setName("Ticket aperti");   
			for (int i=0;i<conta;i++) {   
		        series1.getData().add(new XYChart.Data(ListaProgetti.get(i), Database.getStatoTicket(1,ListaProgetti.get(i))));
		        series2.getData().add(new XYChart.Data(ListaProgetti.get(i), Database.getStatoTicket(0,ListaProgetti.get(i))));
			}
			 ticket_aperti_chiusi_progetto.getData().addAll(series1,series2);
			 XYChart.Series series3 = new XYChart.Series();
			 series3.setName("Ore Lavorative");
			 for (User i : Database.getAllUsers()) {   
			        series3.getData().add(new XYChart.Data(i.getUsername(),Database.sommaOreLavorative(i.getUsername())));	
			        }
			 utente_produttivonew.getData().addAll(series3);
			 
			pieChartData = null;
				pieChartData = FXCollections.observableArrayList(
				        new PieChart.Data("Progetti Aperti: ", Database.getStatoProgetti(1)),
				        new PieChart.Data("Progetti Chiusi: ", Database.getStatoProgetti(0)));
		        pieChartData.forEach(data ->
		                data.nameProperty().
		                bind(Bindings.concat( data.getName(), "", data.pieValueProperty())));
		        		progetti_aperti_chiusi.getData().addAll(pieChartData);
	}
}

