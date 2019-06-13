/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author arnav
 */
public class Characters {

    private static Characters instance;

    public static Characters getInstance() {
        if (Characters.instance == null) {
            Characters.instance = new Characters();
        }
        return Characters.instance;
    }
    public static final Character[] characters = new Character[Constants.NUM_OF_PLAYERS];
    
    public static int getLength() {
        return Constants.NUM_OF_PLAYERS;
    }

    public static void sortCharacters() {
        Arrays.sort(characters, Comparator.comparing(Character::getStars));
        //TODO: also sort by coins
    }

    public Character getCharacter(int n) {
        return characters[n];
    }
}
