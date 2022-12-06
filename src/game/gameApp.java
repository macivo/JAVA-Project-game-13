package game;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import settings.InfoListenerModel;
import windows.WelcomeController;

/**
 * @author Bobby Lian, Mac Müller
 * @group 3
 * Main Program
 * @param args using for run the Application
 */

/**
 * create an Application stage
 * load a fxml-doc to show a Welcome User Interface
 */
public class gameApp extends Application{
	private Stage stage;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		InfoListenerModel info = new InfoListenerModel();
		try {
			FXMLLoader loader = new FXMLLoader(gameApp.class.getClassLoader().getResource("windows/WelcomeUI.fxml"));
			Parent root = (Parent) loader.load();
			(loader.<WelcomeController>getController()).init(info);
			
			scene = new Scene(root);
			scene.getStylesheets().add(gameApp.class.getClassLoader().getResource("windows/MainDesign.css").toExternalForm());

		    stage.setTitle("13! by Mac & Bobby");
		    stage.setScene(scene);
		    stage.show();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
