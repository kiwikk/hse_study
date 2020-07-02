package battleship.controllers;

import battleship.ships.Ocean;
import battleship.ships.ShipButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * GamePane is a main field of single game
 */
public class GamePane extends Pane {
    //в конце игры - messageBox (хотите ли повторить+2 кнопки)
    private Ocean ocean;
    private LoggerTextArea logger;
    InfoLabel infoLabel;
    private Stage primaryStage;
    private Button goBack;
    private CoordinateTextField coordinateX;
    private CoordinateTextField coordinateY;
    private Button shootButton;

    public GamePane(Stage primaryStage) {
        this.primaryStage = primaryStage;
        logger = new LoggerTextArea(20, 500 - (500 / 4) - 30, 500 / 4 + 10, 450 - 40);
        logger.setTextMessage("Start!");
        infoLabel = new InfoLabel(14, 310, 40);
        ocean = new Ocean(10, logger, infoLabel, this, primaryStage);
        goBack = addButton("Go Back", 5, 5, 10, 10, this::setGoBack);

        coordinateX = new CoordinateTextField(320, 250, "x");
        coordinateY = new CoordinateTextField(320, 280, "y");
        shootButton = addButton("Boom", 370, 265, 20, 30, this::shoot);

        getChildren().addAll(logger, infoLabel, goBack, coordinateX, coordinateY, shootButton);
    }

    //shoots by inputing coordinates
    private void shoot(ActionEvent event) {
        try {
            ocean.getShips()[Integer.parseInt(coordinateX.getText())][Integer.parseInt(coordinateX.getText())]
                    .handleShootEvent(event);
        } catch (Exception e) {
            new MessageBox(Alert.AlertType.ERROR, primaryStage,
                    "Error",
                    "Are the numbers correct?",
                    "Check the input data");
        }
        coordinateX.setText("");
        coordinateY.setText("");
    }

    Button addButton(String text, int x, int y, double height, double width, EventHandler<ActionEvent> var1) {

        Button button = new Button(text);

        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMinHeight(height);
        button.setMinWidth(width);

        button.setOnAction(var1);
        button.setFocusTraversable(false);

        if (text.equals("Go Back")) {
            button.setFocusTraversable(true);
            ShipButton.setZeros();
        }

        return button;
    }

    void setGoBack(ActionEvent event) {
        new MessageBox(Alert.AlertType.WARNING, primaryStage,
                "Exit",
                "Are u sure, u want to exit?",
                "All progress will be lost");
    }
}
