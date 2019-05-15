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

    public Block(int x) {
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

    public int getMovespeed() {
        return this.movespeed;
    }

    public void move() {
        this.x -= movespeed;
    }
}

class Jumper extends MinigameObject {

    private int size;
    private Color color;

    public Jumper(){
        
        this.size = 30;
        this.color = new Color (145,30,30);
}
    @Override
    protected void draw() {
        dc.setPaint(color);
        dc.fillRect(x, y, size, size);
    }
}

public class Jumpman extends Minigame {
    Block[] block = new Block[10];
    public Jumpman() {
        super(MinigameType.FFA, 15000);
    }

    
    @Override
    public void init() {
       for(int i = 0; i < 10; i ++){
           block[i] = new Block((i * 50) + 900);       
       }
    }

    @Override
    public void run() {
        for (int i = 0; i < block.length; i ++){
            block[i].move();
        }
    }

    @Override
    public int isDone() {
        return -1;
    }

}
