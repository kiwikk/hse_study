package battleshipv2.controllers;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ULabel extends Label {
    public ULabel(String text, int x, int y) {
        setText(text);
        setLayoutX(x);
        setLayoutY(y);
        setFont(new Font(20));
    }
}
