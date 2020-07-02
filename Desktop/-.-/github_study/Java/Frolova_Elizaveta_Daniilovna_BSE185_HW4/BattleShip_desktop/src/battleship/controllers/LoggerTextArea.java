package battleship.controllers;

import javafx.scene.control.TextArea;

public class LoggerTextArea extends TextArea {
    public LoggerTextArea(double x, double y, double height, double width) {
        super("");
        setLayoutX(x);
        setLayoutY(y);
        setMaxHeight(height);
        setMaxWidth(width);
        setEditable(false);
        setFocused(false);
        setFocusTraversable(false);
    }

    public void setTextMessage(String message) {
        this.setText(this.getText() + message + "\n");
        setScrollTop(Double.MAX_VALUE);
    }

}
