/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import DLibX.DConsole;

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
