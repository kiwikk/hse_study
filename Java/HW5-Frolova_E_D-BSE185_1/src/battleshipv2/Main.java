package battleshipv2;

import battleshipv2.panes.MenuPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    static String[] args;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BattleShip v 2.0");

        if (args.length == 0) {
            closeApplication(primaryStage);
            return;
        }

        MenuPane menuPane = new MenuPane(primaryStage, args[0].toLowerCase());
        primaryStage.setScene(new Scene(menuPane, 500, 550));
        InputStream iconStream = getClass().getResourceAsStream("icons/ship.png");
        Image img = new Image(iconStream);
        primaryStage.getIcons().add(img);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Main.args = args;
        launch(args);
    }

    private void closeApplication(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong Parameters", ButtonType.CLOSE);
        alert.show();
        primaryStage.close();
    }
}
