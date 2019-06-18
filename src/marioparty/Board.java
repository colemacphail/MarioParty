package marioparty;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import DLibX.DConsole;
import Tiles.Tilesets;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import marioparty.Items.Item;
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
    private RollState currentRollState;
    private int selectedItem = -1;
    private int totalRoll = 0;
    private int timesRolled = 0;
    private boolean isButtonPressed = false;

    //INITIALIZER
    private Board() {
        Constants.init();
        this.currentGameState = GameState.INIT;
        this.currentRollState = RollState.DEFAULT;
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
                case INIT:
                    this.minigameBuilder = MinigameBuilder.getInstance();
                    this.tileset = new Tilesets(Tilesets.WORKING); //create the tileset //TODO: make a tileset selector

                    for (int i = 0; i < Characters.getLength(); i++) {//create all characters 
                        Characters.characters[i] = new Character(this.tileset.getSelectedTileset()[0].getX(), this.tileset.getSelectedTileset()[0].getY());
                    }
                    this.characters.setCharacterNames();
                    this.currentGameState = GameState.BOARD;
                    this.currentTurnState = TurnState.ITEM;
                    break;

                case BOARD:
                    this.tileset.draw();//draw all tiles
                    this.dc.setPaint(Color.BLACK);
                    for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                        this.dc.drawString("★" + this.characters.getCharacter(i).getStars(),
                                this.dc.getWidth() / 8 * (i % 2 == 1 ? 7.5 : 1) - this.dc.getWidth() / 16,
                                this.dc.getHeight() / 8 * (i > 1 ? 7 : 1) - this.dc.getHeight() / 16);
                        this.dc.drawString("$" + this.characters.getCharacter(i).getCoins(),
                                this.dc.getWidth() / 8 * (i % 2 == 1 ? 7.5 : 1),
                                this.dc.getHeight() / 8 * (i > 1 ? 7 : 1) - this.dc.getHeight() / 16);
                        for (int j = 0; j < this.characters.getCharacter(i).getItems().size(); j++) {
                            this.dc.drawString("🍬",
                                    this.dc.getWidth() / 8 * (i % 2 == 1 ? 7.5 : 1) - this.dc.getWidth() / 16 + (20 * j + 1),
                                    this.dc.getHeight() / 8 * (i > 1 ? 7 : 1) - this.dc.getHeight() * 3 / 32);
                        }
                    }
                    for (int i = 0; i < Characters.getLength(); i++) {
                        Characters.characters[i].draw(); //draw all characters
                    }

                    switch (this.currentTurnState) {
                        case ITEM:
                            ArrayList<Item> items = Characters.characters[playerTurn].getItems();
                            for (int i = 0; i < items.size() + 1; i++) {
                                this.dc.setPaint(Color.BLACK);
                                if (i == this.selectedItem + 1) {
                                    this.dc.setPaint(Color.RED);
                                }
                                this.dc.drawRect(this.dc.getWidth() / 2 - (items.size() * 38) + i * 75, this.dc.getHeight() / 4, 50, 50);
                                if (i < items.size()) {
                                    items.get(i).draw(this.dc.getWidth() / 2 - (items.size() * 38) + (i + 1) * 75, this.dc.getHeight() / 4 - 5);
                                }
                            }
                            if (this.dc.getKeyPress(37) || this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.MOVE_LEFT)) {
                                this.selectedItem--;
                                this.selectedItem = Math.max(this.selectedItem, -1);
                            }
                            if (this.dc.getKeyPress(39) || this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.MOVE_RIGHT)) {
                                this.selectedItem++;
                                this.selectedItem = Math.min(this.selectedItem, items.size() - 1);
                            }
                            if (this.dc.getKeyPress(' ') || this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A)) {
                                if (this.selectedItem >= 0) {
                                    items.get(selectedItem).triggerEvent();
                                    items.remove(this.selectedItem);
                                }
                                this.selectedItem = -1;
                                this.currentTurnState = TurnState.ROLLING;
                                this.isButtonPressed = true;
                            }
                            break;
                        case ROLLING:
                            switch (this.currentRollState) {
                                case DEFAULT:

                                    this.drawRollingDie();//draw the die
                                    if (this.dc.getKeyPress(' ') || (this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A) && !this.isButtonPressed)) {
                                        this.totalRoll += this.currentRoll;
                                        this.currentTurnState = TurnState.MOVING;
                                        //set selected tile to current position + whatever you rolled
                                        Characters.characters[this.playerTurn].setTargetTilePos((Characters.characters[this.playerTurn].getTilePos() + this.totalRoll) % this.tileset.getSelectedTileset().length);
                                    } else if (!this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A)) {
                                        this.isButtonPressed = false;
                                    }

                                    break;

                                case TWICE:

                                    this.drawRollingDie();
                                    if (this.dc.getKeyPress(' ') || (this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A) && !this.isButtonPressed)) {
                                        this.isButtonPressed = true;
                                        this.totalRoll += this.currentRoll;
                                        this.timesRolled++;
                                    } else if (!this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A)) {
                                        this.isButtonPressed = false;
                                    }

                                    if (this.timesRolled == 1) {
                                        this.dc.setPaint(Color.BLACK);
                                        this.dc.drawRect(450, 60, 50, 50);
                                        this.dc.setFont(new Font("Comic Sans", Font.BOLD, 40));
                                        this.dc.drawString(this.totalRoll, 450, 50);
                                    }

                                    if (this.timesRolled == 2) {
                                        Characters.characters[this.playerTurn].setTargetTilePos((Characters.characters[this.playerTurn].getTilePos() + this.totalRoll) % this.tileset.getSelectedTileset().length);
                                        this.currentTurnState = TurnState.MOVING;
                                        this.timesRolled = 0;
                                    }

                                    break;

                                case THRICE:

                                    this.drawRollingDie();
                                    if (this.dc.getKeyPress(' ') || (this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A) && !this.isButtonPressed)) {
                                        this.isButtonPressed = true;
                                        this.totalRoll += this.currentRoll;
                                        this.timesRolled++;
                                    } else if (!this.controllers.getControllerInput(this.playerTurn).actions().contains(InputAction.A)) {
                                        this.isButtonPressed = false;
                                    }

                                    if (this.timesRolled == 1 || this.timesRolled == 2) {
                                        this.dc.setPaint(Color.BLACK);
                                        this.dc.drawRect(450, 60, 50, 50);
                                        this.dc.setFont(new Font("Comic Sans", Font.BOLD, 40));
                                        this.dc.drawString(this.totalRoll, 450, 50);
                                    }

                                    if (this.timesRolled == 3) {
                                        Characters.characters[this.playerTurn].setTargetTilePos((Characters.characters[this.playerTurn].getTilePos() + this.totalRoll) % this.tileset.getSelectedTileset().length);
                                        this.currentTurnState = TurnState.MOVING;
                                        this.timesRolled = 0;
                                    }

                                    break;
                            }
                            break;

                        case MOVING:
                            this.currentRollState = RollState.DEFAULT;
                            this.drawCountDownDie(); // draw the die as the player moves
                            if (Characters.characters[this.playerTurn].isWithinRange(this.tileset.getSelectedTileset()[Characters.characters[this.playerTurn].getTargetTile()])
                                    && Characters.characters[this.playerTurn].getTilePos() == Characters.characters[this.playerTurn].getTargetTile()
                                    && this.totalRoll == 0) { //if you're on the last tile, end turn
                                Characters.characters[this.playerTurn].setTilePos(Characters.characters[this.playerTurn].getTargetTile());
                                this.currentTurnState = TurnState.END;
                            } else { // if you're not on the last tile, move towards the next one
                                Characters.characters[this.playerTurn].moveToNextTile(this.tileset.getSelectedTileset()[(Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length]);
                                if (Characters.characters[this.playerTurn].isWithinRange(this.tileset.getSelectedTileset()[(Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length])) {
                                    this.totalRoll--;
                                    this.tileset.getSelectedTileset()[(Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length].passingEvent(Characters.characters[playerTurn]);
                                    Characters.characters[this.playerTurn].setTilePos((Characters.characters[this.playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length);
                                }
                            }
                            break;

                        case END://if the turn is over, trigger the tile and go to the next turn
                            this.tileset.getSelectedTileset()[Characters.characters[this.playerTurn].getTargetTile()].triggerEvent(Characters.characters[this.playerTurn]);
                            this.currentTurnState = TurnState.ITEM;
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
        this.dc.drawRect(this.dc.getWidth() / 2, this.dc.getHeight() / 4, 100, 100);
        this.currentRoll = ranGen.nextInt(10) + 1;
        this.dc.setFont(Constants.ROLLING_FONT);
        this.dc.drawString(this.currentRoll, 450, 135);
    }

    public void drawCountDownDie() {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawRect(this.dc.getWidth() / 2, this.dc.getHeight() / 4, 100, 100);
        this.dc.setFont(Constants.ROLLING_FONT);
        this.dc.drawString(this.totalRoll, 450, 135);

    }

    public void changeCurrentRoll(int delta) {
        this.totalRoll += delta;
    }

    public Tilesets getSelectedTileset() {
        return this.tileset;
    }

    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public void setRollState(RollState state) {
        this.currentRollState = state;
    }
}
