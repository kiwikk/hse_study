package battleship.controllers;

import battleship.ships.ShipButton;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

//if I want to make a game with lifes,
// I should use Choice dialog
//from here https://code.makery.ch/blog/javafx-dialogs-official/

/**
 * Represents messages about replay, error or exit
 * */
public class MessageBox {
    Stage primaryStage;
    private ButtonType replay_yes = new ButtonType("Yes");
    private ButtonType replay_no = new ButtonType("No");
    private ButtonType exit_yes = new ButtonType("Yes");
    private ButtonType exit_no = new ButtonType("Continue", ButtonBar.ButtonData.CANCEL_CLOSE);

    //replay == confirmation
    //exit == warning
    //second click == error
    public MessageBox(Alert.AlertType type,
                      Stage primaryStage,
                      String title,
                      String header,
                      String message) {
        Alert messageBox = new Alert(type);

        this.primaryStage = primaryStage;

        messageBox.setTitle(title);
        messageBox.setHeaderText(header);
        messageBox.setContentText(message);

        if (type == Alert.AlertType.CONFIRMATION) {
            makeConfirmation(messageBox);
        } else if (type == Alert.AlertType.WARNING) {
            makeWarning(messageBox);
        } else
            messageBox.show();
    }

    void makeConfirmation(Alert messageBox) {
        messageBox.getButtonTypes().setAll(replay_yes, replay_no);

        Optional<ButtonType> result = messageBox.showAndWait();

        if (result.get() == replay_yes) {
            GamePane pain = new GamePane(primaryStage);
            primaryStage.setScene(new Scene(pain, 450, 500));
        } else {
            MenuPane menu = new MenuPane(primaryStage);
            primaryStage.setScene(new Scene(menu, 450, 500));
        }
    }


    void makeWarning(Alert messageBox) {
        messageBox.getButtonTypes().setAll(exit_yes, exit_no);

        Optional<ButtonType> result = messageBox.showAndWait();

        if (result.get() == exit_yes) {
            MenuPane pain = new MenuPane(primaryStage);
            primaryStage.setScene(new Scene(pain, 450, 500));
        }
    }
}
