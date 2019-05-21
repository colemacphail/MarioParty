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
    private GamepadInput controller;
    protected final DConsole dc = Console.getInstance();

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    
    public void setX(double x){
    this.x = x;
    }
    
    public void setY(double y){
    this.y = y;
    //deep space presented by the boeing company
    }

    protected abstract void draw();
}
