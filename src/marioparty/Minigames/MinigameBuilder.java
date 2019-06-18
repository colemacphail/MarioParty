package marioparty.Minigames;

import java.util.ArrayList;
import java.util.Random;
import marioparty.Minigames.MinigameList.CatchTheApple;
import marioparty.Minigames.MinigameList.Jumpman;
import marioparty.Minigames.MinigameList.Masher;
import marioparty.Minigames.MinigameList.PressAButton;
import marioparty.Minigames.MinigameList.QuickTime;
import marioparty.Minigames.MinigameList.ShootEmUp;
import marioparty.Minigames.MinigameList.TripleJump;

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
        this.minigames.add(new Jumpman());
        this.minigames.add(new CatchTheApple());
        this.minigames.add(new ShootEmUp());
        this.minigames.add(new Masher());
        this.minigames.add(new QuickTime());
        this.minigames.add(new PressAButton());
        this.minigames.add(new TripleJump());
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
