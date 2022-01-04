package Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneHandler {
    private Scene scene;
    private Stage stage;
	private static SceneHandler instance = null;	
	private SceneHandler() {}
	public void init(Stage primaryStage) throws Exception {		
		stage = primaryStage;
		initScene("Login.fxml");
		stage.getIcons().add(new Image("logompj.png"));
		stage.setScene(scene);
		stage.setWidth(320);
    	stage.setHeight(200);
		stage.setTitle("My Personal Jira");
		stage.setResizable(false);
		stage.show();
	}	
	public static SceneHandler getInstance() {
		if(instance == null)
			instance = new SceneHandler();
		return instance;
	}          
    public void setLoginScene() throws Exception {
    	stage.hide();
    	setCurrentScene("Login.fxml");
    	stage.setResizable(false);
    	stage.setWidth(320);
    	stage.setHeight(200);
    	stage.show();
    } 
    public void setRecuperaPassScene() throws Exception {
    	stage.hide();
    	setCurrentScene("recuperapass.fxml");
    	stage.setResizable(false);
    	stage.setWidth(320);
    	stage.setHeight(200);
    	stage.show();
    }  
    public void setRegistrationScene() throws Exception {
    	setCurrentScene("pagReg.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setChangePassScene() throws Exception{
    	setCurrentScene("ChangePassword.fxml");
    	stage.hide();
    	stage.setResizable(false);
    	stage.setWidth(400);
    	stage.setHeight(250);
    	stage.show();
    }
    public void setTLMainPageScene() throws Exception {
    	setCurrentScene("pagPrincipaleTeamLeader.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setReviewProgetto() throws Exception {
    	setCurrentScene("reviewProgetto.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setAddProjectScene() throws Exception{
    	setCurrentScene("pagAggiungiProgetto.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setShowAllProjectScene() throws Exception{
    	setCurrentScene("pagVisualizzaProgetti.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setAdminMainPage() throws Exception
    {
    	setCurrentScene("pagPrincipaleAdmin.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setShowAllUserPage() throws Exception {
    	setCurrentScene("PagVisualizzazioneUtenti.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    public void setDeveloperMainPage() throws Exception{
    	setCurrentScene("pagPrincipaleDeveloper.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    
    public void setAddNewTicketPage() throws Exception{
    	setCurrentScene("pagAggiungiTicket.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }

    public void setShowAllTicketsPage() throws Exception{
    	setCurrentScene("pagVisualizzaTickets.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
    }
    
    public void setReviewTicketScene() throws Exception {
    	setCurrentScene("reviewTicket.fxml");
    	stage.hide();
    	stage.setResizable(true);
    	stage.setWidth(900);
    	stage.setHeight(650);
    	stage.show();
		
	}
    
    
    private void setCurrentScene(String filename) throws Exception {    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/"+filename));
    	Parent root = (Parent) loader.load();
		scene.setRoot(root);
    }    
    private void initScene(String filename) throws Exception {    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/"+filename));
    	Parent root = (Parent) loader.load();
		scene = new Scene(root, 300, 200,Color.ORANGE);	
    }
    public void showError(String message) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("");
		alert.setContentText(message);
		alert.showAndWait();
    }
    public void showInfo(String message) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText("");
		alert.setContentText(message);
		alert.showAndWait();
    }
	
	
}
