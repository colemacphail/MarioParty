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

    private static Board instance;

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    private final DConsole dc = new DConsole();
    private GameState currentGameState;
    private Tile[] tileset;
    private int numOfPlayers = 0;
    private int playerTurn = 0;

    private Board() {
        this.currentGameState = GameState.BOARD;
    }

    public void draw() {

    }

    public void update() {
        switch (currentGameState) {
            case INIT:
                break;
            case BOARD:
                break;
            case MINIGAME_INIT:
                break;
            case MINIGAME:
                break;
            case END:
                break;
        }
    }

}
