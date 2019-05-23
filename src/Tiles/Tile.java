package Tiles;

import DLibX.DConsole;
import marioparty.Console;
import marioparty.Character;
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

    public Tile(int x, int y) { // all tiles must have a position
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

    public abstract void triggerEvent(Character player); // must provide an event when landing on

}
