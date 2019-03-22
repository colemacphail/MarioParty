
package Tiles;

import java.awt.Color;

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
    public void triggerEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
