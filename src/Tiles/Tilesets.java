/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import DLibX.DConsole;
import Tiles.Happening.LaunchTile;
import Tiles.Happening.PiranhaPlantTile;
import Tiles.Happening.WarpTile;
import java.awt.Color;
import marioparty.Console;

/**
 *
 * @author Cole
 */
public class Tilesets {

    public static final Tile[] BASIC = new Tile[]{new AddCoinTile(450, 300), new SubtractCoinTile(400, 300), new ItemTile(350, 300),
        new AddCoinTile(300, 300), new WarpTile(250, 300), new StarTile(250, 250), new SubtractCoinTile(300, 250),
        new AddCoinTile(350, 250), new AddCoinTile(400, 250), new LaunchTile(450, 250), new AddCoinTile(500, 250), new AddCoinTile(550, 250),
        new SubtractCoinTile(600, 250), new AddCoinTile(600, 300), new AddCoinTile(600, 350), new AddCoinTile(600, 400), new SubtractCoinTile(600, 450),
        new AddCoinTile(550, 450), new ChanceTimeTile(500, 450), new WarpTile(450, 450), new PiranhaPlantTile(450, 400), new AddCoinTile(450, 350)};

    private final Tile[] selectedTileset;
    private final DConsole dc = Console.getInstance();

    public Tilesets(Tile[] tileset) {
        this.selectedTileset = tileset;
    }

    public void draw() {
        for (int i = 0; i < this.selectedTileset.length; i++) { // draw lines between tiles
            this.dc.setPaint(Color.BLACK);
            if (i < this.selectedTileset.length - 1) {
                this.dc.drawLine(this.selectedTileset[i].getX(), this.selectedTileset[i].getY(), this.selectedTileset[i + 1].getX(), this.selectedTileset[i + 1].getY());
            } else {
                this.dc.drawLine(this.selectedTileset[i].getX(), this.selectedTileset[i].getY(), this.selectedTileset[0].getX(), this.selectedTileset[0].getY());
            }

        }

        for (int i = 0; i < this.selectedTileset.length; i++) {
            this.selectedTileset[i].draw(); // draw all tiles
        }
    }

    public Tile[] getSelectedTileset() {
        return this.selectedTileset;
    }
    
    public int length() {
        return this.selectedTileset.length;
    }
    
    public Tile getTileAtIndex(int index) {
        return this.selectedTileset[index];
    }

}
