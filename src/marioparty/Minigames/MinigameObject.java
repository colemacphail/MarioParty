/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

/**
 *
 * @author Jacob
 */
public abstract class MinigameObject {
    public double x, y;
    
    public double getX(){
    return this.x;
    }
    
    public double getY(){
    return this.y;
    }
    
    public abstract void init();
    
    public abstract void score();   
    
            
            
    
    
}
