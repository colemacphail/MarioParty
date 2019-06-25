package marioparty.Minigames.MinigameList;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
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
    private double y;

    private double cursorY;
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
        dc.fillRect(450, this.y, 300, 30);
        //ORANGE
        dc.setPaint(Color.ORANGE);
        dc.fillRect(450, this.y, 200, 30);
        //YELLOW
        dc.setPaint(Color.YELLOW);
        dc.fillRect(450, this.y, 100, 30);
        //GREEN
        dc.setPaint(Color.GREEN);
        dc.fillRect(450, this.y, 25, 30);
        //Box
        dc.setPaint(Color.BLACK);
        dc.drawRect(450, this.y, 300, 30);
        //Cursor
        dc.fillRect(this.cursorX, this.y - 10, 1, 30);
    }

    public double getCX() {
        return this.cursorX;
    }

    public double getCY() {
        return this.cursorY;
    }

    public void setY(int y) {
        this.y = y;
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
    private boolean targeted;

    public Athlete() {
        this.x = 30;
        this.size = 30;
        this.color = new Color(145, 30, 30);
        this.pressed = false;
        this.targeted = false;
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

    public void setY(int y) {
        this.y = y;
    }

    public void latch() {
        if (this.pressed == true) {
            this.pressed = false;
        } else {
            this.pressed = true;
        }
    }

    public boolean isLatched() {
        return pressed;
    }

    public boolean isTargeted() {
        return this.targeted;
    }

    public void target() {
        this.targeted = true;

    }

    public void untarget() {
        this.targeted = false;
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
    JumpBar[] jumpbars = new JumpBar[Constants.NUM_OF_PLAYERS];
    double[] targets = new double[3];
    int[][] lines = new int[3][2];

    public TripleJump() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {

        for (int i = 0; i < this.athletes.length; i++) {
            this.athletes[i] = new Athlete();
            this.athletes[i].setY((i * dc.getHeight() / Constants.NUM_OF_PLAYERS) + (dc.getHeight() / Constants.NUM_OF_PLAYERS - 10));
            jumpbars[i] = new JumpBar();
            jumpbars[i].setY((i * dc.getHeight() / Constants.NUM_OF_PLAYERS) + 20);
            jumpbars[i].init();
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
        for (int i = 0; i < athletes.length; i++) {
            dc.setPaint(characters.getCharacter(i).getColour());
            athletes[i].draw();
            for (double target : this.targets) {
                if (athletes[i].getX() <= target && athletes[i].getX() > target - 25) {
                    athletes[i].target();

                }
            }
        }
        for (int i = 0; i < athletes.length; i++) {
            if (!athletes[i].isTargeted()) {

                athletes[i].setX();

            } else if (athletes[i].isTargeted()) {

                jumpbars[i].run();

                if (this.controllers.getControllerInput(i).actions().contains(InputAction.A)) {
                    
                    characters.getCharacter(i).changeMinigameScore((int)(1000/(Math.abs(450 - jumpbars[i].getCX()))));
                    jumpbars[i].init();
                    athletes[i].shift();
                    athletes[i].untarget();

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
