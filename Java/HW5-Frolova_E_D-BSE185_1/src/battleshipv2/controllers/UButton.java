package battleshipv2.controllers;

import javafx.scene.control.Button;

public class UButton extends Button {

    public UButton(String text, int x, int y, double height, double width) {
        setLayoutX(x);
        setLayoutY(y);
        setMinHeight(height);
        setMinWidth(width);

        setText(text);
    }
}
