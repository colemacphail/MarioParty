/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import marioparty.Character;
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
    public void passOverEvent(Character player) {

        player.addItem(this.item);
        this.item = this.itemBuilder.chooseItem();

    }

}
