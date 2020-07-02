package battleship.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MenuPane extends Pane {

    public MenuPane(Stage primaryStage) {

        Label label = addLabel();
        Button playButton = addButton("Play", 120, 140);
        Button multiPlayButton = addButton("Multiplay", 270, 220);
        Button infoButton = addButton("Info", 160, 320);

        playButton.setOnAction((ActionEvent ev) -> {
            GamePane pain = new GamePane(primaryStage);
            primaryStage.setScene(new Scene(pain, 450, 500));
        });

        multiPlayButton.setOnAction((ActionEvent ev) -> {
            Alert message=new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Oh..");
            message.setHeaderText("This part of game is in development");
            message.setContentText("Sry..");

            message.show();
        });

        infoButton.setOnAction((ActionEvent ev)->{
            Alert message=new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Info");
            message.setHeaderText("Version 1.0");
            message.setContentText("Autor: Frolova Elizabeth, BSE185");

            message.show();
        });

        getChildren().addAll(label, playButton, multiPlayButton, infoButton);
    }

    Button addButton(String text, int x, int y) {
        Button button = new Button();

        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setText(text);

        button.setMinHeight(70);
        button.setMinWidth(120);

        return button;
    }

    Label addLabel() {
        Label label = new Label("BattleShip");
        label.setLayoutX(140);
        label.setLayoutY(50);
        label.setFont(new Font(40));

        return label;
    }
}
