package marioparty.Minigames;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Cole
 */
public class MinigameBuilder {

    ArrayList<Minigame> minigames = new ArrayList<>();
    Random randGen = new Random();

    public MinigameBuilder() {
        // add all created minigames here
        // i.e. this.minigames.add(new FunGame());
        minigames.add(new TestMinigame());
        minigames.add(new CatchTheApple());
    }

    //takes in an array of valid types and spits out a minigame that matches one of the types
    public Minigame chooseMinigame(MinigameType[] types) {
        ArrayList<Minigame> possibleMinigames = new ArrayList<>();
        for (Minigame minigame : minigames) {
            for (MinigameType type : types) {
                if (minigame.getType() == type
                        && !possibleMinigames.contains(minigame)) {
                    possibleMinigames.add(minigame);
                }
            }
        }

        int index = randGen.nextInt(possibleMinigames.size());
        return possibleMinigames.get(index);

    }

}
