package marioparty.Minigames;

import DLibX.DConsole;
import marioparty.Console;

/**
 *
 * @author Cole
 */
public abstract class Minigame {

    private final DConsole dc = Console.getInstance();
    private long startTime;
    private long timeout;
    private MinigameType type;

    public void init() {
        this.startTime = System.currentTimeMillis();
    }

    public abstract void run();

    public boolean hasTimeoutOccurred() {
        return System.currentTimeMillis() > this.startTime + this.timeout;
    }

    public abstract boolean isDone();

    public MinigameType getType() {
        return this.type;
    }

}
