package Tiles;

import java.awt.Color;
import marioparty.Character;

/**
 *
 * @author Jacob
 */
public class StarTile extends PassingTile {

    public StarTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {
        this.dc.setPaint(Color.YELLOW);
        super.draw();
    }

    @Override
    public void passOverEvent(Character player) {

        if (player.getCoins() >= 20) {
            player.changeStars(1);
            player.changeCoins(-20);
        } else {
            System.out.println("YOU'RE TOO POOR!");
        }
    }
}
