package marioparty;

import DLibX.DConsole;

/**
 *
 * @author Cole
 */
public abstract class Minigame {

    private final DConsole dc;
    private long startTime;
    private long timeout;
    private MinigameType type;

    public Minigame(DConsole dc) {
        this.dc = dc;
    }

    public void init() {
        this.startTime = System.currentTimeMillis();
    }

    abstract void run();

    private boolean hasTimeoutOccurred() {
        return System.currentTimeMillis() > this.startTime + this.timeout;
    }

    abstract boolean isDone();

    public MinigameType getType() {
        return this.type;
    }

}
