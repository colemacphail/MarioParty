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

    public void jump() {
        /* if (dc.isKeyPressed(' ')) {
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
        }*/
    }

    @Override
    protected void draw() {
        dc.fillRect(x, y, size, size);
    }
}

public class TripleJump {

}
