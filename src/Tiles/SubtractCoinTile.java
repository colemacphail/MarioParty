
package Tiles;

import java.awt.Color;
import marioparty.Character;

/**
 *
 * @author Jacob
 */
public class SubtractCoinTile extends Tile {

    public SubtractCoinTile(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void draw(){
        this.dc.setPaint(Color.RED);
        super.draw();
    }

    @Override
    public void triggerEvent(Character player) {
        player.changeCoins(-3);
    }
}
