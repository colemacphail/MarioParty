/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tiles;

import static Tiles.ChanceTimeTile.ChanceTimeEvents.*;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import marioparty.Character;
import marioparty.Characters;
import marioparty.Constants;

/**
 *
 * @author arnav
 */
public class ChanceTimeTile extends Tile {

    Characters characters;

    public enum ChanceTimeEvents {
        GIVE_20_COINS, GIVE_ALL_COINS, SWAP_COINS, GIVE_STAR, SWAP_STARS
    }

    public ChanceTimeTile(int x, int y) {
        super(x, y);
        this.characters = Characters.getInstance();
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
        if (Constants.NUM_OF_PLAYERS > 1) {
            Random rand = new Random();
            int counter = 0;
            int playerGivingIndex = rand.nextInt(Constants.NUM_OF_PLAYERS);
            int playerRecivingIndex = rand.nextInt(Constants.NUM_OF_PLAYERS - 1);
            ChanceTimeEvents event = ChanceTimeEvents.values()[rand.nextInt(5)];

            if (playerGivingIndex == playerRecivingIndex) {
                playerRecivingIndex = Constants.NUM_OF_PLAYERS - 1;
            }

            int coins1 = this.characters.getCharacter(playerGivingIndex).getCoins();
            int stars1 = this.characters.getCharacter(playerGivingIndex).getStars();
            int coins2 = this.characters.getCharacter(playerRecivingIndex).getCoins();
            int stars2 = this.characters.getCharacter(playerRecivingIndex).getStars();
            switch (event) {
                case GIVE_20_COINS:
                    this.characters.getCharacter(playerGivingIndex).changeCoins(-(coins1 < 20 ? coins1 : 20));
                    this.characters.getCharacter(playerRecivingIndex).changeCoins((coins1 < 20 ? coins1 : 20));
                    break;
                case GIVE_ALL_COINS:
                    this.characters.getCharacter(playerGivingIndex).changeCoins(-coins1);
                    this.characters.getCharacter(playerRecivingIndex).changeCoins(coins1);
                    break;
                case SWAP_COINS:
                    this.characters.getCharacter(playerGivingIndex).setCoins(coins2);
                    this.characters.getCharacter(playerRecivingIndex).setCoins(coins1);
                    break;
                case GIVE_STAR:
                    this.characters.getCharacter(playerGivingIndex).changeStars(-(stars1 < 1 ? 0 : 1));
                    this.characters.getCharacter(playerRecivingIndex).changeStars((stars1 < 1 ? 0 : 1));
                    break;
                case SWAP_STARS:
                    this.characters.getCharacter(playerGivingIndex).setStars(stars2);
                    this.characters.getCharacter(playerRecivingIndex).setStars(stars1);
                    break;
                default:
                    System.out.println("BRUH NO CHANCETIME???");
                    break;
            }
            while (counter < 5000) {
                dc.clear();
                dc.setFont(new Font("Comic Sans", Font.BOLD, 60));
                if (counter > 1000) {
                    dc.drawString(this.characters.getCharacter(playerRecivingIndex).getName(), dc.getWidth() / 4, dc.getHeight() / 2);
                }
                if (counter > 2000) {
                    dc.drawString(this.characters.getCharacter(playerGivingIndex).getName(), dc.getWidth() * 3 / 4, dc.getHeight() / 2);
                }
                if (counter > 4000) {
                    dc.drawString(event.toString(), dc.getWidth() / 2, dc.getHeight() / 2);
                }
                dc.redraw();
            }
        } else {
            System.out.println("you have no friends.");
        }
    }

    @Override
    public void passingEvent(Character player) {
        // actually nothing
    }

}
