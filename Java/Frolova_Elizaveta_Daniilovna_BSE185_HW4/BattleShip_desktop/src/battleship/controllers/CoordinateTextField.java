package battleship.controllers;

import javafx.scene.control.TextField;

public class CoordinateTextField extends TextField {

    public CoordinateTextField(double x, double y, String promtText) {
        setLayoutX(x);
        setLayoutY(y);
        setPrefColumnCount(2);
        setPromptText(promtText);
        setFocusTraversable(false);
    }
}
