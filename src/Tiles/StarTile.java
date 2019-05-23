package Tiles;

import java.awt.Color;
import marioparty.Board;
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
    public void draw() {
        this.dc.setPaint(Color.YELLOW);
        super.draw();
    }

    @Override
    public void triggerEvent(Character player) { // Don't land on the tile, don't count it as a move
        player.setTargetTilePos((player.getTargetTile() + 1) % Board.getInstance().getSelectedTileset().getSelectedTileset().length);
        Board.getInstance().changeCurrentRoll(1);
    }

    @Override
    public void passingEvent(Character player) { // if the player can afford it, buy a star
        this.triggerEvent(player);
        if (player.getCoins() >= 20) {
            player.changeStars(1);
            player.changeCoins(-20);
        } else {
            System.out.println("YOU'RE TOO POOR!");
        }
    }
}
