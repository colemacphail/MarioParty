/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Jacob
 */
class Target {

    private int x;
    private int y;
    private int diameter;
    private boolean visible;
    private Color color;
    private Random rg = new Random();
    
    public Target(){
    this.visible = false;
    this.diameter = 50;
    this.x = rg.nextInt(850)+ 50;
    this.y = rg.nextInt(550) + 50;
    this.color = color;
    }
}

public class ShootEmUp extends Minigame {
    Target[] targets = new Target[10];

    public ShootEmUp() {
        super(MinigameType.FFA, 10000);
    }

    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
        for(int i = 0; i < targets.length; i++){
            targets[i] = new Target();
        }
    }

    @Override
    public void run() {
        this.dc.drawString("SHOOT EM UP", 450, 20);
    }

    @Override
    public int isDone() {//TODO: have actual finishing condition
        if (this.dc.isKeyPressed(' ')) {
            return 1;
        }
        return -1;
    }
}