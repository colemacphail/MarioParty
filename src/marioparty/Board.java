package marioparty;

import marioparty.Minigames.Minigame;
import DLibX.DConsole;
import Tiles.Tilesets;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

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
    private TurnState currentTurnState;
    private Tilesets tileset;
    private final int numOfPlayers = 1;
    private Character[] characters = new Character[this.numOfPlayers];
    private int playerTurn = 0;
    private Minigame selectedMinigame;
    private int cameraOffsetX = 0;
    private int cameraOffsetY = 0;
    private Random ranGen = new Random();
    private int currentRoll = 0;

    private Board() {
        this.currentGameState = GameState.INIT;

    }

    public void draw() {

    }

    public void update() {

        System.out.println(this.currentTurnState);
        switch (this.currentGameState) {
            case INIT:
                this.tileset = new Tilesets(Tilesets.BASIC);
                for (int i = 0; i < this.characters.length; i++) {
                    this.characters[i] = new Character(this.tileset.getSelectedTileset()[0].getX(), this.tileset.getSelectedTileset()[0].getY());
                }
                this.currentGameState = GameState.BOARD;
                this.currentTurnState = TurnState.ROLLING;
                break;

            case BOARD:
                this.tileset.draw();
                for (Character character : this.characters) {
                    character.draw();
                }
//                this.setCameraCenterPoint(this.characters[this.playerTurn].getX(), this.characters[this.playerTurn].getY());

                switch (currentTurnState) {
                    case ROLLING:
                        this.drawRollingDie();
                        if (dc.isKeyPressed(' ')) {
                            this.currentTurnState = TurnState.MOVING;
                            this.characters[playerTurn].setTargetTilePos((this.characters[playerTurn].getTilePos() + this.currentRoll) % this.tileset.getSelectedTileset().length);
                        }
                        break;

                    case MOVING:
                        this.drawCountDownDie();
                        if (this.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[this.characters[playerTurn].getTargetTile()])) {
                            this.characters[playerTurn].setTilePos(this.characters[playerTurn].getTargetTile());
                            this.currentTurnState = TurnState.END;
                        } else {
                            this.characters[playerTurn].moveToNextTile(this.tileset.getSelectedTileset()[(this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length]);
                            if (this.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[(this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length])) {
                                this.characters[playerTurn].setTilePos((this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length);
                            }
                        }
                        break;

                    case END:
                        this.currentTurnState = TurnState.ROLLING;
                        this.playerTurn = (this.playerTurn + 1) % (this.numOfPlayers);
                        break;

                }
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

    public void setCameraCenterPoint(int focusX, int focusY) {
        this.cameraOffsetX = focusX - this.dc.getWidth() / 2;
        this.cameraOffsetY = focusY - this.dc.getHeight() / 2;
    }

    public void drawRollingDie() {
        dc.setPaint(Color.BLACK);
        dc.drawRect(450, 150, 100, 100);
        this.currentRoll = ranGen.nextInt(10) + 1;
        dc.setFont(new Font("Comic Sans", Font.BOLD, 60));
        dc.drawString(this.currentRoll, 450, 135);
    }

    public void drawCountDownDie() {
        dc.setPaint(Color.BLACK);
        dc.drawRect(450, 150, 100, 100);
        this.currentRoll = ranGen.nextInt(10) + 1;
        dc.setFont(new Font("Comic Sans", Font.BOLD, 60));
        dc.drawString((this.characters[this.playerTurn].getTargetTile() - this.characters[this.playerTurn].getTilePos()) % this.tileset.getSelectedTileset().length, 450, 135);
    }

}
