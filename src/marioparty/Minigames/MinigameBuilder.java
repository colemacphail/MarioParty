package marioparty.Minigames;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Cole
 */
public class MinigameBuilder {

    private final ArrayList<Minigame> minigames = new ArrayList<>();
    private final Random randGen = new Random();

    private static MinigameBuilder instance;

    public static MinigameBuilder getInstance() {
        if (MinigameBuilder.instance == null) {
            MinigameBuilder.instance = new MinigameBuilder();
        }

        return MinigameBuilder.instance;
    }

    private MinigameBuilder() {
        // add all created minigames here
        // i.e. this.minigames.add(new FunGame());
        this.minigames.add(new TestMinigame());
        this.minigames.add(new CatchTheApple());
    }

    //takes in an array of valid types and spits out a minigame that matches one of the types
    public Minigame chooseMinigame(MinigameType[] types) {
        ArrayList<Minigame> possibleMinigames = new ArrayList<>();
        for (Minigame minigame : this.minigames) {
            for (MinigameType type : types) {
                if (minigame.getType() == type
                        && !possibleMinigames.contains(minigame)) {
                    possibleMinigames.add(minigame);
                }
            }
        }
        // grab a minigame at a random index
        int index = this.randGen.nextInt(possibleMinigames.size());
        return possibleMinigames.get(index);

    }

}
