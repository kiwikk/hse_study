package battleshipv2.panes;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuPane extends Pane {

    String arg;

    public MenuPane(Stage primaryStage, String arg) {
        this.arg = arg;

        Label label = addLabel();
        Button playButton = addButton("Play", 120, 140);
        Button multiPlayButton = addButton("Multiplay", 270, 220);
        Button infoButton = addButton("Info", 160, 320);

        playButton.setOnAction((ActionEvent ev) -> {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Oh..");
            message.setHeaderText("This part is not supported in this version.");
            message.setContentText("Yeah, I'm really lazy....");

            message.show();

        });

        multiPlayButton.setOnAction((ActionEvent ev) -> {
            ArrangementPane pain = new ArrangementPane(primaryStage, arg);
            primaryStage.setScene(new Scene(pain, 500, 550));
        });

        infoButton.setOnAction((ActionEvent ev) -> {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Info");
            message.setHeaderText("Version 2.0");
            message.setContentText("Author: Frolova Elizabeth, BSE185");

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
        Label label = new Label("BattleShip v 2.0");
        label.setLayoutX(140);
        label.setLayoutY(50);
        label.setFont(new Font(40));

        return label;
    }
}
