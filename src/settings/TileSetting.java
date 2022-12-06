package settings;

import javafx.scene.paint.Color;

import java.util.Map;

public class TileSetting {

    public static final int WIDTH = 75;
    public static final int HEIGHT = 75;

    public static final Map<Integer, Color> COLORS = Map.of(

    		0, Color.rgb(252, 252, 252),
            1, Color.rgb(181, 207, 97),
            2, Color.rgb(230, 194, 74),
            3, Color.rgb(198, 132, 84),
            4, Color.rgb(192, 83, 78),
            5, Color.rgb(196, 83, 147),
            6, Color.rgb(147, 73, 198),
            7, Color.rgb(96, 61, 165),
            8, Color.rgb(54, 65, 181),
            9, Color.rgb(54, 129, 197)

    );

}
