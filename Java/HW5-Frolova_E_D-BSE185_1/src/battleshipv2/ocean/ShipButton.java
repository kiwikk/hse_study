package battleshipv2.ocean;

import battleshipv2.panes.ArrangementPane;
import battleshipv2.panes.GamePane;
import battleshipv2.sockets.USocket;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ShipButton extends Button {

    public Type type;
    public static boolean isMyTurn;
    Color color;
    Type tmpType;
    boolean isHorizontal;
    Ocean ocean;
    boolean isClickable;
    boolean wasClicked;

    public static int moves = 0;
    public static int alive = 20;
    public static int mySteps = 0;
    public static int notMySteps = 0;

    int i, j;
    int index;

    public static int[] numberShips;

    public ShipButton(int x, int y, int i, int j, Ocean ocean) {
        setMinSize(35, 35);
        setMaxSize(35, 35);
        setColor(Color.LIGHTGRAY);
        setLayoutX(x);
        setLayoutY(y);

        this.type = Type.EMPTY;

        this.i = i;
        this.j = j;

        this.ocean = ocean;
        setNumberShips();
        numberShips[0] = 0;

        setOnMouseEntered(this::getData);
        setOnMouseExited(this::removeData);

        setOnMouseClicked(this::onClick);
    }

    private void onClick(MouseEvent event) {
        if (isClickable) {
            shoot();
        } else setData(event);
    }

    void shoot() {
        if (!wasClicked) {
            if (isMyTurn) {
                wasClicked = true;
                //notMySteps++;

                USocket.writeCoordinates(i, j);
                sleep();
                GamePane.logger.setTextMessage("[" + i + "," + j + "] : shoot from " + GamePane.username);
                mySteps++;

                String response = "";
                sleep();
                response = USocket.readResponse();
                if (response == null || alive == 0 || moves == 20 || response.equals("exit")) {
                    GamePane.finish = true;
                    GamePane.winner = GamePane.username;
                    if (response == null || response.equals("exit")) GamePane.winner = GamePane.anotherName;
                    return;
                }
                setColor(response);
                //mySteps++;
                listen(this.ocean);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "This place has already been shot.");
            alert.showAndWait();
        }
    }

    public static void listen(Ocean ocean) {
        new Thread(() -> {
            while (!isMyTurn) {
                System.out.println("Start listening");
                String[] coordinates = {""};

                sleep();
                String response = USocket.readResponse();

                if (response == null || response.equals("exist")) {
                    GamePane.finish = true;
                    GamePane.winner = GamePane.anotherName;
                    return;
                }

                notMySteps++;
                coordinates = response.split(" ");

                int x = 0, y = 0;
                try {
                    x = Integer.parseInt(coordinates[0]);
                    y = Integer.parseInt(coordinates[1]);
                } catch (NumberFormatException ignored) {
                }

                if (ocean.myOcean.getShipAt(x, y).type == Type.EMPTY) {
                    ocean.myOcean.getShipAt(x, y).setColor(Color.LIGHTBLUE);
                    isMyTurn = true;
                    GamePane.meLabel.setTextFill(Color.RED);
                    GamePane.notMeLabel.setTextFill(Color.BLACK);
                    GamePane.logger.setTextMessage("[" + x + "," + y + "] : missed from " + GamePane.anotherName);
                    USocket.writeState(0);
                    sleep();
                } else {
                    ocean.myOcean.getShipAt(x, y).setColor(Color.BLACK);
                    isMyTurn = false;
                    GamePane.meLabel.setTextFill(Color.BLACK);
                    GamePane.notMeLabel.setTextFill(Color.RED);
                    GamePane.logger.setTextMessage("[" + x + "," + y + "] : fired from " + GamePane.anotherName);
                    USocket.writeState(1);
                    sleep();
                    moves++;
                    if (moves == 20) {
                        GamePane.finish = true;
                        GamePane.winner = GamePane.anotherName;
                        return;
                    }
                }
            }
        }).start();
    }


    static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }
    }

    private void setData(MouseEvent event) {
        this.isHorizontal = ArrangementPane.isHorizontal;

        boolean check = checkPlace();

        if (!check) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No.", ButtonType.NO);
            alert.show();
        } else {
            setPossible(false);
            ArrangementPane.type = Type.EMPTY;
        }
    }

    private void removeData(MouseEvent event) {
        for (int i = 0; i < tmpType.ordinal(); i++) {
            int x = this.i + (isHorizontal ? i : 0);
            int y = this.j + (!isHorizontal ? i : 0);

            if (x < 10 && y < 10) {
                ocean.getShipAt(x, y).setText("");
                ocean.getShipAt(x, y).setColor(ocean.getShipAt(x, y).color);
                ocean.getShipAt(x, y).setStyle("-fx-border-color: #ff0000; -fx-border-width: 0px;");
            }
        }
    }

    void setNo() {
        for (int i = 0; i < tmpType.ordinal(); i++) {
            int x = this.i + (isHorizontal ? i : 0);
            int y = this.j + (!isHorizontal ? i : 0);

            if (x < 10 && y < 10) {
                ocean.getShipAt(x, y).setText("No");
                ocean.getShipAt(x, y).setStyle("-fx-border-color: #ff0000; -fx-border-width: 1px;");
            }
        }
    }

    void setPossible(boolean tmp) {
        for (int i = 0; i < tmpType.ordinal(); i++) {
            int x = this.i + (isHorizontal ? i : 0);
            int y = this.j + (!isHorizontal ? i : 0);

            if (tmp) {
                ocean.getShipAt(x, y).setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, new Insets(0))));
            } else {
                index = i;
                ocean.getShipAt(x, y).type = ArrangementPane.type;
                ocean.getShipAt(x, y).setColor(getColor());
            }
        }

        if (!tmp) {
            numberShips[type.ordinal()]--;
        }
    }

    private void getData(Event mouseEvent) {
        this.tmpType = ArrangementPane.type;
        this.isHorizontal = ArrangementPane.isHorizontal;

        boolean check = checkPlace();

        if (!check) {
            setNo();
        } else {
            setPossible(true);
        }
    }

    boolean checkPlace() {
        if (type != Type.EMPTY) return false;

        boolean check = true;

        for (int i = 0; i < tmpType.ordinal(); i++) {
            int x = this.i + (isHorizontal ? i : 0);
            int y = this.j + (!isHorizontal ? i : 0);

            if (x < 10 && y < 10) {
                check &= checkDeck(x, y);
            } else return false;

        }
        return check;
    }

    boolean checkDeck(int x, int y) {
        return ocean.isShipAt(x, y)
                && ocean.isShipAt(x - 1, y + 1)
                && ocean.isShipAt(x + 1, y)
                && ocean.isShipAt(x + 1, y + 1)
                && ocean.isShipAt(x - 1, y)
                && ocean.isShipAt(x, y + 1)
                && ocean.isShipAt(x - 1, y - 1)
                && ocean.isShipAt(x, y - 1)
                && ocean.isShipAt(x + 1, y - 1);
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, new Insets(0))));
    }

    void setColor(String response) {
        switch (response) {
            case "0":
                setColor(Color.LIGHTBLUE);
                isMyTurn = false;
                GamePane.meLabel.setTextFill(Color.BLACK);
                GamePane.notMeLabel.setTextFill(Color.RED);
                GamePane.logger.setTextMessage("missed from " + GamePane.username);
                break;
            case "1":
                setColor(Color.BLACK);
                isMyTurn = true;
                GamePane.meLabel.setTextFill(Color.RED);
                GamePane.notMeLabel.setTextFill(Color.BLACK);
                GamePane.logger.setTextMessage("fired from " + GamePane.username);
                alive--;
                break;
        }
    }


    public static void setNumberShips() {
        numberShips = new int[5];
        for (int i = 0; i < 5; i++) {
            numberShips[i] = 5 - i;
        }
    }

    Color getColor() {
        if (type != Type.EMPTY)
            return Color.LIGHTGREEN;
        else return Color.LIGHTGRAY;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public enum Type {
        EMPTY, //0 80
        SUBMARINE, //1 4
        DESTROYER, //2 3
        CRUISER, //3 2
        BATTLESHIP //4 1
    }
}
