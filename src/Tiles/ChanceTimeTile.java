/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import java.awt.Color;
import java.util.Random;
import marioparty.Character;
import marioparty.Constants;

/**
 *
 * @author arnav
 */
public class ChanceTimeTile extends Tile {

    public ChanceTimeTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {
        this.dc.setPaint(Color.ORANGE);
        super.draw();
        this.dc.setFont(Constants.TILES_TEXT);
        this.dc.setPaint(Color.WHITE);
        this.dc.drawString("☭", x, y);
    }

    @Override
    public void triggerEvent(Character player) {
        Random rand = new Random();
        int playerGivingIndex;
        int playerRecivingIndex;
        System.out.println('h');
    }

    @Override
    public void passingEvent(Character player) {
        // actually nothing
    }

}
