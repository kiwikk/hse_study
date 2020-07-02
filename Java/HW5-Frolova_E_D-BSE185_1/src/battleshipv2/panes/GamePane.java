package battleshipv2.panes;

import battleshipv2.controllers.LoggerTextArea;
import battleshipv2.controllers.UButton;
import battleshipv2.controllers.ULabel;
import battleshipv2.ocean.Ocean;
import battleshipv2.ocean.ShipButton;
import battleshipv2.sockets.USocket;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GamePane extends Pane {
    Ocean ocean;
    Stage primaryStage;
    USocket socket;
    Alert alert;
    public static ULabel meLabel;
    public static ULabel notMeLabel;
    public static LoggerTextArea logger;
    public static String username;
    public static String anotherName;
    public static String winner;
    public static boolean finish = false;


    public GamePane(Stage primaryStage, String anotherUsername, Ocean ocean, USocket socket) {
        this.primaryStage = primaryStage;
        username = socket.uClient == null ? socket.uServer.name : socket.uClient.name;
        anotherName = anotherUsername;
        this.socket = socket;
        this.ocean = ocean;
        primaryStage.setScene(new Scene(this, 1100, 420));

        meLabel = new ULabel("Me", 30, 5);
        ocean.setPain(this);
        ocean.addNotClickedButtons();

        notMeLabel = new ULabel(anotherName, 450, 5);
        Ocean anotherOcean = new Ocean(10, this, primaryStage);
        anotherOcean.setButtons(450, 30, true);
        anotherOcean.setOcean(ocean);

        if (socket.uServer == null) {
            ShipButton.isMyTurn = true;
            meLabel.setTextFill(Color.RED);
        } else new Thread(() ->
        {
            ShipButton.listen(anotherOcean);
            notMeLabel.setTextFill(Color.RED);
        }).start();

        getGreeting(anotherName);

        this.setOnMouseMoved((event) ->
                {
                    if (finish) endGame(winner);
                }
        );

        UButton exitButton = new UButton("Exit", 905, 350, 50, 100);
        exitButton.setOnMouseClicked((event) -> {
            finish = true;
            winner = username;
        });

        getChildren().addAll(meLabel, notMeLabel, logger, exitButton);
    }

    void getGreeting(String name) {
        alert = new Alert(Alert.AlertType.INFORMATION, "Your partner is " + name);
        alert.show();
    }

    public static void endGame(String name) {
        winner = name;
        String text = (ShipButton.moves == 20 || ShipButton.alive == 0) ? "The game is over.\n Winner is " + name : "The game was stopped by " + name;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.showAndWait();

        ShipButton.isMyTurn = false;
        USocket.closeTheGame();

        showResults();

        Platform.exit();
    }

    public static void showResults() {
        String text = "My steps: " + ShipButton.mySteps + "\nNot my steps: " + ShipButton.notMySteps;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.setTitle("Results");
        alert.setHeaderText("Results:\nExit's reason: " + winner);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
