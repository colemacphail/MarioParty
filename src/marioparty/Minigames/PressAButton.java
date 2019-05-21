package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import marioparty.Constants;
import marioparty.Players;

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
    public Set isDone() {
        Set<Players> winningPlayers = new HashSet<>();

        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {

            if (Controllers.getInstance().getControllerInput(i).actions().contains(this.desiredAction)) {
                winningPlayers.add(Players.values()[i]);
            }
        }
        return winningPlayers;
    }

}
