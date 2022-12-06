package windows;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import settings.GameSetting;
import settings.InfoListenerModel;

/**
 * Welcome Controller
 * 
 */
public class WelcomeController implements PropertyChangeListener {

	GameSetting gameSetting = new GameSetting();
	
	// get an observable support
	private InfoListenerModel info;
	// option to turn a computer robot mode.
    private static boolean aiMode = false;
    
    // Observer: the best level of user
	@FXML
	private Label labelLevel;
	// Observer:  a challenge level for user: standard value is 13
	@FXML
	private Label labelNextLevel;
	// Observer:  the stars allow user to use a special option in tha game
	@FXML
	private Label labelStar;
	
	
	@FXML
	private Label labelButtonPlay;
	@FXML
	private Label labelButtonLevel;
	@FXML
	private Label labelButtonAIMode;
	@FXML
	protected void buttonPlay(ActionEvent event) {
		aiMode = false;
		InfoListenerModel info = new InfoListenerModel();
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
	protected void buttonSetting(ActionEvent event) {
		Stage stage;
	    Parent root;
	    
		try {
		    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingUI.fxml"));
			root = (Parent) loader.load();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("MainDesign.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	protected void buttonStatistic(ActionEvent event) {
		Stage stage;
	    Parent root;
	    
		try {
		    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistic.fxml"));
			root = (Parent) loader.load();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("MainDesign.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void buttonAIMode(ActionEvent event) {
		aiMode = true;
		InfoListenerModel info = new InfoListenerModel();
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
	
	public void init(InfoListenerModel info) {
		    this.info = info;
		    this.info.addPropertyChangeListener(this);

			this.labelLevel.setText(String.valueOf(info.getLabelLevel()));
			this.labelNextLevel.setText(String.valueOf(info.getLabelNextLevel()));
			this.labelStar.setText(String.valueOf(info.getStar()));
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		 switch (evt.getPropertyName()) {
	      case InfoListenerModel.propLevel:
	        labelLevel.setText(evt.getNewValue().toString());
	        break;
	      case InfoListenerModel.propNextLevel:
	    	labelNextLevel.setText(evt.getNewValue().toString());
	        break;
	    }
		
	}

	public static boolean isAiMode() {
		return aiMode;
	}


}
