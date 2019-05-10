/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerInput;

import marioparty.Characters;

/**
 *
 * @author arnav
 */
public class Controllers {

    private static Controllers instance;

    private final GamepadInput[] playerInputs = new GamepadInput[4];

    public static Controllers getInstance() {
        if (Controllers.instance == null) {
            Controllers.instance = new Controllers();
        }
        return Controllers.instance;
    }

    public Controllers() {
        for (int i = 0; i < this.playerInputs.length; i++) {
            this.playerInputs[i] = new GamepadInput(i);
        }
    }

    public int getNumOfControllers() {
        int numOfControllers = 0;

        for (int i = 0; i < 4; i++) {
            if (this.playerInputs[i].getIsConnected()) {
                numOfControllers++;
            }
        }

        return numOfControllers;
    }

    public GamepadInput getControllerInput(int n) {
        try {
            return this.playerInputs[n];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not a valid controller!");
            return this.playerInputs[0];
        }
    }
}
