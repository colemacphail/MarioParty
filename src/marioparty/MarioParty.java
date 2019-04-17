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
//        work in progress        
//        UI menu = new UI(); 
//        menu.init();
        run();
    }

    public static void run() {
        System.out.println("Starting game...");
        Board game = Board.getInstance();
        DConsole dc = Console.getInstance();
        
        dc.setOrigin(DConsole.ORIGIN_CENTER);

        while (true) {
            dc.clear();
            game.update();
            dc.redraw();
            DConsole.pause(20);
        }
    }

}
