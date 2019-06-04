/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles.Happening;

import marioparty.Character;
import marioparty.Board;

/**
 *
 * @author arnav
 */
public class WarpTile extends HappeningTile {

    Board board;

    public WarpTile(int x, int y) {
        super(x, y);
        board = Board.getInstance();
    }

    @Override
    public void triggerEvent(Character player) {

        for (int i = 0; i < board.getSelectedTileset().length(); i++) {
            if (board.getSelectedTileset().getTileAtIndex(i) instanceof WarpTile) {
                if (!(board.getSelectedTileset().getTileAtIndex(i).getX() == this.x
                        && board.getSelectedTileset().getTileAtIndex(i).getY() == this.y)) {
                    player.setTilePos(i);
                    player.setX(board.getSelectedTileset().getTileAtIndex(i).getX());
                    player.setY(board.getSelectedTileset().getTileAtIndex(i).getY());
                }
            }
        }
    }

    @Override
    public void passingEvent(Character player) {
        // Actually nothing.
    }
}
