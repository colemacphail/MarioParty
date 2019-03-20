package marioparty;

import DLibX.DConsole;

/**
 *
 * @author Cole
 */
public class Board {
    
    private final DConsole dc;
    private GameState currentGameState;
    private Tile[] tileset;
    
    public Board(DConsole dc){
        this.dc = dc;
        this.currentGameState = GameState.BOARD;
    }
    
}
