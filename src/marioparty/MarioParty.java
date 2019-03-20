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
