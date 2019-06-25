package marioparty.Minigames.MinigameList;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import marioparty.Characters;
import marioparty.Constants;
import marioparty.Minigames.Minigame;
import marioparty.Minigames.MinigameType;
import marioparty.Players;

/**
 *
 * @author Cole
 */
public class Masher extends Minigame {

    private boolean[] buttonWasPressed = new boolean[Constants.NUM_OF_PLAYERS];
    private final Controllers controllers = Controllers.getInstance();
    private final Random rangen = new Random();
    private InputAction desiredAction;

    public Masher() {
        super(MinigameType.FFA, 10000);
    }

    @Override
    public void init() {
        this.desiredAction = InputAction.values()[rangen.nextInt(InputAction.values().length - 5) + 5];
    }

    @Override
    public void run() {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawString("Mash " + this.desiredAction, this.dc.getWidth() / 2, this.dc.getHeight() / 2);
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
    public Set<Players> isDone() {//TODO: have actual finishing condition
        Set<Players> winningPlayers = new HashSet<>();
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (Characters.characters[i].getMinigameScore() >= 20) {
                winningPlayers.add(Players.values()[i]);
            }
        }

        return winningPlayers;
    }

}
