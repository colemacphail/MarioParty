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
class jumpBar {
    
    private int redW = 300;
    private int orangeW = 200;
    private int yellowW = 100;
    private int greenW = 50;
            
    
    private double[] targets = new double[3];
    private int x = 450;
    private int y = 300;
    private int width = 300;
    private int height = 30;
    
    
}

class Athlete extends MinigameObject {

    private int size;
    private Color color;
    private boolean pressed;
    private double yChange;
    private double xChange = 1;

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
    int[][] lines = new int[3][2];

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

    }

    @Override
    public void run() {
        for (Athlete athlete : athletes) {
            athlete.draw();
            athlete.setX();
        }
        for (int i = 0; i < 3; i++) {
            dc.fillRect(lines[i][0], lines[i][1], 20, 10);
        }
        dc.drawRect(450,300,400, 30);
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
