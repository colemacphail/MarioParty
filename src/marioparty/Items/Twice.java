/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Items;

/**
 *
 * @author arnav
 */
//STRUCTURE FOR A CANDY
public class Twice extends Item {

    @Override
    public void triggerEvent() {

    }

    @Override
    public void draw(int x, int y) {
        this.dc.drawString('2', x, y);
    }

}
