package windows;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import settings.GameSetting;
import settings.InfoListenerModel;

public class SettingController {
	
	InfoListenerModel info = new InfoListenerModel();
	GameSetting gameSetting = new GameSetting();
	@FXML
	private Button five;
	@FXML
	private Button ten;
	
	@FXML
	public void initialize() {
		if (gameSetting.getCol() == 5) five.setDisable(true);
		if (gameSetting.getCol() == 10) ten.setDisable(true);
	}

	@FXML
	protected void buttonSave(ActionEvent event) {}
	
	@FXML
	protected void buttonFive(ActionEvent event) {
		gameSetting.changeDimension(5);
		five.setDisable(true);
		ten.setDisable(false);
	}
	@FXML
	protected void buttonTen(ActionEvent event)  {
		gameSetting.changeDimension(10);
		five.setDisable(false);
		ten.setDisable(true);
	}
	
	
	@FXML
	protected void buttonMain(ActionEvent event) {
	Stage stage;
    Parent root;
    
	try {
	    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeUI.fxml"));
		root = (Parent) loader.load();
		(loader.<WelcomeController>getController()).init(info);
		
	    Scene scene = new Scene(root);
	    scene.getStylesheets().add(getClass().getResource("MainDesign.css").toExternalForm());
	    stage.setScene(scene);
	    stage.show();
	} catch (IOException e) {
		e.printStackTrace();
	}

	}
}
