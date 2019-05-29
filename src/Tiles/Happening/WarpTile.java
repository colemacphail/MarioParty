/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles.Happening;

import marioparty.Character;

/**
 *
 * @author arnav
 */
public class WarpTile extends HappeningTile {
    
    public WarpTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void triggerEvent(Character player) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO: make this work
    }

    @Override
    public void passingEvent(Character player) {
//        Actually nothing
    }
    
}
