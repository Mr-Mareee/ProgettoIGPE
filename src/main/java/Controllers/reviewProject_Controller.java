package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import Application.Database;
import Application.DbConnection;
import Application.Project;
import Application.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class reviewProject_Controller {

    @FXML
    private Text Linked_Tickets;

    @FXML
    private Text Pm;

    @FXML
    private Text Users;

    @FXML
    private Text nome;

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<String> LOGS;

    @FXML
    private Text workedTimeText;

    @FXML
    void backToHome(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().setShowAllProjectScene();
    }

    @FXML
    void close_project(ActionEvent event) throws Exception {
    	Database.setStatoProgetto(nome.getText(), 0);
    	SceneHandler.getInstance().showInfo("Il progetto è stato chiuso");
    	SceneHandler.getInstance().setShowAllProjectScene();
    }

    @FXML
    void reopen_Project(ActionEvent event) throws  Exception {
    	Database.setStatoProgetto(nome.getText(), 1);
    	SceneHandler.getInstance().showInfo("Il progetto è stato riaperto");
    	SceneHandler.getInstance().setShowAllProjectScene();
    }
    
    public void initialize() throws SQLException {
    	if (Database.wantsDarkMode(Variabili_Globali.getUser_Online())) {
    		root.getStylesheets().add("darkmode.css");
    		Variabili_Globali.setIsDark(1);
			}
		else root.setStyle("-fx-background-color: f5f5dc");
    	Project A=Variabili_Globali.getPassaProgetto();
    	nome.setText(A.getNome());
    	String B="";
    	for (int i=0;i<A.getUsers().size();i++) {
    		B+=A.getUsers().get(i);
    		if (i!=A.getUsers().size()-1) B+=",";
    	}
    	Users.setText(B);
    	Pm.setText(A.getPM().getUsername());
    	B="";
    	for (int i=0;i<A.getTicketsAssociati().size();i++) {
    		B+=A.getTicketsAssociati().get(i);
    		if (i!=A.getTicketsAssociati().size()-1) B+=",";
    	}
    	Linked_Tickets.setText(B);
    	for (int i=0;i<A.getTicketsAssociati().size();i++) {
    		ArrayList<String> b=Database.getLogs(A.getTicketsAssociati().get(i));
    		for(String s : b) {
    			LOGS.getItems().add(s);
    		}
    	}
    	Connection con=DbConnection.getInstance().getCon();
    	PreparedStatement ps=null;
    	String sql="Select loggedWork FROM progetto WHERE Nome=?";
    	ps=con.prepareStatement(sql);
    	ps.setString(1, A.getNome());
    	ResultSet rs=ps.executeQuery();
    	workedTimeText.setText(rs.getString(1));
    }
    
}