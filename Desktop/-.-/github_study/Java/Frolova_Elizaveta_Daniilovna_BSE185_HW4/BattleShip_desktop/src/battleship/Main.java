/**
 * @Autor Frolova Elizabeth
 * group:BSE185
 * @version 1.0
 *
 * */

package battleship;

import battleship.controllers.MenuPane;
import javafx.application.Application;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BattleShip");
        MenuPane menu = new MenuPane(primaryStage);
        primaryStage.setScene(new Scene(menu, 450, 500));
        InputStream iconStream = getClass().getResourceAsStream("icons/ship.png");
        Image img = new Image(iconStream);
        primaryStage.getIcons().add(img);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
