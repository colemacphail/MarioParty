package marioparty.Minigames;

import java.awt.Color;
import java.util.Random;
import marioparty.Board;

/**
 *
 * @author Jacob
 */
class Apple {

    //VARIABLES
    private int x;
    private int y;
    private int radius;
    private Color color;
    private int fallspeed;
    private Random rg = new Random();

    //INIT
    public Apple() {
        this.fallspeed = 5;
        this.radius = 10;
        this.x = rg.nextInt(900);
        this.y = rg.nextInt(300) - 300;
        this.color = Color.RED;
    }

    //METHODS
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRad() {
        return this.radius;
    }

    public int getFallspeed() {
        return this.fallspeed;
    }

    public void setY(int a) {
        this.y = this.y + a;
    }

}

class Net {

    //VARIABLES
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int movespeed;

    //INIT
    public Net() {
        this.x = 50;
        this.y = 580;
        this.width = 30;
        this.height = 10;
        this.color = new Color(110, 60, 10);
        this.movespeed = 6;
    }

    //METHODS
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getSpeed() {
        return this.movespeed;
    }

    public void setX(int a) {
        this.x += a;
    }

    public boolean hitbox() {
        //TODO
        return true;
    }
}

public class CatchTheApple extends Minigame {

    private Board board = Board.getInstance();
    private Apple[] apples = new Apple[12];
    private Net[] nets = new Net[board.getNumOfPlayers()];

    //SET TYPE
    public CatchTheApple() {
        super(MinigameType.FFA, 15000);
    }

    //SETUP
    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
        for (int i = 0; i < this.apples.length; i++) {
            this.apples[i] = new Apple();
        }
        for (int i = 0; i < nets.length; i++) {
            this.nets[i] = new Net();
        }

    }

    //UPDATE CYCLE
    @Override
    public void run() {

        if (!isDone()) {
            for (Apple apple : this.apples) {
                apple.setY(apple.getFallspeed());
                this.dc.fillEllipse(apple.getX(), apple.getY(), apple.getRad(), apple.getRad());
            }

            for (Net net : nets) {
                if (this.dc.isKeyPressed(68)) {
                    net.setX(net.getSpeed());
                } else if (this.dc.isKeyPressed(65)) {
                    net.setX(-(net.getSpeed()));
                }
                this.dc.fillRect(net.getX(), net.getY(), net.getWidth(), net.getHeight());
            }

        }
    }

    //END CLAUSE
    @Override
    public boolean isDone() {
        return this.dc.isKeyPressed(' ');
    }
}
