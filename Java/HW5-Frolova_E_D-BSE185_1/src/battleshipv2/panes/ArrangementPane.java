package battleshipv2.panes;

import battleshipv2.controllers.LoggerTextArea;
import battleshipv2.controllers.MessageBox;
import battleshipv2.controllers.UButton;
import battleshipv2.controllers.ULabel;
import battleshipv2.ocean.Ocean;
import battleshipv2.ocean.ShipButton;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Optional;

public class ArrangementPane extends Pane {
    public static ShipButton.Type type;
    public static boolean isHorizontal;

    private Ocean ocean;
    private Stage primaryStage;
    private UButton goBack;
    MessageBox mbox;
    String arg;

    public ArrangementPane(Stage primaryStage, String arg) {
        this.primaryStage = primaryStage;
        this.arg = arg;
        createNewOcean();
        GamePane.logger = new LoggerTextArea(850, 30, 300, 210);

        this.setOnKeyPressed(this::changeOrientation);

        ULabel text = new ULabel("Arrangement", 175, 0);
        UButton playButton = new UButton("Play", 176, 440, 50, 80);
        setEvents(playButton);

        goBack = new UButton("Go Back", 2, 2, 7, 7);
        goBack.setOnAction(this::setGoBack);
        UButton removeButton = new UButton("Remove All", 410, 370, 20, 80);
        removeButton.setOnMouseClicked((event) -> createNewOcean());

        getChildren().addAll(playButton, removeButton, text, goBack);
    }

    void setGoBack(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Go Back");
        alert.setHeaderText("Are u sure, u want to exit?");
        alert.setContentText("All progress will be lost");

        alert.getButtonTypes().setAll(new ButtonType("Yes"), new ButtonType("No"));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == alert.getButtonTypes().get(0)) {
            MenuPane pain = new MenuPane(primaryStage, arg);
            primaryStage.setScene(new Scene(pain, 500, 550));
        }
    }

    private void createNewOcean() {
        ocean = new Ocean(10, this, primaryStage);
        ocean.setButtons(30, 30, false);
        type = ShipButton.Type.EMPTY;
        setButtons();
    }

    private void setEvents(UButton play) {
        play.setOnMouseEntered((event) ->
        {
            if (Arrays.stream(ShipButton.numberShips).sum() != 0) {
                //TODO:To uncomment the line!!!!
                play.setDisable(true);
            }
        });

        play.setOnMouseExited((event) -> {
            play.setDisable(false);
        });

        play.setOnAction(this::playGame);
    }

    private void playGame(ActionEvent actionEvent) {
        switch (arg) {
            case "server":
                mbox = new MessageBox("Server", primaryStage, ocean);
                break;
            case "client":
                mbox = new MessageBox("Client", primaryStage, ocean);
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong program parameters", ButtonType.CLOSE);
                alert.show();
                primaryStage.close();
        }
    }

    private void changeOrientation(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL) {
            isHorizontal = !isHorizontal;
        }
    }


    //    SUBMARINE, //1 4
    //    DESTROYER, //2 3
    //    CRUISER, //3 2
    //    BATTLESHIP //4 1
    public void setButtons() {
        UButton submarine = new UButton("Submarine", 410, 30, 20, 72);
        UButton destroyer = new UButton("Destroyer", 410, 60, 20, 72);
        UButton cruiser = new UButton("Cruiser", 410, 90, 20, 72);
        UButton battleship = new UButton("Battleship", 410, 120, 20, 72);

        setTypes(submarine, destroyer, cruiser, battleship);
        setDisable(submarine, destroyer, cruiser, battleship);

        getChildren().addAll(submarine, destroyer, cruiser, battleship);
    }

    private void setTypes(UButton submarine, UButton destroyer, UButton cruiser, UButton battleship) {
        submarine.setOnMouseClicked((event) -> {
            type = ShipButton.Type.SUBMARINE;
            if (ShipButton.numberShips[type.ordinal()] == 0) submarine.setDisable(true);
        });

        destroyer.setOnMouseClicked((event) -> {
            type = ShipButton.Type.DESTROYER;
            if (ShipButton.numberShips[type.ordinal()] == 0) destroyer.setDisable(true);
        });

        cruiser.setOnMouseClicked((event) -> {
            type = ShipButton.Type.CRUISER;
            if (ShipButton.numberShips[type.ordinal()] == 0) cruiser.setDisable(true);
        });

        battleship.setOnMouseClicked((event) -> {
            type = ShipButton.Type.BATTLESHIP;
            if (ShipButton.numberShips[type.ordinal()] == 0) battleship.setDisable(true);
        });
    }

    private void setDisable(UButton submarine, UButton destroyer, UButton cruiser, UButton battleship) {
        submarine.setOnMouseEntered((event) ->
        {
            if (ShipButton.numberShips[ShipButton.Type.SUBMARINE.ordinal()] == 0) submarine.setDisable(true);
            submarine.setTooltip(new Tooltip(String.format("Lenght: %s\nNumber: %s", ShipButton.Type.SUBMARINE.ordinal(),
                    ShipButton.numberShips[ShipButton.Type.SUBMARINE.ordinal()])));
        });

        destroyer.setOnMouseEntered((event) ->
        {
            if (ShipButton.numberShips[ShipButton.Type.DESTROYER.ordinal()] == 0) destroyer.setDisable(true);
            destroyer.setTooltip(new Tooltip(String.format("Lenght: %s\nNumber: %s", ShipButton.Type.DESTROYER.ordinal(),
                    ShipButton.numberShips[ShipButton.Type.DESTROYER.ordinal()])));
        });

        cruiser.setOnMouseEntered((event) ->
        {
            if (ShipButton.numberShips[ShipButton.Type.CRUISER.ordinal()] == 0) cruiser.setDisable(true);
            cruiser.setTooltip(new Tooltip(String.format("Lenght: %s\nNumber: %s", ShipButton.Type.CRUISER.ordinal(),
                    ShipButton.numberShips[ShipButton.Type.CRUISER.ordinal()])));
        });

        battleship.setOnMouseEntered((event) ->
        {
            if (ShipButton.numberShips[ShipButton.Type.BATTLESHIP.ordinal()] == 0) battleship.setDisable(true);
            battleship.setTooltip(new Tooltip(String.format("Lenght: %s\nNumber: %s", ShipButton.Type.BATTLESHIP.ordinal(),
                    ShipButton.numberShips[ShipButton.Type.BATTLESHIP.ordinal()])));
        });
    }
}
