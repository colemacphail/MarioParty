/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import DLibX.DConsole;

/**
 *
 * @author Cole
 */
public class Character {

    public enum CharacterName {
        LUIGI, WARIO, WALUIGI, YOSHI, GOOMBA, BOWSER, PEACH, PIRANHA_PLANT, 
        DONKEY_KONG, MR_L
    }
    
    private final DConsole dc;
    private CharacterName name;
    private int x;
    private int y;
    private int coins;
    private int stars;

    public Character(DConsole dc) {
        this.dc = dc;
    }

    public void move() {

    }

    public void draw() {

    }
    
    public void check() {
        if (this.coins < 0) {
            this.coins = 0;
        } 
        if (this.stars < 0) {
            this.stars = 0;
        }
    }
}
