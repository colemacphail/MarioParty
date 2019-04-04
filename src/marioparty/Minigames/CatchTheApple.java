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
        this.radius = 5;
        this.x = rg.nextInt(900);
        this.y = rg.nextInt(300)- 300;
        this.color = Color.RED;
    }
    
    public int getX(){
    return this.x;
    }
    
    public int getY(){
    return this.y;
    }
    public int getRad(){
    return this.radius;
    }
    
}

class Net{
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int movespeed;
   
    
    public Net(){
    this.x = 50;
    this.y = 20;
    this.width = 30;
    this.height = 10;
    this.color = new Color(110,60,10);
    this.movespeed = 4;
    }
}

public class CatchTheApple extends Minigame {

    private Apple[] apples = new Apple[12];
    public CatchTheApple() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
        for (int i = 0; i < apples.length; i++){
        apples[i] = new Apple();
        }
        

    }

    @Override
    public void run() {

        if (!isDone()) {
            for (int i = 0; i< apples.length; i++){
            dc.fillEllipse(apples[i].getX(), apples[i].getY(), apples[i].getRad(), apples[i].getRad());
            }
        }
    }

    @Override
    public boolean isDone() {
        return this.dc.isKeyPressed(' ');
    }
}
