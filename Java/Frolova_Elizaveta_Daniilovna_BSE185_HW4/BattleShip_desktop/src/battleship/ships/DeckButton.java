package battleship.ships;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Is main part of ship
 */
public class DeckButton extends ShipButton {
    static Random rnd = new Random();

    @Override
    public int getLenght() {
        return 1;
    }

    DeckButton(Ocean ocean, Stage primaryStage) {
        super(ocean, primaryStage);
    }

    DeckButton(int x, int y) {
        super();
        setBackground(new Background(new BackgroundFill(Color.OLIVE, CornerRadii.EMPTY, new Insets(0))));
        coordinates.setX(x);
        coordinates.setY(y);
    }

    //finds place for itself
    public void generatePlace(int bound) {
        boolean check = false;
        int x = 0, y = 0;

        while (!check) {
            x = rnd.nextInt(bound - 1) + 1;
            y = rnd.nextInt(bound - 1) + 1;
            check = checkPlace(x, y);
        }

        coordinates.setX(x);
        coordinates.setY(y);
    }

    //checks, if a place is comfortable
    public boolean checkPlace(int x, int y) {
        return ocean.getShipAt(x, y)
                && ocean.getShipAt(x - 1, y + 1)
                && ocean.getShipAt(x + 1, y)
                && ocean.getShipAt(x + 1, y + 1)
                && ocean.getShipAt(x - 1, y)
                && ocean.getShipAt(x, y + 1)
                && ocean.getShipAt(x - 1, y - 1)
                && ocean.getShipAt(x, y - 1)
                && ocean.getShipAt(x + 1, y - 1);
    }


}
