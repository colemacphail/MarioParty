/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import DLibX.DConsole;

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

    abstract void run();

    public boolean hasTimeoutOccurred() {
        return System.currentTimeMillis() > this.startTime + this.timeout;
    }

    abstract boolean isDone();

    public MinigameType getType() {
        return this.type;
    }

}
