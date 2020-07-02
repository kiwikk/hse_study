package battleship.ships;

import javafx.stage.Stage;

/**
 * A ship with 2 decks
 * */
public class DestroyersButton extends ShipButton {
    private static final int lenght = 2;
    private static final int number = 3;

    public DestroyersButton(Ocean ocean, Stage primaryStage) {
        super(ocean, primaryStage);
        isEmpty = false;
    }

    @Override
    public int getLenght() {
        return lenght;
    }

    @Override
    public void generateDeck(int bound) {
        for (int i = 0; i < number; i++) {
            super.generateDeck(bound);
        }
    }
}
