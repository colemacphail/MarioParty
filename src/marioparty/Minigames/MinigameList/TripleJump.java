package marioparty.Minigames.MinigameList;

import ControllerInput.Controllers;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import marioparty.Characters;
import marioparty.Constants;
import marioparty.Minigames.Minigame;
import marioparty.Minigames.MinigameObject;
import marioparty.Minigames.MinigameType;
import marioparty.Players;

/**
 *
 * @author Jacob
 */
class JumpBar {

    private int redW = 150;
    private int orangeW = 100;
    private int yellowW = 50;
    private int greenW = 25;

    private double cursorX;
    private double cXChange = 1;
    private final double ACCEL = 1.1;
    private final double CURSOR_Y = 250;

    public void run() {
        this.cursorX = 250;
        this.cursorX += cXChange;
        cXChange *= ACCEL;

        if (this.cursorX >= 600) {
            this.cXChange = 0;
            this.cursorX = 600;
        }
    }

    public double getCX() {
        return this.cursorX;
    }

    public double getCY() {
        return this.CURSOR_Y;
    }

}

class Athlete extends MinigameObject {

    private int size;
    private Color color;
    private boolean pressed;
    private double yChange;
    private double xChange = 5;

    public Athlete() {
        this.x = 30;
        this.y = 585;
        this.size = 30;
        this.color = new Color(145, 30, 30);
        this.pressed = false;
    }

    public void jump() {
        if (dc.isKeyPressed(' ')) {
            if (!pressed) {
                this.pressed = true;
                this.yChange = -10;
            }
        }

        this.y += this.yChange;

        this.yChange += 0.5;

        if (this.y >= 585) {
            this.pressed = false;
            this.yChange = 0;
            this.y = 585;
        }
    }

    public double getXChange() {
        return this.xChange;
    }

    public void setX() {
        this.x += this.xChange;
    }

    @Override
    protected void draw() {
        dc.fillRect(x, y, size, size);

    }
}

public class TripleJump extends Minigame {

    private Characters characters = Characters.getInstance();
    private final Controllers controllers = Controllers.getInstance();
    Athlete[] athletes = new Athlete[Constants.NUM_OF_PLAYERS];
    JumpBar jumpbar = new JumpBar();
    double[] targets = new double[3];
    int[][] lines = new int[3][2];
    boolean targeted = false;

    public TripleJump() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        for (int i = 0; i < athletes.length; i++) {
            athletes[i] = new Athlete();
        }
        for (int i = 0; i < 3; i++) {
            lines[i][0] = i * 225 + 300;
            lines[i][1] = 595;
        }
        for (int i = 0; i < 3; i++) {
            targets[i] = i * 225 + 300;
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            dc.fillRect(lines[i][0], lines[i][1], 20, 10);
        }
        for (Athlete athlete : athletes) {
            athlete.draw();
        }
        if (!targeted) {
            for (Athlete athlete : athletes) {
                athlete.setX();
            }
        for (Athlete athlete : athletes) {
            for (int i = 0; i < targets.length; i++) {
                if (athlete.getX() < targets[i] && athlete.getX() >= targets[i] - 25) {
                    targeted = true;
                }
            }
        }
        
        } else if (targeted) {
            
            if(jumpbar.getCX() >= 580){
            targeted = false;
            
            }

            jumpbar.run();
            dc.setPaint(Color.RED);
            dc.fillRect(450, 301, 400, 30); //RED
            dc.setPaint(Color.ORANGE);
            dc.fillRect(450, 301, 250, 30); //ORNAGE
            dc.setPaint(Color.YELLOW);
            dc.fillRect(450, 301, 150, 30); //YELLOW
            dc.setPaint(Color.GREEN);
            dc.fillRect(450, 301, 50, 30); //GREEN
            dc.setPaint(Color.BLACK);
            dc.drawRect(450, 300, 400, 30); //BOX

            dc.fillRect(jumpbar.getCX(), jumpbar.getCY(), 10, 50);
        }
    }

    @Override
    public Set<Players> isDone() {
        Set<Players> winningPlayers = new HashSet<>();

        boolean isDone = true;

        for (Athlete athlete : athletes) {
            if (athlete.getXChange() != 0) {
                isDone = false;
            }
        }
        if (isDone || this.hasTimeoutOccurred()) {
            int maxScore = 0;
            for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                maxScore = Math.max(maxScore, characters.getCharacter(i).getMinigameScore());
            }

            for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                if (characters.getCharacter(i).getMinigameScore() >= maxScore) {
                    winningPlayers.add(Players.values()[i]);
                }
            }
        }

        return winningPlayers;
    }

}
