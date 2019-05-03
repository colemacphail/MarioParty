package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import java.util.Random;
import marioparty.Characters;
import marioparty.Constants;

/**
 *
 * @author Cole
 */
public class Masher extends Minigame {

    private boolean[] buttonWasPressed = new boolean[Constants.NUM_OF_PLAYERS];
    private Controllers controllers = Controllers.getInstance();
    private Random rangen = new Random();
    private InputAction desiredAction;

    public Masher() {
        super(MinigameType.FFA, 10000);
    }

    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
        this.desiredAction = InputAction.values()[rangen.nextInt()];
    }

    @Override
    public void run() {
        this.dc.drawString("Press " + this.desiredAction, 450, 300);
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (this.controllers.getControllerInput(i).actions().contains(this.desiredAction) && !this.buttonWasPressed[i]) {
                Characters.characters[i].changeMinigameScore(1);
                this.buttonWasPressed[i] = true;
            }

            if (!this.controllers.getControllerInput(i).actions().contains(this.desiredAction)) {
                this.buttonWasPressed[i] = false;
            }
        }
    }

    @Override
    public int isDone() {//TODO: have actual finishing condition

        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (Characters.characters[i].getMinigameScore() >= 20){
                return i;
            }
        }

        return -1;
    }

}
