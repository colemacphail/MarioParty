package marioparty;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import DLibX.DConsole;
import Tiles.Tilesets;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import marioparty.Minigames.Minigame;
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
    //VARIABLES
    private final DConsole dc = Console.getInstance();
    private GameState currentGameState;
    private TurnState currentTurnState;
    private Tilesets tileset;
    private final Controllers controllers = Controllers.getInstance();
    private int playerTurn = 0;
    private Minigame selectedMinigame;
    private int cameraOffsetX = 0;
    private int cameraOffsetY = 0;
    private final Random ranGen = new Random();
    private int currentRoll = 0;
    private MinigameBuilder minigameBuilder;
    private Characters characters;

    //INITIALIZER
    private Board() {
        this.currentGameState = GameState.INIT;
        characters = Characters.getInstance();
    }

    public void update() {
        //TURN STATES
        switch (this.currentGameState) {
            case MENU: //TODO: make an actual menu
                // dc.drawImage("menu.jpg",0,0);
                //JPanel p = new JPanel();
                //p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

                break;
            case INIT:
                this.minigameBuilder = MinigameBuilder.getInstance();
                this.tileset = new Tilesets(Tilesets.BASIC); //create the tileset //TODO: make a tileset selector

                for (int i = 0; i < Characters.getLength(); i++) {//create all characters 
                    Characters.characters[i] = new Character(this.tileset.getSelectedTileset()[0].getX(), this.tileset.getSelectedTileset()[0].getY(), i);
                }
                this.currentGameState = GameState.BOARD;
                this.currentTurnState = TurnState.ROLLING;
                break;

            case BOARD:
                this.tileset.draw();//draw all tiles
                for (int i = 0; i < Characters.getLength(); i++) {
                    Characters.characters[i].draw(); //draw all characters
                }

                switch (currentTurnState) {
                    case ROLLING:
                        this.drawRollingDie();//draw the die
                        if (this.dc.isKeyPressed(' ') || this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A)) {//if you press space, roll the die
                            this.currentTurnState = TurnState.MOVING;
                            //set selected tile to current position + whatever you rolled
                            Characters.characters[playerTurn].setTargetTilePos((Characters.characters[playerTurn].getTilePos() + this.currentRoll) % this.tileset.getSelectedTileset().length);
                        }
                        break;

                    case MOVING:
                        this.drawCountDownDie(); // draw the die as the player moves
                        if (Characters.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[Characters.characters[playerTurn].getTargetTile()])
                                && Characters.characters[playerTurn].getTilePos() == Characters.characters[playerTurn].getTargetTile()
                                && this.currentRoll == 0) { //if you're on the last tile, end turn
                            Characters.characters[playerTurn].setTilePos(Characters.characters[playerTurn].getTargetTile());
                            this.currentTurnState = TurnState.END;
                        } else { // if you're not on the last tile, move towards the next one
                            Characters.characters[playerTurn].moveToNextTile(this.tileset.getSelectedTileset()[(Characters.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length]);
                            if (Characters.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[(Characters.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length])) {
                                this.currentRoll--;
                                Characters.characters[playerTurn].setTilePos((Characters.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length);
                            }
                        }
                        break;

                    case END://if the turn is over, trigger the tile and go to the next turn
                        this.tileset.getSelectedTileset()[Characters.characters[this.playerTurn].getTargetTile()].triggerEvent(Characters.characters[this.playerTurn]);
                        this.currentTurnState = TurnState.ROLLING;
                        this.playerTurn = (this.playerTurn + 1) % (Constants.NUM_OF_PLAYERS);
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
                if (this.selectedMinigame.isDone() != -1 || this.selectedMinigame.hasTimeoutOccurred()) { // if the minigame is done, finish
                    this.currentGameState = GameState.BOARD;
                    if (this.selectedMinigame.isDone() != -1) {
                        try {
                            Characters.characters[this.selectedMinigame.isDone()].changeCoins(5);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Not a valid winner!");
                        }
                    }
                }
                break;

            case END:
                this.dc.dispose();
                System.exit(1);
                break;

        }
    }

    //METHODS
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
