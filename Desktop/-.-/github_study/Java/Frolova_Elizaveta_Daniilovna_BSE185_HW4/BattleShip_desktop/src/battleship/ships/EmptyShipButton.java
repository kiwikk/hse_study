package battleship.ships;

import javafx.stage.Stage;

/**
 * Represents an empty square
 * */
public class EmptyShipButton extends ShipButton {
    private static final int lenght = 0;
    private static final int number = 1;

    @Override
    public int getLenght() {
        return lenght;
    }

    public EmptyShipButton(Ocean ocean, Stage primaryStage) {
        super(ocean, primaryStage);
        isEmpty=true;
    }

    @Override
    public void generateDeck(int bound) {
        for (int i = 0; i < number; i++) {
            super.generateDeck(lenght);
        }
    }
}
