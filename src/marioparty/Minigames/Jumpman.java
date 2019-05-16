/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import DLibX.DConsole;
import java.awt.Color;
import marioparty.Console;
import marioparty.Constants;

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
    private DConsole dc = Console.getInstance();

    public Block(int x) {
        this.x = x;
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

    public void draw() {
        this.dc.fillRect(this.x, this.y, this.size, 3 * this.size);
    }
}

class Jumper extends MinigameObject {

    private int size;
    private Color color;

    public Jumper() {
        this.x = 30;
        this.y = 585;
        this.size = 30;
        this.color = new Color(145, 30, 30);
    }

    @Override
    protected void draw() {
        dc.setPaint(this.color);
        dc.fillRect(this.x, this.y, this.size, this.size);
    }

    public void jump() {
        if (this.dc.isKeyPressed(' ')) {
            //TODO
        }
    }

}

public class Jumpman extends Minigame {

    Block[] block = new Block[10];
    Jumper[] jumper = new Jumper[Constants.NUM_OF_PLAYERS];

    public Jumpman() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        for (int i = 0; i < 10; i++) {
            block[i] = new Block((i * 275) + 900);
        }
        for (int i = 0; i < jumper.length; i++) {
            jumper[i] = new Jumper();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < block.length; i++) {
            block[i].move();
            block[i].draw();
        }
        for (int i = 0; i < jumper.length; i++) {
            jumper[i].draw();
        }
    }

    @Override
    public int isDone() {
        if (this.dc.isKeyPressed(' ')) {
            return 1;
        }
        return -1;
    }

}
