/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles.Happening;

import Tiles.Tile;
import java.awt.Color;
import marioparty.Character;
import marioparty.Constants;

/**
 *
 * @author arnav
 */
public class HappeningTile extends Tile {

    public HappeningTile(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void draw() {
        this.dc.setPaint(Color.GREEN);
        super.draw();
        this.dc.setFont(Constants.TILES_TEXT);
        this.dc.setPaint(Color.WHITE);
        this.dc.drawString("?", x, y);
    }

    @Override
    public void triggerEvent(Character player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void passingEvent(Character player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
