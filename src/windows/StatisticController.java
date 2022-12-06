package windows;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import settings.InfoListenerModel;
import settings.Statistic;
import settings.TileSetting;
import settings.Statistic.StatisticList;

public class StatisticController {
	InfoListenerModel info = new InfoListenerModel();

	Statistic state = new Statistic();
	@FXML
	Pane statistic;
	VBox vBox;
	HBox hBox;
	
	@FXML
	public void initialize() {
		vBox = new VBox();
		StatisticList stateListObj = new StatisticList();
		stateListObj = state.readStatistic();
		ArrayList<Statistic> stateListTemp = new ArrayList<>(); 
		stateListTemp.addAll(stateListObj.getStateList());
		for (Statistic s: stateListTemp) {
			hBox = new HBox();
			Label place = new Label();
			Label topLevel = new Label();
			Label date = new Label();
			Label move = new Label();
			place.setText("Place " + String.valueOf(stateListTemp.indexOf(s)+1)+".");
			place.setBackground(new Background(
	                new BackgroundFill (
	                		TileSetting.COLORS.get(1),
	                        new CornerRadii(1),
	                        new Insets(0,0,0,0))

	                )
	        );
			topLevel.setText("Level: " + String.valueOf(s.getTopLevel()));
			topLevel.setMinWidth(100);
			date.setText("Date: "+ s.getDate());
			date.setMinWidth(250);
			move.setText("move: " + String.valueOf(s.getMove()));
			move.setMinWidth(100);
			hBox.getChildren().addAll(topLevel, date, move);
			vBox.getChildren().add(place);
			vBox.getChildren().add(hBox);
		}
		statistic.getChildren().add(vBox);
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
