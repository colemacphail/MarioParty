
package Tiles;

import java.awt.Color;
import marioparty.Character;

/**
 *
 * @author Jacob
 */
public class StarTile extends Tile {
    public StarTile(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void draw(){
        this.dc.setPaint(Color.YELLOW);
        super.draw();
    }

    @Override
    public void triggerEvent(Character player) {
        player.changeStars(1);
    }
}
