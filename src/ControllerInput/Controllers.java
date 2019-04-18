/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerInput;

import marioparty.Constants;

/**
 *
 * @author arnav
 */
public class Controllers {
    
    private static Controllers instance;
    private final GamepadInput[] playerInputs = new GamepadInput[Constants.NUM_OF_PLAYERS];
    
    public static Controllers getInstance() {
        if (Controllers.instance == null) {
            Controllers.instance = new Controllers();
        }
        return Controllers.instance;
    }
    
    public Controllers () {
        for (int i = 0; i < this.playerInputs.length; i++) {
            this.playerInputs[i] = new GamepadInput(i);
        }
    }
        
    public GamepadInput[] getPlayerInput() {
        return playerInputs;
    }
}
