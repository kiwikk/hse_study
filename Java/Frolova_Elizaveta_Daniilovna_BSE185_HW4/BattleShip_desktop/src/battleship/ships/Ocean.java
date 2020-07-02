package battleship.ships;

import battleship.controllers.GamePane;
import battleship.controllers.InfoLabel;
import battleship.controllers.LoggerTextArea;
import javafx.stage.Stage;

/**
 * represents Ocean (a home of ships)
 */
public class Ocean {
    GamePane pain;
    Stage primaryStage;
    private int oceanSize;
    private ShipButton[][] decks;
    private ShipButton[][] ships;


    public Ocean(int oceanSize, LoggerTextArea logger, InfoLabel infoLabel, GamePane pain, Stage primaryStage) {
        this.pain = pain;
        this.primaryStage = primaryStage;
        ShipButton.setLabel(infoLabel);
        ShipButton.setLogger(logger);

        this.oceanSize = oceanSize + 2;

        decks = initializeShips(this.oceanSize);
        ships = initializeShips(oceanSize);

        generateShips();
    }

    public boolean getShipAt(int x, int y) {
        return decks[x][y].isEmpty;
    }

    public ShipButton getShip(int x, int y) {
        return ships[x][y];
    }

    /**
     * generates all types of ships and all number of them
     */
    void generateShips() {

        createBattleShips();
        createCruiserShip();
        createDestroyer();
        createSubmarine();

        setButtons();
    }

     ShipButton createCruiserShip() {
        CruiserButton ship = new CruiserButton(this, primaryStage);
        ship.generateDeck(oceanSize - ship.getLenght());

        return ship;
    }

    BattleshipButton createBattleShips() {
        BattleshipButton ship = new BattleshipButton(this, primaryStage);
        ship.generateDeck(oceanSize - ship.getLenght());

        return ship;
    }

    DestroyersButton createDestroyer() {
        DestroyersButton ship = new DestroyersButton(this, primaryStage);
        ship.generateDeck(oceanSize - ship.getLenght());

        return ship;
    }

    SubmarineButton createSubmarine() {
        SubmarineButton ship = new SubmarineButton(this, primaryStage);
        ship.generateDeck(oceanSize - ship.getLenght());

        return ship;
    }

    void setShip(int x, int y, ShipButton ship) {
        ships[x - 1][y - 1] = ship;
        decks[x][y] = ship;
    }

    /**
     * creates a buttons-ships
     */
    void setButtons() {
        int x = 20;
        int y = 40;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ships[i][j].setLayoutX(x + 29 * i);
                ships[i][j].setLayoutY(y + 29 * j);
            }
        }

        for (int i = 0; i < 10; i++) {
            pain.getChildren().addAll(ships[i]);
        }
    }

    ShipButton[][] initializeShips(int oceanSize) {
        ShipButton[][] emptyShips = new ShipButton[oceanSize][oceanSize];

        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                ShipButton empty = new EmptyShipButton(this, primaryStage);
                empty.coordinates.setX(i);
                empty.coordinates.setY(j);
                emptyShips[i][j] = empty;
            }
        }

        return emptyShips;
    }


    public ShipButton[][] getShips() {
        return ships;
    }


}
