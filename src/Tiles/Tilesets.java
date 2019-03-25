/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import DLibX.DConsole;
import java.awt.Color;
import marioparty.Console;

/**
 *
 * @author Cole
 */
public class Tilesets {

    public static final Tile[] BASIC = new Tile[]{new AddCoinTile(450, 300), new AddCoinTile(400, 300), new AddCoinTile(350, 300), new AddCoinTile(300, 300), new SubtractCoinTile(250, 300), new StarTile(250, 250)};

    private final Tile[] selectedTileset;
    private final DConsole dc = Console.getInstance();

    public Tilesets(Tile[] tileset) {
        this.selectedTileset = tileset;
    }

    public void draw() {
        for (int i = 0; i < this.selectedTileset.length; i++) {
            this.dc.setPaint(Color.BLACK);
            if (i < this.selectedTileset.length - 1) {
                this.dc.drawLine(this.selectedTileset[i].getX(), this.selectedTileset[i].getY(), this.selectedTileset[i + 1].getX(), this.selectedTileset[i + 1].getY());
            } else {
                this.dc.drawLine(this.selectedTileset[i].getX(), this.selectedTileset[i].getY(), this.selectedTileset[0].getX(), this.selectedTileset[0].getY());
            }

        }

        for (int i = 0; i < this.selectedTileset.length; i++) {
            this.selectedTileset[i].draw();
        }
    }

    public Tile[] getSelectedTileset() {
        return this.selectedTileset;
    }

}
