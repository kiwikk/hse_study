package battleship.ships;

import battleship.controllers.Coordinates;
import battleship.controllers.InfoLabel;
import battleship.controllers.LoggerTextArea;
import battleship.controllers.MessageBox;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * A "main" class, that's represented by each ship in the game
 */
public abstract class ShipButton extends Button {
    static Random rnd = new Random();

    ArrayList<ShipButton> decks = new ArrayList<>();
    static Ocean ocean;
    static Stage primaryStage;

    static LoggerTextArea logger;
    static InfoLabel label;

    boolean isHorizontal;
    boolean isEmpty;
    boolean isClicked;
    boolean isShotedDown;
    Color color;

    private static int howManyKilled = 0;
    private static int howManyShots = 0;
    private static int howManyShotedDown = 0;

    private static final int lenght = 0;
    Coordinates coordinates = new Coordinates();

    public abstract int getLenght();

    ShipButton() {
        setMinSize(27, 27);
        setColor(Color.LIGHTGRAY);
        setOnMouseClicked(this::handleShootEvent);
        this.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            focusState(newValue);
        });
    }

    ShipButton(Ocean ocean, Stage primaryStage) {
        this();
        this.ocean = ocean;
        this.primaryStage = primaryStage;
    }

    //is focus of the pointer and it's color
    private void focusState(boolean value) {
        if (value) {
            setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, new Insets(0))));
            setOnKeyPressed((KeyEvent ev) -> {
                if (ev.getCode() == KeyCode.ENTER)
                    handleShootEvent(ev);
            });
        } else
            setColor(color);
    }

    /**
     * react on a click
     */
    public void handleShootEvent(Event ev) {
        if (isClicked) {
            new MessageBox(Alert.AlertType.ERROR, primaryStage, "Khm..", "Would you like to shoot a shot place?", "I wouldn't.");
            logger.setTextMessage("Missclicked");
        } else {
            if (isEmpty())
                setClicked();
            else {
                setShotedDown();
                if (isEverybodyKilled()) {
                    setKilledColor();
                    howManyKilled++;
                    howManyShotedDown--;
                    if (howManyKilled == 10) {
                        setZeros();
                        logger.setTextMessage("The end of the game");
                        MessageBox msg = new MessageBox(Alert.AlertType.CONFIRMATION, primaryStage, "Congratulations!", "You win.", "Would you like to replay?");
                    }
                    logger.setTextMessage("Killed a " + this.toString() + " at " + coordinates);
                } else {
                    logger.setTextMessage("Shoted down a ship at " + coordinates);
                }
            }
        }

        ShipButton.label.setSankShips(howManyKilled);
        ShipButton.label.setShotedDownShips(howManyShotedDown);
        ShipButton.label.setShots(howManyShots);

        ShipButton.label.setInfo();
    }


    boolean isEverybodyKilled() {
        return decks.stream().allMatch(d -> d.isShotedDown);
    }

    void setKilledColor() {
        for (var deck : decks
        ) {
            deck.setColor(Color.DARKGRAY);
        }
    }

    //generates a whole ship
    public void generateDeck(int bound) {
        boolean check = false;
        isHorizontal = rnd.nextBoolean();

        DeckButton deck = new DeckButton(ocean, primaryStage);

        while (!check) {
            check = true;
            deck.generatePlace(bound);
            this.coordinates.setY(deck.coordinates.getY());
            this.coordinates.setX(deck.coordinates.getX());
            for (int i = 1; i < getLenght(); ++i) {
                check &= deck.checkPlace(coordinates.getX() + (isHorizontal ? i : 0),
                        coordinates.getY() + (!isHorizontal ? i : 0));
            }
        }

        ArrayList<ShipButton> all_decks = new ArrayList<>();

        for (int i = 0; i < getLenght(); i++) {
            DeckButton tmp = new DeckButton(coordinates.getX() + (isHorizontal ? i : 0),
                    coordinates.getY() + (!isHorizontal ? i : 0));
            ocean.setShip(tmp.coordinates.getX(), tmp.coordinates.getY(), tmp);
            all_decks.add(tmp);
        }

        setAllDecks(all_decks);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setClicked() {
        isClicked = true;
        howManyShots++;
        setColor(Color.LIGHTBLUE);
    }

    public void setShotedDown() {
        if (decks.stream().noneMatch(d -> d.isShotedDown)) {
            howManyShotedDown++;
        }
        howManyShots++;
        isShotedDown = true;
        isClicked = true;
        setColor(Color.LIGHTPINK);
    }

    static void setLogger(LoggerTextArea logger) {
        ShipButton.logger = logger;
    }

    static void setLabel(InfoLabel label) {
        ShipButton.label = label;
    }

    public void setDecks(ArrayList<ShipButton> decks) {
        this.decks = decks;
    }

    void setAllDecks(ArrayList<ShipButton> decks) {
        for (var d : decks
        ) {
            d.setDecks(decks);
        }
    }

    public static void setZeros() {
        howManyKilled = 0;
        howManyShots = 0;
        howManyShotedDown = 0;
    }

    @Override
    public String toString() {
        String type = "";

        switch (this.decks.size()) {
            case 1:
                type = "Submarine (length 1)";
                break;
            case 2:
                type = "Destroyer (length 2)";
                break;
            case 3:
                type = "Cruiser (length 3)";
                break;
            case 4:
                type = "Battleship (length 4)";
        }

        return type;
    }

    public void setColor(Color color) {
        this.color=color;
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, new Insets(0))));
    }
}
