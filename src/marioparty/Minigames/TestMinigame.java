package marioparty.Minigames;

import DLibX.DConsole;
import marioparty.Console;

/**
 *
 * @author Cole
 */
public class TestMinigame extends Minigame {

    public TestMinigame(MinigameType type) {
        super(type);
    }

    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
        this.timeout = 15000;
    }

    @Override
    public void run() {
        dc.drawString("Press space", 450, 300);
    }

    @Override
    public boolean isDone() {
        return this.dc.isKeyPressed(' ');
    }

}
