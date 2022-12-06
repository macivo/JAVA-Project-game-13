package windows;

import settings.BoardSync;
import settings.GameSetting;
import settings.InfoListenerModel;
import settings.OnePaneTiles;
import settings.Statistic;
import game.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import game.RandomNumber;

/**
* the central of game is here
*/
public class GameController implements PropertyChangeListener {
	
	// value to check the neighbors if a tile clicked
	private static int checkValue;
	// all tile will be stored in the ArrayList
    private static ArrayList<Tile> tileList;
    
    // to stored the column to shift down the tiles
    private ArrayList<Integer> colToShiffList = new ArrayList<>();
    private Parent gameOverMenu;
    
    // state of aiMode
    private static boolean aiMode;
    // aiStop if game over
    private boolean aiStop;

    // state of top level on playing board
    private static int gameTopLevel;
    // state of min level on playing board
    private static int gameMinLevel;
    // counting how many move on board
    private static int gameMove;
    // to set bomberState
    private boolean bomberState = false;

    GameSetting gameSetting = new GameSetting();
    BoardSync boardSync = new BoardSync();
    RandomNumber randNum = new RandomNumber();
    InfoListenerModel info;
    
    // all Labels and panes für design
	@FXML
	private Label labelLevel;
	@FXML
	private Label labelNextLevel;
	@FXML
	private Label labelStar;
	@FXML
	public Pane gameBoard;
	@FXML
	public StackPane gamePane;
	@FXML
	public StackPane bottomOption;
	@FXML
	public HBox hBoxGameOption;
	@FXML
	public VBox vBoxAIOption;
	@FXML
	private Button bomber;
	@FXML
	private Button back;
	
	/**
	* game start at here
	* min and top level start at 1, and set top level with tiles
	*/
	@FXML
	public void initialize() {
		tileList = new ArrayList<>();
		gameMinLevel = 1;
		gameTopLevel = 1;
		for (int i = 0; i < gameSetting.getCol(); i++) {
			for (int j = 0; j < gameSetting.getRol(); j++) {		
				int num = randNum.getRandomNumber(gameMinLevel, 6);
				if (num > gameTopLevel) gameTopLevel = num;
		        Tile c = new Tile(i, j, num, this);
		        tileList.add(c);		        
			}
		}	
		if ( WelcomeController.isAiMode() == true) {
			bottomOption.getChildren().remove(hBoxGameOption);
			aiMode = WelcomeController.isAiMode();
			
		}
		if ( WelcomeController.isAiMode() == false) {
			bottomOption.getChildren().remove(vBoxAIOption);
			aiMode = WelcomeController.isAiMode();
		}
		gameBoard.getChildren().addAll(tileList);	
		gameBoard.setMinWidth(gameSetting.getCol());
		gameBoard.setMinHeight(gameSetting.getRol());
		bomber.setDisable(true);
		back.setDisable(true);
		statusCheck();
	}
	
	@FXML
	protected void buttonBomber(ActionEvent event) {
		if (gameSetting.getStar() > 200) {
			this.bomberState = true;
			gameSetting.changeStar(gameSetting.getStar()-200);
			labelStar.setText(String.valueOf(gameSetting.getStar()));
		}
		else System.out.println("no more star!!");
	}
	
	@FXML
	protected void gameMenu(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
		try {
			gameOverMenu = loader.load();
			(loader.<GameMenuController>getController()).init(this);
			gamePane.getChildren().add(gameOverMenu);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	protected void buttonBack(ActionEvent event) {
		if (gameSetting.getStar() > 200) {
			gameSetting.changeStar(gameSetting.getStar()-200);
			labelStar.setText(String.valueOf(gameSetting.getStar()));
			gameTopLevel = 0;
			tileList = new ArrayList<>();
			gameBoard.getChildren().clear();
			OnePaneTiles pane = new OnePaneTiles();
			pane = boardSync.getBoard();
			for (BoardSync a: pane.getListToSync()) {
				Tile b = new Tile(a.getCol(),a.getRol(), a.getValue(), this);
				if (a.getValue() > gameTopLevel) gameTopLevel = a.getValue();
				tileList.add(b);
			}
			gameBoard.getChildren().addAll(tileList);
			this.statusCheck();
			back.setDisable(true);
		}
		else System.out.println("no more star!!");
	}
	/**
	* every click of a tile will be checking at hier
	*/
	public void boardAction(Tile tile) {
		OnePaneTiles onePane = new OnePaneTiles();
		for(Tile a: tileList) {
			BoardSync boardSync = new BoardSync(a.getCol(),a.getRol(), a.getValue(), tileList.indexOf(a));
			onePane.add(boardSync);
		}
		boardSync.sync(onePane);

		checkValue = tile.getValue();
		int tilePos = tileList.indexOf(tile);
		if (bomberState == true) {
			int tempValue = tile.getValue();
			checkValue = -1;
			Tile.setValue(tile, checkValue);
			colToShiffList.add(tileList.get(tilePos).getCol());		
			gameSetting.setStar(gameSetting.getStar()-200);
			aroundCheck(tilePos);
			statusCheck();
			bomberState = false;
			bomber.setDisable(true);
			if(tempValue == gameTopLevel) {
				gameTopLevel = 0;
				for(Tile a: tileList) {
					if (a.getValue() > gameTopLevel) gameTopLevel = a.getValue();
				}
			statusCheck();	
			}
			return;
		}
		
		if(	(tilePos-gameSetting.getCol() >= 0 &&
			checkValue == tileList.get(tilePos-gameSetting.getCol()).getValue())
			||
			(tilePos+gameSetting.getCol() <= tileList.size()-1 &&
			checkValue == tileList.get(tilePos+gameSetting.getCol()).getValue())
			||
			(tilePos % gameSetting.getCol() != 0 && tilePos-1 >= 0 &&
			checkValue == tileList.get(tilePos-1).getValue())
			||
			((tilePos+1) % gameSetting.getCol() != 0 && tilePos+1 <= tileList.size()-1 &&
			checkValue == tileList.get(tilePos+1).getValue())){


			Tile.setValue(tile, checkValue+1);
			if(tile.getValue() > gameTopLevel) gameTopLevel = tile.getValue();
			gameMove++;
			bomber.setDisable(false);
			back.setDisable(false);
			aroundCheck(tilePos);
			statusCheck();
		}	
	}
	
	/**
	* check the neighbors if they have a same value with checkValue
	* if they are same set the value -1
	*/
	void aroundCheck(int tilePos) {

		if(tilePos-gameSetting.getCol() >= 0 &&
		   checkValue == tileList.get(tilePos-gameSetting.getCol()).getValue()) {
			Tile.setValue(tileList.get(tilePos-gameSetting.getCol()), -1);
			colToShiffList.add(tileList.get(tilePos-gameSetting.getCol()).getCol());
			aroundCheck(tilePos-gameSetting.getCol());
		}
		
		if(tilePos+gameSetting.getCol() <= tileList.size()-1 &&
			checkValue == tileList.get(tilePos+gameSetting.getCol()).getValue()) {
			Tile.setValue(tileList.get(tilePos+gameSetting.getCol()), -1);
			colToShiffList.add(tileList.get(tilePos+gameSetting.getCol()).getCol());
			aroundCheck(tilePos+gameSetting.getCol());
		}
		if( tilePos-1 >= 0 &&
			tileList.get(tilePos).getCol() == tileList.get(tilePos-1).getCol()&& 
			checkValue == tileList.get(tilePos-1).getValue()) {
			Tile.setValue(tileList.get(tilePos-1), -1);
			colToShiffList.add(tileList.get(tilePos-1).getCol());
			aroundCheck(tilePos-1);
		}
		if(tilePos+1 <= tileList.size()-1 && 
			tileList.get(tilePos).getCol() == tileList.get(tilePos+1).getCol()&&
			checkValue == tileList.get(tilePos+1).getValue()) {
			Tile.setValue(tileList.get(tilePos+1), -1);
			colToShiffList.add(tileList.get(tilePos+1).getCol());
			aroundCheck(tilePos+1);
		}
		toShiff();
		colToShiffList.clear();
	}
	/**
	* shift all tiles with value -1 up
	* set a new random value to the tiles with -1 value
	*/
	private void toShiff() {
		for (int col: colToShiffList) {
			col *= gameSetting.getCol();
			int start = col+ (gameSetting.getCol()-1);
			int next = start-1;
			int end = col;
				while(start >= end) {
					if(tileList.get(start).getValue() <= 0) {
						break;	
					}					
					start--; next--;
				}	
				while(next >= end) {
					if(tileList.get(next).getValue() >= 0) break;
					next--;
				}			
				while (next >= end) {
					Tile.swapValue(tileList.get(start), tileList.get(next));
					start--; next--;
				}	
				Tile.setValue(tileList.get(end), (int) randNum.getRandomNumber(gameMinLevel, gameTopLevel));
		}
	}

	/**
	* any actions on the board have to call this function to check the board state
	*/
	void statusCheck() {
		Tile tile = new Tile();
		boolean gameOver = true;
		
		for (Tile t: tileList) {
			int tilePos = tileList.indexOf(t);
			int checkValue = t.getValue();
			int left, right, top, bottom, corTL, corTR, corBR, corBL;
			left = 2; right = 2;	top = 2; bottom = 2;
			corTL = 5; corTR = 5; corBR = 5; corBL = 5;
			boolean isTopLevel = false;
			
					if(	tilePos-gameSetting.getCol() >= 0 &&
					   checkValue == tileList.get(tilePos-gameSetting.getCol()).getValue()) {
						left = 0; corTL =0; corBL = 0; gameOver = false;
					}
					
					if(tilePos+gameSetting.getCol() <= tileList.size()-1 &&
						checkValue == tileList.get(tilePos+gameSetting.getCol()).getValue()) {
						right = 0; corTR =0; corBR = 0; gameOver = false;
					}
					if( tilePos % gameSetting.getCol() != 0 && tilePos-1 >= 0 &&
						checkValue == tileList.get(tilePos-1).getValue()) {
						top = 0; corTL =0; corTR = 0; gameOver = false;
					}
					if((tilePos+1) % gameSetting.getCol() != 0 && tilePos+1 <= tileList.size()-1 && 
						checkValue == tileList.get(tilePos+1).getValue()) {
						bottom = 0; corBL =0; corBR = 0; gameOver = false;
					}	
					if (top == 0 && right == 0) corTR = 3;
					if (top == 0 && left == 0) corTL = 3;
					if (bottom == 0 && right == 0) corBR = 3;
					if (bottom == 0 && left == 0) corBL = 3;
					if (checkValue == gameTopLevel)	isTopLevel = true;
					if (gameSetting.getBestLevel() < gameTopLevel) {
						labelLevel.setText(String.valueOf(gameTopLevel));
						gameSetting.changeBestLevel(gameTopLevel);
					}
					if (gameSetting.getToLevel() < gameTopLevel) {
						labelNextLevel.setText(String.valueOf(gameTopLevel+1));
						gameSetting.changeToLevel(gameTopLevel+1);
					}
										
			tile.setMerge(t, left, right, top, bottom, corTL, corTR, corBR, corBL , isTopLevel);
		}
		if (gameTopLevel-7 >= gameMinLevel) minLevelClear();
		if (gameOver == true) this.gameOver(); 
	}
	
	/**
	* if top level > 8 , remove all the tiles with minLevel
	*/
	private void minLevelClear() {
			for (Tile t: tileList) {
				if (t.getValue() == gameMinLevel) {
					Tile.setValue(t, -1);
					colToShiffList.add(t.getCol());
				}
			}
		gameMinLevel++;
		toShiff();
		colToShiffList.clear();
		statusCheck();
	}
	
	/**
	* game over handling
	*/
	void gameOver() {
		if ( aiMode == false) {
			gameSetting.changeStar(gameSetting.getStar()+gameMove);
			Statistic state = new Statistic();
			state.checkStatistic(gameTopLevel, gameMove);
			bomber.setVisible(false);
			back.setVisible(false);
		}
		FXMLLoader gameOver = new FXMLLoader(getClass().getResource("GameOver.fxml"));
		try {
			this.gamePane.getChildren().add(gameOver.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		aiStop = true;
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
	void gameContinue() {
		gamePane.getChildren().remove(gameOverMenu);
	}
	
	/**
	* AI mode
	*/
	@FXML
	protected void random() {
		
		while(aiStop != true) {
			Random random = new Random();
			this.boardAction(tileList.get(random.nextInt(gameSetting.getCol()*gameSetting.getRol()-1)));
		}
		System.out.println(gameTopLevel+": "+ gameMove+" moved");
	}
	@FXML
	protected void greedy() {
		int minValue = gameMinLevel;
		while(aiStop != true) {
			ArrayList<Integer> loopList = new ArrayList<>();
			for(Tile a: tileList) {
				if (a.getValue() == minValue) loopList.add(tileList.indexOf(a));
			}
			for(Integer a: loopList) {
				this.boardAction(tileList.get(a));
			}
			loopList.clear();
			minValue++;
			if (minValue > gameTopLevel) minValue = gameMinLevel;
		}
		System.out.println(gameTopLevel+": "+ gameMove+" moved");
	}

}
	



		
		
		
		


