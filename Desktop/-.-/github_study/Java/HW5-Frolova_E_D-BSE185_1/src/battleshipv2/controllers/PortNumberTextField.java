package battleshipv2.controllers;

import javafx.scene.control.TextField;

public class PortNumberTextField extends TextField {

    public PortNumberTextField(double x, double y, String promtText) {
        setLayoutX(x);
        setLayoutY(y);
        setPrefColumnCount(20);
        setPromptText(promtText);
        setFocusTraversable(false);
    }
}
