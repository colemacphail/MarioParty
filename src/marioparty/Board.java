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
    private final Characters characters;
    private int turn = 0;
    private int maxTurns = 20;
    private int counter = 0;
    private boolean isPaused = false;
    private long pauseTime;

    //INITIALIZER
    private Board() {
        Constants.init();
        this.currentGameState = GameState.INIT;
        this.characters = Characters.getInstance();
    }

    public void update() {

        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (this.controllers.getControllerInput(i).actions().contains(InputAction.PAUSE)) {
                this.isPaused = !this.isPaused;
                if (this.isPaused) {
                    this.pauseTime = System.currentTimeMillis();
                } else {
                    if (this.currentGameState == GameState.MINIGAME) {
                        long unpauseTime = System.currentTimeMillis();
                        long pausedTime = unpauseTime - this.pauseTime;
                        this.selectedMinigame.addPausedTime(pausedTime);
                    }
                }
            }
        }

        if (this.isPaused) {
            this.dc.drawString("PAUSED", this.dc.getWidth() / 2, this.dc.getHeight() / 2);
        } else {
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
                        Characters.characters[i] = new Character(this.tileset.getSelectedTileset()[0].getX(), this.tileset.getSelectedTileset()[0].getY());
                    }
                    this.currentGameState = GameState.BOARD;
                    this.currentTurnState = TurnState.ROLLING;
                    break;

                case BOARD:
                    this.tileset.draw();//draw all tiles
                    this.dc.setPaint(Color.BLACK);
                    for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                        this.dc.drawString("★" + this.characters.getCharacter(i).getStars(),
                                this.dc.getWidth() / 8 * (i % 2 == 1 ? 7.5 : 1) - this.dc.getWidth() / 16,
                                this.dc.getHeight() / 8 * (i > 1 ? 5 : 1) - this.dc.getHeight() / 16);
                        this.dc.drawString("$" + this.characters.getCharacter(i).getCoins(),
                                this.dc.getWidth() / 8 * (i % 2 == 1 ? 7.5 : 1),
                                this.dc.getHeight() / 8 * (i > 1 ? 5 : 1) - this.dc.getHeight() / 16);
                        for (int j = 0; j < this.characters.getCharacter(i).getItems().size(); j++) {
                            this.dc.drawString("🍬",
                                    this.dc.getWidth() / 8 * (i % 2 == 1 ? 7.5 : 1) - this.dc.getWidth() / 16 + (20 * j + 1),
                                    this.dc.getHeight() / 8 * (i > 1 ? 5 : 1) - this.dc.getHeight() * 3 / 32);
                        }
                    }
                    for (int i = 0; i < Characters.getLength(); i++) {
                        Characters.characters[i].draw(); //draw all characters
                    }

                    switch (this.currentTurnState) {
                        case ITEM:

                            break;
                        case ROLLING:
                            this.drawRollingDie();//draw the die
                            if (this.dc.isKeyPressed(' ') || this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A)) {//if you press space, roll the die
                                this.currentTurnState = TurnState.MOVING;
                                //set selected tile to current position + whatever you rolled
                                Characters.characters[this.playerTurn].setTargetTilePos((Characters.characters[this.playerTurn].getTilePos() + this.currentRoll) % this.tileset.getSelectedTileset().length);
                            }
                            break;

                        case MOVING:
                            this.drawCountDownDie(); // draw the die as the player moves
                            if (Characters.characters[this.playerTurn].isWithinRange(this.tileset.getSelectedTileset()[Characters.characters[this.playerTurn].getTargetTile()])
                                    && Characters.characters[this.playerTurn].getTilePos() == Characters.characters[this.playerTurn].getTargetTile()
                                    && this.currentRoll == 0) { //if you're on the last tile, end turn
                                Characters.characters[this.playerTurn].setTilePos(Characters.characters[this.playerTurn].getTargetTile());
                                this.currentTurnState = TurnState.END;
                            } else { // if you're not on the last tile, move towards the next one
                                Characters.characters[this.playerTurn].moveToNextTile(this.tileset.getSelectedTileset()[(Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length]);
                                if (Characters.characters[this.playerTurn].isWithinRange(this.tileset.getSelectedTileset()[(Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length])) {
                                    this.currentRoll--;
                                    this.tileset.getSelectedTileset()[(Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length].passingEvent(Characters.characters[playerTurn]);
                                    Characters.characters[this.playerTurn].setTilePos((Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length);
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
                    for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                        Characters.characters[i].setMinigameScore(0);
                    }
                    this.selectedMinigame
                            = this.minigameBuilder.chooseMinigame(new MinigameType[]{MinigameType.FFA});//select the minigame
                    this.selectedMinigame.init();
                    this.selectedMinigame.setStartTime();
                    this.currentGameState = GameState.MINIGAME;
                    break;

                case MINIGAME:
                    this.selectedMinigame.run();
                    if (!this.selectedMinigame.isDone().isEmpty() || this.selectedMinigame.hasTimeoutOccurred()) { // if the minigame is done, finish

                        this.currentGameState = GameState.MINIGAME_END;

                        this.turn++;
                    }
                    break;

                case MINIGAME_END:
                    this.counter++;
                    this.selectedMinigame.drawHorizontalSplitscreen();
                    for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                        this.dc.drawString(this.characters.getCharacter(i).getCoins(), this.dc.getWidth() / 4,
                                this.dc.getHeight() / 4 * i + this.dc.getHeight() / 8);
                    }
                    if (this.counter == 75) {
                        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                            if (this.selectedMinigame.isDone().contains(Players.values()[i])) {
                                this.characters.getCharacter(i).changeCoins(10);
                            }
                        }
                    }

                    if (this.counter > 150) {
                        this.counter = 0;
                        if (this.turn >= this.maxTurns) {
                            this.currentGameState = GameState.END;
                        } else {
                            this.currentGameState = GameState.BOARD;
                        }
                    }
                    break;
                case END:
                    System.exit(1);
                    break;

            }
        }
    }
    //METHODS

    public GameState getCurrentGameState() {
        return this.currentGameState;
    }

    public void setCurrentGameState(GameState desiredGameState) {
        this.currentGameState = desiredGameState;
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

    public void changeCurrentRoll(int delta) {
        this.currentRoll += delta;
    }

    public Tilesets getSelectedTileset() {
        return this.tileset;
    }

    public int getPlayerTurn() {
        return this.playerTurn;
    }
}
