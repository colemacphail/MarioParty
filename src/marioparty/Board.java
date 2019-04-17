package marioparty;

import ControllerInput.GamepadInput;
import marioparty.Minigames.Minigame;
import DLibX.DConsole;
import Tiles.Tilesets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import static java.awt.SystemColor.menu;
import java.util.Random;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import marioparty.Minigames.MinigameBuilder;
import marioparty.Minigames.MinigameType;

/**
 *
 * @author Cole
 */
public class Board {

    private static Board instance;

    public static Board getInstance() {
        if (Board.instance == null) {
            Board.instance = new Board();
        }
        return Board.instance;
    }

    private final DConsole dc = Console.getInstance();
    private GameState currentGameState;
    private TurnState currentTurnState;
    private Tilesets tileset;
    private final int numOfPlayers = 1;
    private final Character[] characters = new Character[this.numOfPlayers];
    private int playerTurn = 0;
    private Minigame selectedMinigame;
    private int cameraOffsetX = 0;
    private int cameraOffsetY = 0;
    private final Random ranGen = new Random();
    private int currentRoll = 0;
    private MinigameBuilder minigameBuilder;
    private final GamepadInput[] playerInputs = new GamepadInput[this.numOfPlayers];

    private Board() {
        this.currentGameState = GameState.INIT;

        for (int i = 0; i < this.playerInputs.length; i++) { // create all controllers
            this.playerInputs[i] = new GamepadInput(i);
        }

    }

    public void update() {

        switch (this.currentGameState) {
            case MENU: //TODO: make an actual menu
                // dc.drawImage("menu.jpg",0,0);
                //JPanel p = new JPanel();
                //p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

                break;
            case INIT:
                this.minigameBuilder = MinigameBuilder.getInstance();
                this.tileset = new Tilesets(Tilesets.BASIC); //create the tileset //TODO: make a tileset selector
                for (int i = 0; i < this.characters.length; i++) {//create all characters 
                    this.characters[i] = new Character(this.tileset.getSelectedTileset()[0].getX(), this.tileset.getSelectedTileset()[0].getY());
                }
                this.currentGameState = GameState.BOARD;
                this.currentTurnState = TurnState.ROLLING;
                break;

            case BOARD:
                this.tileset.draw();//draw all tiles
                for (Character character : this.characters) {
                    character.draw();//draw all characters
                }

                switch (currentTurnState) {
                    case ROLLING:
                        this.drawRollingDie();//draw the die
                        if (dc.isKeyPressed(' ')) {//if you press space, roll the die
                            this.currentTurnState = TurnState.MOVING;
                            //set selected tile to current position + whatever you rolled
                            this.characters[playerTurn].setTargetTilePos((this.characters[playerTurn].getTilePos() + this.currentRoll) % this.tileset.getSelectedTileset().length);
                        }
                        break;

                    case MOVING:
                        this.drawCountDownDie(); // draw the die as the player moves
                        if (this.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[this.characters[playerTurn].getTargetTile()])
                                && this.characters[playerTurn].getTilePos() == this.characters[playerTurn].getTargetTile()
                                && this.currentRoll == 0) { //if you're on the last tile, end turn
                            this.characters[playerTurn].setTilePos(this.characters[playerTurn].getTargetTile());
                            this.currentTurnState = TurnState.END;
                        } else { // if you're not on the last tile, move towards the next one
                            this.characters[playerTurn].moveToNextTile(this.tileset.getSelectedTileset()[(this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length]);
                            if (this.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[(this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length])) {
                                this.currentRoll--;
                                this.characters[playerTurn].setTilePos((this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length);
                            }
                        }
                        break;

                    case END://if the turn is over, trigger the tile and go to the next turn
                        this.tileset.getSelectedTileset()[this.characters[this.playerTurn].getTargetTile()].triggerEvent(this.characters[this.playerTurn]);
                        this.currentTurnState = TurnState.ROLLING;
                        this.playerTurn = (this.playerTurn + 1) % (this.numOfPlayers);
                        if (this.playerTurn == 0) {
                            this.currentGameState = GameState.MINIGAME_INIT;//if finished turn order, play a minigame
                        }
                        break;

                }
                break;

            case MINIGAME_INIT:
                this.selectedMinigame
                        = this.minigameBuilder.chooseMinigame(new MinigameType[]{MinigameType.FFA});//select the minigame
                this.selectedMinigame.init();
                this.currentGameState = GameState.MINIGAME;
                break;

            case MINIGAME:
                this.selectedMinigame.run();
                if (this.selectedMinigame.isDone() || this.selectedMinigame.hasTimeoutOccurred()) { // if the minigame is done, finish
                    this.currentGameState = GameState.BOARD;
                }
                break;

            case END:
                this.dc.dispose();
                System.exit(1);
                break;

        }
    }

    public int getCameraOffsetX() {
        return this.cameraOffsetX;
    }

    public int getCameraOffsetY() {
        return this.cameraOffsetY;
    }

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public void setCameraCenterPoint(int focusX, int focusY) {
        this.cameraOffsetX = focusX - this.dc.getWidth() / 2;
        this.cameraOffsetY = focusY - this.dc.getHeight() / 2;
    }

    public void drawRollingDie() {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawRect(450, 150, 100, 100);
        this.currentRoll = ranGen.nextInt(10) + 1;
        this.dc.setFont(new Font("Comic Sans", Font.BOLD, 60));
        this.dc.drawString(this.currentRoll, 450, 135);
    }

    public void drawCountDownDie() {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawRect(450, 150, 100, 100);
        this.dc.setFont(new Font("Comic Sans", Font.BOLD, 60));
        this.dc.drawString(this.currentRoll, 450, 135);
    }

}
