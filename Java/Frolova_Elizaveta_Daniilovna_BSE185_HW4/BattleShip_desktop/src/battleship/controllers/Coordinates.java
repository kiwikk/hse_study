package battleship.controllers;


/**
 * keeps coordinates for ships
 **/
public class Coordinates {

    int x, y; //values from 0 to 9

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "coordinates [" +
                "" + x +
                "; " + y +
                ']';
    }
}
