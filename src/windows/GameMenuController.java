package windows;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import settings.InfoListenerModel;

public class GameMenuController {
	private GameController game;
	
	public void init(GameController game) {
		this.game = game;
	}

	public static FXMLLoader play;
	InfoListenerModel info = new InfoListenerModel();
	
	@FXML
	protected void playcontinue(ActionEvent event){
		game.gameContinue();;
	}
	
	@FXML
	protected void restart(ActionEvent event) {
		Stage stage;
	    Parent root;
		try {
		    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameUI.fxml"));
			root = (Parent) loader.load();
			(loader.<GameController>getController()).init(info);
			
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("MainDesign.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	@FXML
	protected void mainMenu(ActionEvent event){
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
