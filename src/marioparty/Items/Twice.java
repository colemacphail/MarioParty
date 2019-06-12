/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Items;

import java.awt.Color;
import marioparty.Board;
import marioparty.RollState;

/**
 *
 * @author arnav
 */
//STRUCTURE FOR A CANDY
public class Twice extends Item {

    @Override
    public void triggerEvent() {
        Board.getInstance().setRollState(RollState.TWICE);
    }

    @Override
    public void draw(int x, int y) {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawString('2', x, y);
    }

}
