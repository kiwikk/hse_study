package battleship.ships;

import javafx.stage.Stage;

/**
 * A ship with 1 deck
 * */
public class SubmarineButton extends ShipButton {
    private static final int lenght = 1;
    private static final int number = 4;

    public SubmarineButton(Ocean ocean, Stage primaryStage) {
        super(ocean, primaryStage);
        isEmpty = false;
    }

    @Override
    public int getLenght() {
        return lenght;
    }

    public static int getNumber() {
        return number;
    }

    @Override
    public void generateDeck(int bound) {
        for (int i = 0; i < number; i++) {
            super.generateDeck(bound);
        }
    }
}
