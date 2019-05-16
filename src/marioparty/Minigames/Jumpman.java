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
    private int width;
    private int length;
    private Color color;
    private int movespeed;

    private DConsole dc = Console.getInstance();

    public Block() {
        
        this.y = 580;
        this.width = 20;
        this.length = 40;
        this.movespeed = 5;
    }
    public void setX(int x){
    this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLength() {
        return this.length;
    }

    public int getMovespeed() {
        return this.movespeed;
    }

    public void move() {
        this.x -= movespeed;
    }

    public void draw() {
        this.dc.fillRect(this.x, this.y, this.width, 3 * this.length);
    }
}

class Jumper extends MinigameObject {

    private int size;
    private Color color;
    private double yChange;
    private boolean pressed;

    public Jumper() {
        this.x = 30;
        this.y = 585;
        this.size = 30;
        this.color = new Color(145, 30, 30);
        this.yChange = 0;
        this.pressed = false;
    }
    
    public int getSize(){
    return this.size;
    }
    

    @Override
    protected void draw() {
        dc.setPaint(this.color);
        dc.fillRect(this.x, this.y, this.size, this.size);
    }

    public void jump() {

        if (dc.isKeyPressed(' ')) {
            if (!pressed) {
                this.pressed = true;
                this.yChange = -10;
            }
        }

        this.y += this.yChange;

        this.yChange += 0.5;

        if (this.y >= 585) {
            this.pressed = false;
            this.yChange = 0;
            this.y = 585;
        }
    }

}

public class Jumpman extends Minigame {

    Block[] blocks = new Block[10];
    Jumper[] jumpers = new Jumper[Constants.NUM_OF_PLAYERS];

    public Jumpman() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        for (int i = 0; i < 10; i++) {
            
            blocks[i] = new Block();
            blocks[i].setX((i * 275) + 900);
        }
        for (int i = 0; i < jumpers.length; i++) {
            jumpers[i] = new Jumper();
        }
    }

    @Override
    public void run() {

        for (Jumper jumper : jumpers) {
            for (Block block : blocks) {
                if(jumper.getX() + jumper.getSize() >= block.getX() 
                        && jumper.getX() + jumper.getSize()<= block.getX() + block.getWidth() 
                        && jumper.getY() + jumper.getSize() >= block.getY() - block.getLength()){
                    jumper.setX(-40);
                    jumper.setY(-40);
                }
            }
        }
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move();
            blocks[i].draw();
        }
        for (int i = 0; i < jumpers.length; i++) {
            jumpers[i].jump();
            jumpers[i].draw();
        }
    }

    @Override
    public int isDone() {
        if (this.dc.isKeyPressed('f')) {
            return 1;
        }
        return -1;
    }

}
