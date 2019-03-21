package marioparty;

import Tiles.Tile;
import marioparty.Minigames.Minigame;
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

    private final DConsole dc = Console.getInstance();
    private GameState currentGameState;
    private Tile[] tileset;
    private int numOfPlayers = 0;
    private int playerTurn = 0;
    private Minigame selectedMinigame;
    private int cameraOffsetX = 0;
    private int cameraOffsetY = 0;

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
                this.selectedMinigame.init();
                this.currentGameState = GameState.MINIGAME;
                break;
            case MINIGAME:
                this.selectedMinigame.run();
                if (this.selectedMinigame.isDone() || this.selectedMinigame.hasTimeoutOccurred()) {
                    this.currentGameState = GameState.BOARD;
                }
                break;
            case END:
                break;
        }
    }

    public int getCameraOffsetX() {
        return this.cameraOffsetX;
    }
    
    public int getCameraOffsetY() {
        return this.cameraOffsetY;
    }
    
    public void setCameraCenterPoint(int focusX, int focusY){
        this.cameraOffsetX = focusX - this.dc.getWidth() / 2;
        this.cameraOffsetY = focusY - this.dc.getHeight() / 2;
    }

}
