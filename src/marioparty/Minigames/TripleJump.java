package marioparty.Minigames;

import java.awt.Color;

/**
 *
 * @author Jacob
 */
class Athlete extends MinigameObject {

    private int size;
    private Color color;
    private boolean pressed;

    public Athlete() {
        this.x = 30;
        this.y = 585;
        this.size = 30;
        this.color = new Color(145, 30, 30);
        this.pressed = false;
    }

    @Override
    protected void draw() {
        dc.fillRect(x, y, size, size);
    }
}

public class TripleJump {

}
