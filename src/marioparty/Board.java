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
public class Board {
    
    private final DConsole dc;
    private GameState currentGameState;
    private Tile[] tileset;
    
    public Board(DConsole dc){
        this.dc = dc;
        this.currentGameState = GameState.BOARD;
    }
    
}
