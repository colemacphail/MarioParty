/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import DLibX.DConsole;

/**
 *
 * @author Cole
 */
public class MarioParty {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Board game = Board.getInstance();
        
        while (true) {
            game.update();
            game.draw();
            DConsole.pause(20);
        }
    }
    
}
