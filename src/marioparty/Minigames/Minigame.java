package marioparty.Minigames;

import DLibX.DConsole;
import marioparty.Console;

/**
 *
 * @author Cole
 */
public abstract class Minigame {

    //VARIABLES
    protected final DConsole dc = Console.getInstance();
    protected long startTime;
    protected long timeout;
    private final MinigameType type;
    
    //CONSTRUCTOR
    public Minigame(MinigameType type, long timeout) {
        this.type = type;
        this.timeout = timeout;
    }
    
    //METHODS
    public abstract void init();

    public abstract void run();

    public boolean hasTimeoutOccurred() {
        return System.currentTimeMillis() > this.startTime + this.timeout;
    }

    public abstract boolean isDone();

    public MinigameType getType() {
        return this.type;
    }

}
