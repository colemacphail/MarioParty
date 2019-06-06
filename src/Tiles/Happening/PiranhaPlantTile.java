/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles.Happening;

import java.awt.Color;
import marioparty.Character;

/**
 *
 * @author arnav
 */
public class PiranhaPlantTile extends HappeningTile {

    public PiranhaPlantTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {
        dc.setPaint(Color.RED);
        dc.fillRect(x + 1, y + 1, 25, 25);
        super.draw();
    }

    @Override
    public void triggerEvent(Character player) {
        if (Math.random() < 0.15) {
            player.changeCoins(10);
        } else {
            player.changeCoins(-10);
        }
    }

    @Override
    public void passingEvent(Character player) {
        //Notta
    }

}
