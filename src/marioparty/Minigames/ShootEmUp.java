/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.GamepadInput;
import ControllerInput.InputAction;
import DLibX.DConsole;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import marioparty.Characters;
import marioparty.Console;
import marioparty.Constants;

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
    private DConsole dc = Console.getInstance();
    
    public Target() {
        this.diameter = 50;
        this.x = rg.nextInt(850) + 50;
        this.y = rg.nextInt(550) + 50;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getDiameter() {
        return this.diameter;
    }
    
    public void draw() {
        
        this.dc.setPaint(Color.BLACK);
        this.dc.fillEllipse(this.x, this.y, this.diameter, this.diameter);
        
    }
}

class Crosshair extends MinigameObject {
    
    private int x;
    private int y;
    private DConsole dc;
    private Color color;
    private int diameter;
    
    protected Crosshair(Color color) {
        this.dc = Console.getInstance();
        this.x = this.dc.getWidth() / 2;
        this.y = this.dc.getHeight() / 2;
        this.diameter = 20;
        this.color = color;
    }
    
    @Override
    protected void draw() {
        this.dc.setPaint(this.color);
        this.dc.drawEllipse(this.x, this.y, this.diameter, this.diameter);
    }
    
    protected void move(float x, float y) {
        this.x += (int) (x * 10);
        this.y += (int) (y * 10);
    }
    
}

public class ShootEmUp extends Minigame {
    
    private ArrayList<Target> targets = new ArrayList<>();
    private Crosshair[] crosshairs = new Crosshair[Constants.NUM_OF_PLAYERS];
    private Controllers controllers;
    

    public ShootEmUp() {
        super(MinigameType.FFA, 10000);
    }
    
    @Override
    public void init() {
        this.controllers = Controllers.getInstance();
        
        for (int i = 0; i < 10; i++) {
            this.targets.add(new Target());
        }
        
        for (int i = 0; i < this.crosshairs.length; i++) {
            switch (i) {
                case 0:
                    this.crosshairs[i] = new Crosshair(Color.RED);
                    break;
                case 1:
                    this.crosshairs[i] = new Crosshair(Color.BLUE);
                    break;
                case 2:
                    this.crosshairs[i] = new Crosshair(Color.GREEN);
                    break;
                case 3:
                    this.crosshairs[i] = new Crosshair(Color.YELLOW);
                    break;
                
            }
        }
    }
    
    @Override
    public void run() {
        for (Target target : this.targets) {
            target.draw();
        }
        
        for (int i = 0; i < this.crosshairs.length; i++) {
            GamepadInput playerIn = this.controllers.getControllerInput(i);
            this.crosshairs[i].draw();
            this.crosshairs[i].move(playerIn.getLeftStickX(), playerIn.getLeftStickY());
            if (playerIn.actions().contains(InputAction.A)) {
                for (Target target : this.targets) {
                    double distance = Math.sqrt(Math.pow(this.crosshairs[i].getX() - target.getX(), 2) + Math.pow(this.crosshairs[i].getY() - target.getY(), 2));
                    if (distance < target.getDiameter() / 2) {
                        this.targets.remove(target);
                        Characters.getInstance().characterAtI(i).changeMinigameScore(1);
                    }
                }
            }
        }
    }
    
    @Override
    public int isDone() {//TODO: have actual finishing condition
        int winningPlayer = -1;
        if (this.targets.isEmpty()) {
            for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                if (Characters.getInstance().characterAtI(i).getMinigameScore() > (winningPlayer >= 0 ? Characters.getInstance().characterAtI(winningPlayer).getMinigameScore() : -1)) {
                    winningPlayer = i;
                }
            }
            for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                if (Characters.getInstance().characterAtI(i).getMinigameScore() == (winningPlayer >= 0 ? Characters.getInstance().characterAtI(winningPlayer).getMinigameScore() : -1)) {
                    winningPlayer += i * 10;
                }
            }
        }
        
        return winningPlayer;
    }
}
