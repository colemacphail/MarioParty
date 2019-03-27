package marioparty;

import marioparty.Items.Item;
import DLibX.DConsole;
import Tiles.Tile;
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
    private double x;
    private double y;
    private int coins = 0;
    private int stars = 0;
    private int tilePos = 0;
    private int targetTilePos = 0;
    private Item[] item = new Item[4];
    private boolean itemUsed = false;

    public Character(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    public void moveToNextTile(Tile tile) {
        double xChange = 0;
        double yChange = 0;

        //set angle based on position relative to player
        double angle;
        if (tile.getX() == this.x) {
            if (tile.getY() > this.y) {
                angle = 90;
            } else {
                angle = 270;
            }
        } else {
            angle = Math.atan((tile.getY() - this.y) / (tile.getX() - this.x));
        }
        //constantly move towards player based on angle
        if (tile.getX() >= x) {
            xChange = Math.cos((angle));
            yChange = Math.sin((angle));
        } else if (tile.getX() < x) {
            xChange = -Math.cos((angle));
            yChange = -Math.sin((angle));
        }

        this.move(xChange, yChange);
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

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setTargetTilePos(int position) {
        this.targetTilePos = position;
    }

    public void setTilePos(int position) {
        this.tilePos = position;
    }

    public int getTilePos() {
        return this.tilePos;
    }

    public int getTargetTile() {
        return this.targetTilePos;
    }

    public boolean isWithinRange(Tile tile) {
        return this.x > tile.getX() - 1 && this.x < tile.getX() + 1 && this.y > tile.getY() - 1 && this.y < tile.getY() + 1;
    }

    public void changeCoins(int coins) {
        this.coins += coins;
    }

    public void changeStars(int stars) {
        this.stars += stars;
    }
}
