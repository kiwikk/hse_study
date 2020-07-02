package battleship.controllers;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Is a label, that represents numbers of sank ships, shots and shoted down ships
 */
public class InfoLabel extends Label {
    private int sankShips;
    private int shotedDown;
    private int shots;

    public InfoLabel(int font_size, double x, double y) {
        this.setFont(new Font(font_size));
        setLayoutX(x);
        setLayoutY(y);
    }

    public void setSankShips(int sankShips) {
        this.sankShips = sankShips;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public void setShotedDownShips(int shotedDown) {
        this.shotedDown = shotedDown;

    }

    public void setInfo() {
        this.setText(
                "Sank Ships: " + sankShips +
                        "\nAlive Ships: " + (10 - sankShips) +
                        "\nShoted down ships: " + shotedDown +
                        "\nShots: " + shots
        );
    }
}
