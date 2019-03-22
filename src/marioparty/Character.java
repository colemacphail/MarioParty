package marioparty;

import marioparty.Items.Item;
import DLibX.DConsole;
import java.awt.Color;

/**
 *
 * @author Cole
 */
public class Character {

    public enum CharacterName {
        LUIGI, WARIO, WALUIGI, YOSHI, GOOMBA, BOWSER, PEACH, PIRANHA_PLANT,
        DONKEY_KONG, MR_L
    }

    private final DConsole dc = Console.getInstance();
    private CharacterName name;
    private int x;
    private int y;
    private int coins;
    private int stars;
    private int tilePos;
    private Item[] item = new Item[4];
    private boolean itemUsed = false;

    public Character() {
    }

    public void move() {
        this.tilePos++;
    }

    public void draw() {
//        this.dc.drawImage(this.name.toString() + ".png", x, y);
        //place holders
        this.dc.setPaint(Color.BLACK);
        this.dc.drawRect(this.x, this.y, 40, 40);
    }
    
    public void useItem(int itemPosition) {
        item[itemPosition].TriggerEvent();
        itemUsed = true;
    }

    public void check() {
        if (this.coins < 0) {
            this.coins = 0;
        }
        if (this.stars < 0) {
            this.stars = 0;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setTilePos(int position) {
        this.tilePos = position;
    }
}
