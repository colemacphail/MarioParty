package marioparty.Items;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Cole
 */
public class ItemBuilder {

    private final ArrayList<Item> items = new ArrayList<>();
    private final Random randGen = new Random();

    private static ItemBuilder instance;

    public static ItemBuilder getInstance() {
        if (ItemBuilder.instance == null) {
            ItemBuilder.instance = new ItemBuilder();
        }

        return ItemBuilder.instance;
    }

    private ItemBuilder() {
        // add all created items here
        // i.e. this.items.add(new CoolItem());
        this.items.add(new Twice());
    }

    //spits out an item at random
    public Item chooseItem() {
        // grab a minigame at a random index
        int index = this.randGen.nextInt(this.items.size());
        return this.items.get(index);
    }

}
