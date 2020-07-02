package battleshipv2.controllers;

import battleshipv2.ocean.Ocean;
import battleshipv2.panes.GamePane;
import battleshipv2.sockets.Client;
import battleshipv2.sockets.Server;
import battleshipv2.sockets.USocket;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MessageBox extends Dialog {

    String name;
    String port;
    Stage primaryStage;
    Ocean ocean;
    String host = "";
    USocket socket;

    public MessageBox(String title, Stage primaryStage, Ocean ocean) {
        setTitle(title);
        this.primaryStage = primaryStage;
        this.ocean = ocean;
        String type = "";

        switch (title.toLowerCase()) {
            case "server":
                type = "Start";
                break;
            case "client":
                type = "Connect";
                break;
        }

        setHeaderText("Input:");

        Image img = new Image(MessageBox.class.getResourceAsStream("/battleshipv2/icons/connection.png"));
        setGraphic(new ImageView(img));
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/battleshipv2/icons/internet.png").toString()));

        ButtonType loginButtonType = new ButtonType(type, ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 150, 10, 10));


        TextField userHost = new TextField();
        userHost.setPromptText("Host");
        if (!title.toLowerCase().equals("client")) {
            userHost.setVisible(false);
        }
        TextField userPort = new TextField();
        userPort.setPromptText("Port");
        TextField username = new TextField();
        username.setPromptText("Username");


        if (userHost.isVisible()) {
            grid.add(new Label("Host:"), 0, 0);
            grid.add(userHost, 1, 0);
        }

        grid.add(new Label("Port:"), 0, 1);
        grid.add(userPort, 1, 1);
        grid.add(new Label("Username:"), 0, 2);
        grid.add(username, 1, 2);

        Node loginButton = getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        userPort.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(userHost.isVisible() ?
                    (userHost.textProperty().isEmpty().get() || newValue.trim().isEmpty())
                    : newValue.trim().isEmpty());
        });

        userHost.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(userHost.isVisible() ?
                    (userPort.textProperty().isEmpty().get() || newValue.trim().isEmpty())
                    : newValue.trim().isEmpty());
        });

        getDialogPane().setContent(grid);

        Platform.runLater((userHost.isVisible() ? userHost : userPort)::requestFocus);

        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(userPort.getText(), username.getText());
            }
            return null;
        });


        Optional<Pair<String, String>> result = showAndWait();

        result.ifPresent(usernamePassword -> {
            port = usernamePassword.getKey();
            name = usernamePassword.getValue();
            host = userHost.getText();
            try {
                createConnection(title.toLowerCase());
            } catch (Exception ignored) {
            }
        });
    }

    public void createConnection(String type) {
        socket = new USocket();
        try {
            switch (type) {
                case "server":
                    Server server = new Server(name, port);
                    socket.startServer(server);
                    name = socket.startServerConnection();
                    break;
                case "client":
                    Client client = new Client(name, host, port);
                    socket.startClient(client);
                    name = socket.startClientConnection();
                    break;
            }
        } catch (NumberFormatException e) {
            Alert message = new Alert(Alert.AlertType.ERROR, "Wrong port\n (should be from 1024 to 65535)");
            message.showAndWait();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This address does not exist");
            alert.showAndWait();
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        new GamePane(primaryStage, name, ocean, socket);
    }
}
