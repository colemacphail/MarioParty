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
    private final int numOfPlayers = 2;
    private final Character[] characters = new Character[this.numOfPlayers];
    private int playerTurn = 0;
    private Minigame selectedMinigame;
    private int cameraOffsetX = 0;
    private int cameraOffsetY = 0;
    private final Random ranGen = new Random();
    private int currentRoll = 0;
    private final MinigameBuilder minigameBuilder = MinigameBuilder.getInstance();
    private final GamepadInput[] playerInputs = new GamepadInput[this.numOfPlayers];

    private Board() {
        this.currentGameState = GameState.INIT;

        for (int i = 0; i < this.playerInputs.length; i++) {
            this.playerInputs[i] = new GamepadInput(i);
        }

    }

    public void update() {

        switch (this.currentGameState) {
            case MENU:
                // dc.drawImage("menu.jpg",0,0);
                //JPanel p = new JPanel();
                //p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

                break;
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
                        if (this.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[this.characters[playerTurn].getTargetTile()])
                                && this.characters[playerTurn].getTilePos() == this.characters[playerTurn].getTargetTile()
                                && this.currentRoll == 0) {
                            this.characters[playerTurn].setTilePos(this.characters[playerTurn].getTargetTile());
                            this.currentTurnState = TurnState.END;
                        } else {
                            this.characters[playerTurn].moveToNextTile(this.tileset.getSelectedTileset()[(this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length]);
                            if (this.characters[playerTurn].isWithinRange(this.tileset.getSelectedTileset()[(this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length])) {
                                this.currentRoll--;
                                this.characters[playerTurn].setTilePos((this.characters[playerTurn].getTilePos() + 1) % this.tileset.getSelectedTileset().length);
                            }
                        }
                        break;

                    case END:
                        this.tileset.getSelectedTileset()[this.characters[this.playerTurn].getTargetTile()].triggerEvent(this.characters[this.playerTurn]);
                        this.currentTurnState = TurnState.ROLLING;
                        this.playerTurn = (this.playerTurn + 1) % (this.numOfPlayers);
                        if (this.playerTurn == 0) {
                            this.currentGameState = GameState.MINIGAME_INIT;
                        }
                        break;

                }
                break;

            case MINIGAME_INIT:
                this.selectedMinigame
                        = this.minigameBuilder.chooseMinigame(new MinigameType[]{MinigameType.FFA});
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
