/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import marioparty.Constants;

/**
 *
 * @author Jacob
 */
public class MinigameObjects {
     private static MinigameObjects instance;

    public static MinigameObjects getInstance() {
        if (MinigameObjects.instance == null) {
            MinigameObjects.instance = new MinigameObjects();
        }
        return MinigameObjects.instance;
    }
    public static final MinigameObject[] minigameobjects = new MinigameObject[Constants.NUM_OF_PLAYERS];
    
    public static int getLength() {
        return Constants.NUM_OF_PLAYERS;
    }
    
    public MinigameObject objectAtI(int i) {
        return minigameobjects[i];
    }
}
