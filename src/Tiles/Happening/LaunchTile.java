/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles.Happening;

import java.util.Random;
import marioparty.Board;
import marioparty.Character;
import marioparty.Characters;
import marioparty.Constants;

/**
 *
 * @author arnav
 */
public class LaunchTile extends HappeningTile {

    Board board;
    Characters characters;
    Random rand = new Random();

    public LaunchTile(int x, int y) {
        super(x, y);

        board = Board.getInstance();
        characters = Characters.getInstance();
    }

    @Override
    public void triggerEvent(Character player) {
        if (Constants.NUM_OF_PLAYERS > 1) {
            int playerIndex = rand.nextInt(Constants.NUM_OF_PLAYERS - 1);
            if (playerIndex == board.getPlayerTurn()) {
                playerIndex = Constants.NUM_OF_PLAYERS - 1;
            }
            while (player.getY() > -50) {
                player.setY(player.getY() - 1);
            }

            player.setTilePos(characters.getCharacter(playerIndex).getTilePos());
            player.setX(board.getSelectedTileset().getTileAtIndex(player.getTilePos()).getX());
            while (player.getY() < board.getSelectedTileset().getTileAtIndex(player.getTilePos()).getY()) {
                player.setY(player.getY() + 1);
            }
        } else {
            System.out.println("No target to warp to!");
        }
    }

    @Override
    public void passingEvent(Character player) {
        //Nothing at all.
    }
}
