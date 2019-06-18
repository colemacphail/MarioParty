/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import ControllerInput.Controllers;
import java.awt.BasicStroke;
import java.awt.Font;

/**
 *
 * @author arnav
 */
public class Constants {

    public static int NUM_OF_PLAYERS = 1;
    public static final Font TILES_TEXT = new Font("TILES_TEXT", Font.BOLD, 18);
    public static final Font ROLLING_FONT = new Font("Comic Sans", Font.BOLD, 60);
    public static final BasicStroke CHARACTER_STROKE = new BasicStroke(3);
    public static final BasicStroke REGULAR_STROKE = new BasicStroke(1);

    public static void init() {
        Constants.NUM_OF_PLAYERS = Math.max(Controllers.getInstance().getNumOfControllers(), 1);
    }
}
