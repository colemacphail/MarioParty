package marioparty.Minigames;

import java.awt.Color;
import java.util.Random;
import marioparty.Characters;
import marioparty.Constants;

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

    public int getDia() {
        return this.radius;
    }

    public int getFallspeed() {
        return this.fallspeed;
    }

    public void setY(int a) {
        this.y = this.y + a;
    }
    public void remove(){
    this.x = -5;
    this.y = -5;
    this.fallspeed = 0;
    }
}

class Net extends MinigameObject {

    //VARIABLES
    private int width;
    private int height;
    private Color color;
    private int movespeed;

    //INIT
    public Net() {
        this.x = 30;
        this.y = 580;

        this.width = 30;
        this.height = 10;
        this.color = new Color(110, 60, 10);
        this.movespeed = 6;
    }

    //METHODS
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
    

}

public class CatchTheApple extends Minigame {

    Characters characters = Characters.getInstance();
    private Apple[] apples = new Apple[12];
    private Net[] nets = new Net[Constants.NUM_OF_PLAYERS];

    //SET TYPE
    public CatchTheApple() {
        super(MinigameType.FFA, 5000);
    }

    public void hitbox() {

        for (int i = 0; i < nets.length; i++) {
            for (Apple apple : apples) {
                if (apple.getX() + (apple.getDia() / 2) >= nets[i].getX() - (nets[i].getWidth() / 2)
                        && (apple.getX() - (apple.getDia() / 2) <= nets[i].getX() + nets[i].getWidth() / 2)
                        && apple.getY() >= nets[i].getY() - apple.getDia()
                        && apple.getY() <= nets[i].getY()) {
                    characters.characterAtI(i).setMinigameScore(100);
                    apple.remove();
                    System.out.println(characters.characterAtI(i).getMinigameScore());
                }
            }
        }
    }

    //SETUP
    @Override
    public void init() {

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

        for (Apple apple : this.apples) {
            apple.setY(apple.getFallspeed());
            this.dc.fillEllipse(apple.getX(), apple.getY(), apple.getDia(), apple.getDia());
        }

        for (Net net : nets) {
            if (this.dc.isKeyPressed(68)) {
                net.changeX(net.getSpeed());
            } else if (this.dc.isKeyPressed(65)) {
                net.changeX(-(net.getSpeed()));
            }
            this.dc.fillRect(net.getX(), net.getY(), net.getWidth(), net.getHeight());
        }

    }

    //END CLAUSE
    @Override
    public int isDone() {//TODO: have actual finishing condition
        if (this.dc.isKeyPressed(' ')) {
            return 1;
        }
        return -1;
    }
}
