package marioparty.Minigames;

import java.awt.Color;
import java.util.Random;
import marioparty.Board;

/**
 *
 * @author Jacob
 */
class Apple {

    private int x;
    private int y;
    private int radius;
    private Color color;
    private int fallspeed;
    private Random rg = new Random();

    public Apple() {
        this.fallspeed = 5;
        this.radius = 10;
        this.x = rg.nextInt(900);
        this.y = rg.nextInt(300) - 300;
        this.color = Color.RED;
    }

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

    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int movespeed;

    public Net() {
        this.x = 50;
        this.y = 580;
        this.width = 30;
        this.height = 10;
        this.color = new Color(110, 60, 10);
        this.movespeed = 6;
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

    public int getHeight() {
        return this.height;
    }

    public int getSpeed() {
        return this.movespeed;
    }

    public void changeX(int x) {
        this.x += x;
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

    public CatchTheApple() {
        super(MinigameType.FFA, 15000);
    }

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

    @Override
    public void run() {

        if (!isDone()) {
            for (int i = 0; i < this.apples.length; i++) { // make apples fall
                apples[i].setY(apples[i].getFallspeed());
                this.dc.fillEllipse(this.apples[i].getX(), this.apples[i].getY(), this.apples[i].getRad(), this.apples[i].getRad());

            }

            for (int i = 0; i < nets.length; i++) { //move around
                if (this.dc.isKeyPressed(68)) {
                    nets[i].changeX(nets[i].getSpeed());
                } else if (this.dc.isKeyPressed(65)) {
                    nets[i].changeX(-(nets[i].getSpeed()));
                }
                this.dc.fillRect(this.nets[i].getX(), nets[i].getY(), nets[i].getWidth(), nets[i].getHeight()); //draw nets
            }

        }
    }

    @Override
    public boolean isDone() {//TODO: have actual finishing condition
        return this.dc.isKeyPressed(' ');
    }
}
