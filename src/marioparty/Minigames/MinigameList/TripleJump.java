package marioparty.Minigames.MinigameList;

import ControllerInput.Controllers;
import DLibX.DConsole;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import marioparty.Characters;
import marioparty.Console;
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

    //-0.0001(x-375)^2 (x-525)
    private double cAccel;
    private double cursorX;
    private double cXChange;

    private final double CURSOR_Y = 275;
    private DConsole dc = Console.getInstance();

    public void init() {
        this.cursorX = 300;
        this.cXChange = 0;
        this.cAccel = 0;

    }

    public void run() {

        /*if (this.cursorX >= 600 || this.cursorX <= 300) {
            
            this.cXChange *= -1;

        }*/
        this.cAccel = -0.000003 * (Math.pow((this.cursorX - 375), 2)) * (this.cursorX - 525);
        this.cXChange += this.cAccel;
        this.cursorX += cXChange;

        //RED
        dc.setPaint(Color.RED);
        dc.fillRect(450, 300, 300, 30);
        //ORANGE
        dc.setPaint(Color.ORANGE);
        dc.fillRect(450, 300, 200, 30);
        //YELLOW
        dc.setPaint(Color.YELLOW);
        dc.fillRect(450, 300, 100, 30);
        //GREEN
        dc.setPaint(Color.GREEN);
        dc.fillRect(450, 300, 25, 30);
        //Box
        dc.setPaint(Color.BLACK);
        dc.drawRect(450, 300, 300, 30);
        //Cursor
        dc.fillRect(this.cursorX, this.CURSOR_Y, 1, 30);
    }

    public double getCX() {
        return this.cursorX;
    }

    public double getCY() {
        return this.CURSOR_Y;
    }

    public void reset() {
        this.cursorX = 0;
        this.cXChange = 0;
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

    public void shift() {
        this.x += this.size;
    }

    public double getXChange() {
        return this.xChange;
    }

    public void setX() {
        this.x += this.xChange;
    }

    public boolean jump() {
        return dc.getKeyPress(' ');
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
        this.jumpbar.init();
        for (int i = 0; i < this.athletes.length; i++) {
            this.athletes[i] = new Athlete();
        }
        for (int i = 0; i < 3; i++) {
            this.lines[i][0] = i * 225 + 300;
            this.lines[i][1] = 595;
        }
        for (int i = 0; i < 3; i++) {
            this.targets[i] = i * 225 + 300;
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            dc.fillRect(this.lines[i][0], this.lines[i][1], 20, 10);
        }
        for (Athlete athlete : this.athletes) {
            athlete.draw();
            for (double target : this.targets) {
                if (athlete.getX() <= target && athlete.getX() > target - 25) {
                    this.targeted = true;

                }
            }
        }
        if (!targeted) {
            for (Athlete athlete : this.athletes) {
                athlete.setX();
            }

        } else {
            this.jumpbar.run();
            for (int i = 0; i < athletes.length; i++) {
                if (athletes[i].jump()) {
                    jumpbar.init();
                    characters.getCharacter(i).changeMinigameScore(100);
                    athletes[i].shift();
                    this.targeted = false;

                }
            }

        }

    }

    @Override
    public Set<Players> isDone() {
        Set<Players> winningPlayers = new HashSet<>();

        boolean isDone = true;

        for (Athlete athlete : athletes) {
            if (athlete.getX() <= 900) {
                isDone = false;
            }
        }
        if (isDone || this.hasTimeoutOccurred()) {
            int maxScore = 0;
            for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
                maxScore = Math.max(maxScore, characters.getCharacter(i).getMinigameScore());
                System.out.println(characters.getCharacter(i).getMinigameScore());
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
