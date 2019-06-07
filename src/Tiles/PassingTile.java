/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import marioparty.Board;
import marioparty.Character;

/**
 *
 * @author colem
 */
public abstract class PassingTile extends Tile {

    public PassingTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void triggerEvent(Character player) { // Don't land on the tile, don't count it as a move
        player.setTargetTilePos((player.getTargetTile() + 1) % Board.getInstance().getSelectedTileset().getSelectedTileset().length);
        Board.getInstance().changeCurrentRoll(1);
    }

    @Override
    public void passingEvent(Character player) {
        this.triggerEvent(player);
        this.passOverEvent(player);
    }

    public abstract void passOverEvent(Character player);

}
