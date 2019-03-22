package Tiles;

import DLibX.DConsole;
import marioparty.Console;
import ControllerInput.InputAction;

/**
 *
 * @author Cole
 */
public abstract class Tile {

    protected final DConsole dc;
    protected InputAction[] possibleDirections;

    protected int x;
    protected int y;

    Tile(int x, int y) {
        this.dc = Console.getInstance();
        this.x = x;
        this.y = y;

    }

    public void draw() {
        this.dc.fillEllipse(this.x, this.y, 20, 20);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract void triggerEvent();

}
