package battleship.ships;

import javafx.stage.Stage;

/**
 * A ship with 4 decks
 * */
public class BattleshipButton extends ShipButton {
    private static final int lenght = 4;
    private static final int number = 1;

    @Override
    public int getLenght() {
        return lenght;
    }

    public BattleshipButton(Ocean ocean, Stage primaryStage) {
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
