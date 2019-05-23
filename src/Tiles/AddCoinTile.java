package Tiles;

import java.awt.Color;
import marioparty.Character;

/**
 *
 * @author Cole
 */
public class AddCoinTile extends Tile {

    public AddCoinTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {
        this.dc.setPaint(Color.BLUE);
        super.draw();
    }

    @Override
    public void triggerEvent(Character player) {
        player.changeCoins(3);
    }

    @Override
    public void passingEvent(Character player) {
        //nothing happens
    }

}
