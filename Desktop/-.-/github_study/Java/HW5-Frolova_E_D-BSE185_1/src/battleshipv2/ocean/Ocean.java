package battleshipv2.ocean;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * represents Ocean (a home of ships)
 */
public class Ocean {
    public Ocean myOcean;
    Pane pain;
    Stage primaryStage;
    private int oceanSize;
    private ShipButton[][] ships;


    public Ocean(int oceanSize, Pane pain, Stage primaryStage) {
        this.pain = pain;
        this.primaryStage = primaryStage;
        this.oceanSize = oceanSize;
        ships = new ShipButton[oceanSize][oceanSize];
    }

    /**
     * creates a buttons-ships
     */
    public void setButtons(int startX, int startY, boolean isClickable) {
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                ships[i][j] = new ShipButton(startX + 37 * i, startY + 37 * j, i, j, this);
                ships[i][j].setClickable(isClickable);
            }
        }

        addButtons();
    }

    public void addButtons() {
        for (int i = 0; i < oceanSize; i++) {
            pain.getChildren().addAll(ships[i]);
        }
    }

    public void addNotClickedButtons() {
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                ships[i][j].setClickable(false);
            }
        }

        addButtons();
    }

    public void setPain(Pane pane) {
        this.pain = pane;
    }

    public void setOcean(Ocean ocean) {
        this.myOcean = ocean;
    }

    public boolean isShipAt(int x, int y) {
        if (x < 0 || y < 0 || x == 10 || y == 10) return true;
        if (x > 10 || y > 10) return false;
        return ships[x][y].type == ShipButton.Type.EMPTY;
    }

    public ShipButton getShipAt(int x, int y) {
        return ships[x][y];
    }

}
