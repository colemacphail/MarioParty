/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles.Happening;

import Tiles.Tile;
import java.awt.Color;
import marioparty.Constants;

/**
 *
 * @author arnav
 */
public abstract class HappeningTile extends Tile {

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
}
