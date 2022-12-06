package game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import settings.TileSetting;
import windows.GameController;;


/**
 * a Tile as object
 * with values of column, roll, value
 * setOnMouseClicked should do an event at GameController class
 */

public class Tile extends Label {
	
    private int col;
    private int rol;
    private int value;
    private GameController game;
    
    public int getCol() {
        return col;
    }

    public int getRol() {
        return rol;
    }

    public int getValue() {
        return value;
    }
    
    /**
     * set a Value for a tile
     * @param tile as object
     * @param value for a tile
     */
    public static void setValue(Tile tile, int value) {
        tile.setText(Integer.toString(value));
        tile.value = value;
        if (value > 9) value = value%9;
        tile.setBackground(new Background(
                new BackgroundFill (
                		TileSetting.COLORS.get(value),
                        new CornerRadii(5),
                        new Insets(2,2,2,2))

                )
        );
        
    }
    
    /**
    * swap the value of two tiles
    */
    public static void swapValue(Tile tileA, Tile tileB) {
    	int valueTemp = tileA.value;
    	tileA.value = tileB.value;
    	tileB.value = valueTemp;
    	setValue(tileA, tileA.value);
    	setValue(tileB, tileB.value);

    }
    
    /**
    * design the tile
    */
    public void setMerge(Tile tile, int l, int r, int t, int b, int corTL, int corTR, int corBR, int corBL, boolean isTopLevel) {
    	int topColor = tile.value;
    	if (isTopLevel == true) topColor = 0;
    	if (topColor > 9) topColor = topColor%9;
    	tile.setBackground(new Background(new BackgroundFill (
              			TileSetting.COLORS.get(topColor),
                        new CornerRadii(corTL, corTR, corBR, corBL, false),
                        new Insets(t,r,b,l))));
    }

    /**
    * constructor to create a tile
    */
    public Tile(int x, int y, int value, GameController game) {
        this.col = x;
        this.rol = y;
        this.value = value;
        if (this.value > 9) this.value = value%9;
        this.game = game;
        this.setText(Integer.toString(value));
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked(event -> {
        	game.boardAction(this);
        });

        this.setLayoutX(x* TileSetting.WIDTH);
        this.setLayoutY(y * TileSetting.HEIGHT);
        this.setPrefHeight(TileSetting.HEIGHT);
        this.setPrefWidth(TileSetting.WIDTH);
        this.setStyle("-fx-text-fill: #440235");

        this.setBackground(new Background(
                new BackgroundFill (
                		TileSetting.COLORS.get(this.value),
                        new CornerRadii(5),
                        new Insets(2,2,2,2))

                )
        );
    }

	public Tile() {
	}

	public GameController getGame() {
		return game;
	}

	public void setGame(GameController game) {
		this.game = game;
	}





}
