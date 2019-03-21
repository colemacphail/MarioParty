package Tiles;

import java.awt.Color;

/**
 *
 * @author Cole
 */
public class AddCoinTile extends Tile{
    
    public AddCoinTile(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void draw(){
        this.dc.setPaint(Color.BLUE);
        super.draw();
    }

    @Override
    public void triggerEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
