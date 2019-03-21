package Tiles;

import DLibX.DConsole;
import marioparty.Console;
import ControllerInput.InputAction;

/**
 *
 * @author Cole
 */
public abstract class Tile {

    private final DConsole dc;
    private InputAction[] possibleDirections;
    
    private int x;
    private int y;

    Tile(int x, int y) {
        this.dc = Console.getInstance();
        this.x = x;
        this.y = y;
        
    }

    public void draw() {
        this.dc.fillEllipse(this.x, this.y, 20, 20);
    }
    
    
}
