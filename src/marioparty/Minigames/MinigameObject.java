/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import ControllerInput.GamepadInput;
import DLibX.DConsole;
import marioparty.Console;

/**
 *
 * @author Jacob
 */
public abstract class MinigameObject {

    protected double x;
    protected double y;
    protected long time;
    protected int score;
    
    private GamepadInput controller;
    protected final DConsole dc = Console.getInstance();

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    
    public long getTime(){
        return this.time;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void setX(double destination){
    this.x = destination;
    }
    
    public void setY(double destination){
    this.y = destination; //deep space presented by the boeing company
    }
    
    public void updateTime(){
    this.time = System.currentTimeMillis();
    }

    protected abstract void draw();
}
