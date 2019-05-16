/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import ControllerInput.Controllers;

/**
 *
 * @author arnav
 */
public class Constants {

    public static int NUM_OF_PLAYERS = 1;

    public static void init() {
        Constants.NUM_OF_PLAYERS = Math.max(Controllers.getInstance().getNumOfControllers(), 1);
    }
}
