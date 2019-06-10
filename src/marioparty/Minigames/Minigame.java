package marioparty.Minigames;

import DLibX.DConsole;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Set;
import marioparty.Characters;
import marioparty.Console;
import marioparty.Constants;
import marioparty.Players;

/**
 *
 * @author Cole
 */
public abstract class Minigame {

    //VARIABLES
    protected final DConsole dc = Console.getInstance();
    protected long startTime;
    protected long timeout;
    private final Characters characters = Characters.getInstance();
    private final MinigameType type;
    private long pausedTime;

    //CONSTRUCTOR
    public Minigame(MinigameType type, long timeout) { // all minigames must have a type and timeout
        this.type = type;
        this.timeout = timeout;
    }

    //METHODS
    public abstract void init();

    public abstract void run();

    public boolean hasTimeoutOccurred() {
        return System.currentTimeMillis() > this.startTime + this.timeout + this.pausedTime;
    }

    /**
     *
     * @return winning players
     */
    public abstract Set<Players> isDone();

    public MinigameType getType() {
        return this.type;
    }

    public void drawCornerSplitscreen() {
        this.dc.setPaint(Color.BLACK);
        this.dc.setStroke(new BasicStroke(2));
        this.dc.drawLine(0, this.dc.getHeight() / 2, this.dc.getWidth(), this.dc.getHeight() / 2);
        this.dc.drawLine(this.dc.getWidth() / 2, 0, this.dc.getWidth() / 2, this.dc.getHeight());
    }

    public void displayMinigameScoreCornerSplitscreen() {
        this.dc.setPaint(Color.BLACK);
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            this.dc.drawString(this.characters.getCharacter(i).getMinigameScore(),
                    this.dc.getWidth() / 8 * (i % 2 == 1 ? 5 : 1) - this.dc.getWidth() / 16,
                    this.dc.getHeight() / 8 * (i > 1 ? 5 : 1) - this.dc.getHeight() / 16);
        }
    }

    public void drawVerticalSplitscreen() {
        this.dc.setPaint(Color.BLACK);
        this.dc.setStroke(new BasicStroke(2));
        this.dc.drawLine(this.dc.getWidth() / 4, 0, this.dc.getWidth() / 4, this.dc.getHeight());
        this.dc.drawLine(this.dc.getWidth() / 2, 0, this.dc.getWidth() / 2, this.dc.getHeight());
        this.dc.drawLine(this.dc.getWidth() / 4 * 3, 0, this.dc.getWidth() / 4 * 3, this.dc.getHeight());
    }

    public void displayMinigameScoreVerticalSplitscreen() {
        this.dc.setPaint(Color.BLACK);
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            this.dc.drawString(this.characters.getCharacter(i).getMinigameScore(),
                    this.dc.getWidth() / 8 * (i) + this.dc.getWidth() / 8 * (i + 1),
                    this.dc.getHeight() / 8 * (7));
        }
    }

    public void drawHorizontalSplitscreen() {
        this.dc.setPaint(Color.BLACK);
        this.dc.setStroke(new BasicStroke(2));
        this.dc.drawLine(0, this.dc.getHeight() / 4, this.dc.getWidth(), this.dc.getHeight() / 4);
        this.dc.drawLine(0, this.dc.getHeight() / 2, this.dc.getWidth(), this.dc.getHeight() / 2);
        this.dc.drawLine(0, this.dc.getHeight() / 4 * 3, this.dc.getWidth(), this.dc.getHeight() / 4 * 3);
    }

    public void displayMinigameScoreHorizontalSplitscreen() {
        this.dc.setPaint(Color.BLACK);
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            this.dc.drawString(this.characters.getCharacter(i).getMinigameScore(),
                    this.dc.getWidth() / 8 * (7),
                    this.dc.getHeight() / 8 * (i) + this.dc.getHeight() / 8 * (i + 1));
        }
    }

    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public void addPausedTime(long timePaused) {
        this.pausedTime += timePaused;
    }

}
