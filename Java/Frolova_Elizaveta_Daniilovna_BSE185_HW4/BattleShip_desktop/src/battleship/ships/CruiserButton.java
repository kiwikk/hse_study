package battleship.ships;

import javafx.stage.Stage;

/**
 * A ship with 3 decks
 * */
public class CruiserButton extends ShipButton {
    private static final int lenght = 3;
    private static final int number = 2;


    @Override
    public int getLenght() {
        return lenght;
    }

    public CruiserButton(Ocean ocean, Stage primaryStage) {
        super(ocean, primaryStage);
        isEmpty = false;
    }

    @Override
    public void generateDeck(int bound) {
        for (int i = 0; i < number; i++) {
            super.generateDeck(bound);
        }
    }
}
