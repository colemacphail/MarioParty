/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import DLibX.DConsole;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import marioparty.Console;
import marioparty.Constants;
import marioparty.Players;

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

        this.y = 560;
        this.width = 20;
        this.length = 80;
        this.movespeed = 5;
    }

    public void setX(int x) {
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
        this.dc.fillRect(this.x, this.y, this.width, this.length);
    }
}

class Jumper extends MinigameObject {

    private int size;
    private Color color;
    private double yChange;
    private boolean pressed;
    private boolean alive;

    public Jumper() {
        this.x = 30;
        this.y = 585;
        this.size = 30;
        this.color = new Color(145, 30, 30);
        this.yChange = 0;
        this.pressed = false;
        this.time = System.currentTimeMillis();
        this.alive = true;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void dead() {
        this.alive = false;
    }

    @Override
    protected void draw() {
        dc.setPaint(this.color);
        dc.fillRect(this.x, this.y, this.size, this.size);
    }

    public void jump() {

        if (!pressed) {
            this.pressed = true;
            this.yChange = -10;
        }

    }

    public void fall() {

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
    Controllers controllers = Controllers.getInstance();
    private long startTime = System.currentTimeMillis();

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
            if (jumper.isAlive()) {
                jumper.updateTime();
                jumper.setScore((int) (startTime - jumper.getTime()));
            }

            for (Block block : blocks) {

                if ((jumper.getX() + (jumper.getSize() / 2) >= block.getX() - (block.getWidth() / 2)
                        && jumper.getX() - (jumper.getSize() / 2) <= block.getX() + (block.getWidth() / 2)
                        && jumper.getY() + (jumper.getSize() / 2) >= block.getY() - (block.getLength() / 2))) {
                    System.out.println("hitting");
                    System.out.println(jumper.getX() + jumper.getSize() / 2 + "," + (block.getX() - block.getWidth() / 2));
                    System.out.println(jumper.getY() + jumper.getSize() / 2 + "," + (block.getY() - block.getLength() / 2));
                    jumper.setX(10000);
                    jumper.dead();

                }
            }
        }
        for (Block block : blocks) {
            block.move();
            block.draw();
        }
        for (int i = 0; i < jumpers.length; i++) { //TODO: Controller support
            if (this.controllers.getControllerInput(i).actions().contains(InputAction.A)) {
                jumpers[i].jump();
            }
            jumpers[i].fall();
            jumpers[i].draw();
        }
    }

    @Override
    public boolean hasTimeoutOccurred() {
        boolean isAllDead = true;

        for (int i = 0; i < this.jumpers.length; i++) {
            if (this.jumpers[i].isAlive()) {
                isAllDead = false;
            }
        }

        return super.hasTimeoutOccurred() || isAllDead;
    }

    @Override
    public Set isDone() {
        Set<Players> winningPlayers = new HashSet<>();

        if (this.hasTimeoutOccurred()) {
            for (int i = 0; i < this.jumpers.length; i++) {
                if (jumpers[i].isAlive()) {
                    winningPlayers.add(Players.values()[i]);
                }
            }
        }
        return winningPlayers;
    }

}
