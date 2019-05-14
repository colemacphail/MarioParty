/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import java.awt.Color;

/**
 *
 * @author Jacob
 */
class Block {

    private int x;
    private int y;
    private int size;
    private Color color;
    private int movespeed;

    public Block() {
        this.y = 580;
        this.size = 20;
        this.movespeed = 5;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
    }
    
    public int getMovespeed(){
    return this.movespeed;
    }

    public void move() {
        this.x -= movespeed;
    }
}

class Jumper extends MinigameObject {
private int size;
private Color color;
}

public class Jumpman extends Minigame {

    public Jumpman(MinigameType type, long timeout) {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int isDone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
