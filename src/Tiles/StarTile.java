
package Tiles;

import java.awt.Color;

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
    public void triggerEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
