package marioparty.Minigames;

import java.awt.Color;
import java.util.Random;

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
    
    public int getFallspeed(){
        return this.fallspeed;
        }
    
    public void setY(int a){
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
        this.y = 20;
        this.width = 30;
        this.height = 10;
        this.color = new Color(110, 60, 10);
        this.movespeed = 4;
    }
}

public class CatchTheApple extends Minigame {

    private Apple[] apples = new Apple[12];
    private Net[] nets = new Net[]

    public CatchTheApple() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
        for (int i = 0; i < this.apples.length; i++) {
            this.apples[i] = new Apple();
        }

    }

    @Override
    public void run() {

        if (!isDone()) {
            for (int i = 0; i < this.apples.length; i++) {
                apples[i].setY(apples[i].getFallspeed());
                this.dc.fillEllipse(this.apples[i].getX(), this.apples[i].getY(), this.apples[i].getRad(), this.apples[i].getRad());
                
            }
        
        }
    }

    @Override
    public boolean isDone() {
        return this.dc.isKeyPressed(' ');
    }
}
