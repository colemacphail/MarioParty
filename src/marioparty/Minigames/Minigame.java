package marioparty.Minigames;

import DLibX.DConsole;
import marioparty.Characters;
import marioparty.Console;
import marioparty.Constants;

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

    //CONSTRUCTOR
    public Minigame(MinigameType type, long timeout) { // all minigames must have a type and timeout
        this.type = type;
        this.timeout = timeout;
    }

    //METHODS
    public abstract void init();

    public abstract void run();

    public boolean hasTimeoutOccurred() {
        return System.currentTimeMillis() > this.startTime + this.timeout;
    }

    /**
     *
     * @return -1 if not done, otherwise return winning player(s)
     */
    public abstract int isDone();

    public MinigameType getType() {
        return this.type;
    }

    public void displayMinigameScoreCornerSplitscreen() {
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            dc.drawString(characters.characterAtI(i).getMinigameScore(),
                    dc.getWidth() / 4 * (i % 2 == 1 ? 3 : 1) - dc.getWidth() / 8,
                    dc.getHeight() / 4 * (i > 1 ? 3 : 1) - dc.getHeight() / 8);
        }
    }
    
    public void displayMinigameScoreVerticalSplitscreen() {
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            dc.drawString(characters.characterAtI(i).getMinigameScore(),
                    dc.getWidth() / 8 * (i) + dc.getWidth() / 8 * (i + 1),
                    dc.getHeight() / 8 * (7));
        }
    }

}
