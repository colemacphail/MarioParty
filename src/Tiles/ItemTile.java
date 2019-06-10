/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import java.awt.Color;
import marioparty.Character;
import marioparty.Constants;
import marioparty.Items.Item;
import marioparty.Items.ItemBuilder;

/**
 *
 * @author colem
 */
public class ItemTile extends PassingTile {

    ItemBuilder itemBuilder = ItemBuilder.getInstance();
    Item item = itemBuilder.chooseItem();

    public ItemTile(int x, int y) {
        super(x, y);
    }
    
    
    @Override 
    public void draw() {
        dc.setFont(Constants.TILES_TEXT);
        this.dc.setPaint(Color.BLACK);
        this.dc.drawString("🍬", x, y - 4);
    }

    @Override
    public void passOverEvent(Character player) {

        player.addItem(this.item);
        this.item = this.itemBuilder.chooseItem();

    }

}
