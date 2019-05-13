package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import java.util.Random;
import marioparty.Constants;

/**
 *
 * @author Cole
 */
public class PressAButton extends Minigame {

    private boolean[] buttonWasPressed = new boolean[Constants.NUM_OF_PLAYERS];
    private final Controllers controllers = Controllers.getInstance();
    private final Random rangen = new Random();
    private InputAction desiredAction;

    public PressAButton() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        this.desiredAction = InputAction.values()[rangen.nextInt(InputAction.values().length - 4) + 4];
    }

    @Override
    public void run() {
        this.dc.drawString("Press " + this.desiredAction, 450, 300);
    }

    @Override
    public int isDone() {
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {

            if (Controllers.getInstance().getControllerInput(i).actions().contains(this.desiredAction)) {
                System.out.println(i);
                return i;
            }
        }
        return -1;
    }

}
